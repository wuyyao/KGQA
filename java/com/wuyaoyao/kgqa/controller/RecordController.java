package com.wuyaoyao.kgqa.controller;

import com.wuyaoyao.kgqa.entity.Record;
import com.wuyaoyao.kgqa.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/history")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @ResponseBody
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public List<Record> getAllRecord(@RequestBody HashMap<String, String> map) {
        String userName = map.get("userName");
        return recordService.getRecord(userName);
    }
}
