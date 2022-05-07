package com.wuyaoyao.kgqa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class SimFeatureUtils {

    private static int min(int one, int two, int three) {
        int min = one;
        if (two < min) {
            min = two;
        }
        if (three < min) {
            min = three;
        }
        return min;
    }

    public static int ld(String str1, String str2) {
        int d[][]; // 矩阵
        int n = str1.length();
        int m = str2.length();
        int i; // 遍历str1的
        int j; // 遍历str2的
        char ch1; // str1的
        char ch2; // str2的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }
        for (i = 1; i <= n; i++) { // 遍历str1
            ch1 = str1.charAt(i - 1);
            // 去匹配str2
            for (j = 1; j <= m; j++) {
                ch2 = str2.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1]+ temp);
            }
        }
        return d[n][m];
    }
    public static double sim(String str1, String str2) {
        try {
            double ld = (double)ld(str1, str2);
            return (1-ld/(double)Math.max(str1.length(), str2.length()));
        } catch (Exception e) {
            return 0.1;
        }
    }

    public static String similar(String diseaseName) {
        Double max = 0.0;
        String most_similar = "";
        try {
            File myFile = new File("C:\\Users\\Administrator\\IdeaProjects\\KGQA\\src\\main\\resources\\dictionary\\disease.txt");//通过字符串创建File类型对象，指向该字符串路径下的文件
            if (myFile.isFile() && myFile.exists()) { //判断文件是否存在
                InputStreamReader Reader = new InputStreamReader(new FileInputStream(myFile), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(Reader);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    Double tmp = sim(diseaseName, lineTxt);
                    if(tmp > max){
                        most_similar = lineTxt;
                        max = tmp;
                    }
                }
                Reader.close();
                System.out.println("最相似的实体名为："+most_similar+"编辑距离相似度为"+max);
                /*List<Map.Entry<String, Double>> entryList2 = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
                Collections.sort(entryList2, new Comparator<Map.Entry<String, Double>>() {
                    @Override
                    public int compare(Map.Entry<String, Double> me1, Map.Entry<String, Double> me2) {
                        return me2.getValue().compareTo(me1.getValue()); // 降序排序
                    }
                });
                System.out.println("第一种Map排序方式, 根据value排序结果: \n" + entryList2);*/
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return most_similar;
    }
}