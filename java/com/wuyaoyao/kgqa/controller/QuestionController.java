package com.wuyaoyao.kgqa.controller;

import com.wuyaoyao.kgqa.entity.Record;
import com.wuyaoyao.kgqa.service.QuestionService;
import com.wuyaoyao.kgqa.service.RecordService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 基于分类器和java代码进行答案搜寻
 */
@RestController
@RequestMapping("/query")
public class QuestionController {
    @Autowired
    private QuestionService questService;

    @Autowired
    private RecordService recordService;

    @ResponseBody
    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public String answer(@RequestBody HashMap<String, String> map) throws Exception {
        String question = map.get("question");
        System.out.println(question);
        //如果是登录状态就把问题存入到该用户的搜索历史中
        if (map.get("userName") != null && map.get("userName") != ""){
            String userName = map.get("userName");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String time = df.format(new Date());
            Record record = new Record();
            record.setName(userName);
            record.setSearchRecord(question);
            record.setSearchTime(time);
            record.setType("问题搜索");
            recordService.addRecord(record);
        }
        return questService.answer(question);
    }

    @ResponseBody
    @RequestMapping(value = "/knowledgeGraph", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public String query(@RequestBody HashMap<String, String> map) throws JSONException {
        String diseaseName = map.get("name");
        System.out.println(diseaseName);
        //如果是登录状态就把问题存入到该用户的搜索历史中
        if (map.get("userName") != null && map.get("userName") != ""){
            String userName = map.get("userName");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String time = df.format(new Date());
            Record record = new Record();
            record.setName(userName);
            record.setSearchRecord(diseaseName);
            record.setSearchTime(time);
            record.setType("可视化搜索");
            recordService.addRecord(record);
        }
        return questService.knowledgeGraph(diseaseName);
    }

}
