package com.wuyaoyao.kgqa.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DiseaseIfExist {
    public static boolean ifExist(String diseaseName) {
        String fileName = "E:\\pythonProject\\dictionary\\disease.txt";
        try {
            /*创建输入流和输出流*/
            FileInputStream inputStream = new FileInputStream(fileName);
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            String line = null;
            while ((line = in.readLine()) != null) {
                if (diseaseName.equals(line)) {
                    in.close();
                    return true;
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args){
        String diseaseName = "小儿肺炎";
        System.out.println(ifExist(diseaseName));
    }
}
