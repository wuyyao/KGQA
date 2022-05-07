package com.wuyaoyao.kgqa.utils;

import com.hankcs.hanlp.HanLP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实体相似度计算
 */
public class SimilarUtil {
    public static String similar(String diseaseName, int type) {
        Double max = 0.0;
        String most_similar = "";
        String pathName = null;
        try {
            if (type == 0) {
                pathName = "C:\\Users\\Administrator\\IdeaProjects\\KGQA\\src\\main\\resources\\dictionary\\disease.txt";
            } else if (type == 1) {
                pathName = "C:\\Users\\Administrator\\IdeaProjects\\KGQA\\src\\main\\resources\\dictionary\\drug.txt";
            } else if (type == 2) {
                pathName = "C:\\Users\\Administrator\\IdeaProjects\\KGQA\\src\\main\\resources\\dictionary\\check.txt";
            } else if (type == 3) {
                pathName = "C:\\Users\\Administrator\\IdeaProjects\\KGQA\\src\\main\\resources\\dictionary\\food.txt";
            }
            File myFile = new File(pathName);//通过字符串创建File类型对象，指向该字符串路径下的文件
            if (myFile.isFile() && myFile.exists()) { //判断文件是否存在
                InputStreamReader Reader = new InputStreamReader(new FileInputStream(myFile), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(Reader);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    Double tmp = getSimilarity(diseaseName, lineTxt);
                    if (tmp > max) {
                        most_similar = lineTxt;
                        max = tmp;
                        if (tmp == 1.0) { //已经找到完全一样的实体
                            break;
                        }
                    }
                }
                if (max < 0.5) {
                    System.out.println(diseaseName + " 没有相似度大于0.5的实体，最大相似度为：" + max);
                    return "";
                }
                Reader.close();
                System.out.println("与 " + diseaseName + " 最相似的实体名为：" + most_similar + " 余弦相似度为：" + max);
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return most_similar;
    }

    public static double getSimilarity(String sentence1, String sentence2) {
        List<String> sent1Words = getSplitWords(sentence1);
        List<String> sent2Words = getSplitWords(sentence2);
        List<String> allWords = mergeList(sent1Words, sent2Words);

        int[] statistic1 = statistic(allWords, sent1Words);
        int[] statistic2 = statistic(allWords, sent2Words);

        double dividend = 0;
        double divisor1 = 0;
        double divisor2 = 0;
        for (int i = 0; i < statistic1.length; i++) {
            dividend += statistic1[i] * statistic2[i];
            divisor1 += Math.pow(statistic1[i], 2);
            divisor2 += Math.pow(statistic2[i], 2);
        }

        return dividend / (Math.sqrt(divisor1) * Math.sqrt(divisor2));
    }

    private static int[] statistic(List<String> allWords, List<String> sentWords) {
        int[] result = new int[allWords.size()];
        for (int i = 0; i < allWords.size(); i++) {
            result[i] = Collections.frequency(sentWords, allWords.get(i));
        }
        return result;
    }

    private static List<String> mergeList(List<String> list1, List<String> list2) {
        List<String> result = new ArrayList<>();
        result.addAll(list1);
        result.addAll(list2);
        return result.stream().distinct().collect(Collectors.toList());
    }

    private static List<String> getSplitWords(String sentence) {
        // 标点符号会被单独分为一个Term，去除之
        return HanLP.segment(sentence).stream().map(a -> a.word).filter(s -> !"`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？".contains(s)).collect(Collectors.toList());
    }

    private static void test(String name){
        similar(name, 0);
    }

    public static void main(String[] args) {
        test("发热");
    }

}
