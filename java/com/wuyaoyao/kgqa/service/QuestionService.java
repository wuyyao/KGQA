package com.wuyaoyao.kgqa.service;

import org.json.JSONException;

public interface QuestionService {
    /**
     * 问题搜索
     * 基于分类器计算问题与模板的匹配概率找到答案
     * @param question
     * @return
     * @throws Exception
     */
    String answer(String question) throws Exception;

    /**
     * 知识图谱
     * @param diseaseName
     * @return
     * @throws JSONException
     */
    String knowledgeGraph(String diseaseName) throws JSONException;
}
