package com.wuyaoyao.kgqa.service.impl;

import com.wuyaoyao.kgqa.service.SearchService;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class SearchServiceImpl implements SearchService {
    /**
     *
     * @param question
     * @return
     */
    @Override
    public String findAnswer(String question){
        Process proc;
        String answer = "";
        try {
            String[] args = new String[] { "E:\\pythonProject\\venv\\Scripts\\python.exe", "E:\\pythonProject\\framework_query\\QA.py", question};
            proc = Runtime.getRuntime().exec(args);// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"gbk"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                answer = answer + line;
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return answer;
    }

    /**
     * 根据疾病名称调用python程序找到答案绘制知识图谱
     * @param diseaseName
     * @return
     */
    @Override
    public String findGraph(String diseaseName){
        Process proc;
        String answer = "";
        try {
            String[] args = new String[] { "E:\\pythonProject\\venv\\Scripts\\python.exe", "E:\\pythonProject\\framework_query\\visualization.py", diseaseName};
            proc = Runtime.getRuntime().exec(args);// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"gbk"));
            String line = null;
            while ((line = in.readLine()) != null) {
                answer = answer + line;
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return answer;
    }
}
