package com.wuyaoyao.kgqa.controller;

import com.wuyaoyao.kgqa.entity.Record;
import com.wuyaoyao.kgqa.service.RecordService;
import com.wuyaoyao.kgqa.service.SearchService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 基于python写的代码进行答案搜寻
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService service;

    @Autowired
    private RecordService recordService;

    @ResponseBody
    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public String answer(@RequestBody HashMap<String, String> map) {
        String question = map.get("question");
        System.out.println(question);
        if (map.get("userName") != null && map.get("userName") != "") {
            String userName = map.get("userName");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String time = df.format(new Date());
            Record record = new Record();
            record.setName(userName);
            record.setSearchRecord(question);
            record.setSearchTime(time);
            record.setType("text");
            recordService.addRecord(record);
        }
        return service.findAnswer(question);
    }

    @ResponseBody
    @RequestMapping(value = "/knowledgeGraph", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String query(@RequestBody HashMap<String, String> map) throws JSONException {
        String diseaseName = map.get("name");
        System.out.println(diseaseName);
        if (map.get("userName") != null && map.get("userName") != "") {
            String userName = map.get("userName");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String time = df.format(new Date());
            Record record = new Record();
            record.setName(userName);
            record.setSearchRecord(diseaseName);
            record.setSearchTime(time);
            record.setType("graph");
            recordService.addRecord(record);
        }
        String result = service.findGraph(diseaseName);
        String info = "{\"info\":" + result.substring(0, result.indexOf("}") + 1) + ",";
        String show = "\"show\":" + result.substring(result.indexOf("}") + 1, result.length()) + "}";
        String data = info + show;
        System.out.println(data);
        return data;
    }
}
