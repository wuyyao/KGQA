package com.wuyaoyao.kgqa.service;

public interface SearchService {
    /**
     * 根据问题调用python程序用基于模板匹配的方式找到答案
     * @param question
     * @return
     */
    String findAnswer(String question);

    /**
     * 根据疾病名称调用python程序找到答案绘制知识图谱
     * @param diseaseName
     * @return
     */
    String findGraph(String diseaseName);
}
