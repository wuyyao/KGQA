package com.wuyaoyao.kgqa.utils;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 把分类器问题模板进行分词得到词汇表
 */
public class GetVocabulary {
    public static <HashMap> void main(String[] args) {
        java.util.HashMap<String, Integer> map = new java.util.HashMap<>();
        String fileName = "E:\\毕业设计\\question\\vocabulary.txt";
        String fileNameOut = "E:\\毕业设计\\question\\result.txt";

        try {
            /*创建输入流和输出流*/
            FileInputStream inputStream = new FileInputStream(fileName);
            ;/*产生了一个(节点)流对象
            方便后期用流对其数据操作:一般是使用该流对象对应具有的方法进行操作:
            比如.read()方法来取用某些内容*/
            FileOutputStream outputStream = new FileOutputStream(fileNameOut);

            //String str;
            byte[] bytes = new byte[1024];/*申请一个byte[]类型的数组(对象),该对象的规格通过new Byte[1024] 来定义,用bytes引用变量类管理这个数组对象;
            这个数组用于存储届时读取的数据,另外注意不要使用byte的包装类Byte,两者有所不容*/
            int len = 0;
            try {
                while ((len = inputStream.read(bytes)) != -1) {
                    String s = new String(bytes, StandardCharsets.UTF_8);
                    List<Term> termList = StandardTokenizer.segment(s);/*标准分词器*/
                    // System.out.println(termList);//查看分词效果,发现符号和词语都别列入列表中。
                    for (Term x : termList) {
                        String str = x.toString();
                        if (map.containsKey(str)) {
                            map.put(str, map.get(str) + 1);
                        } else {
                            map.put(str, 1);
                        }
                    }

                }
            }//whileEndIO 要特别注意io流（的关闭）不能在循环里写（出现）
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
//
            List<String> list = new ArrayList<>();
            StringBuffer stringBuffer = new StringBuffer();
            for (String x : map.keySet()) {
                System.out.println(x + ": " + map.get(x));
                list.add(x);
            }//endFor
            /*链接成一整个字符串*/
            for (String x : list) {
                stringBuffer.append(x);
                stringBuffer.append("\r\n");
            }
            System.out.println(stringBuffer);
            /*写入文件*/
            outputStream.write(stringBuffer.toString().getBytes());
            /*关闭io流，释放资源*/
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
