package com.wuyaoyao.kgqa.service.impl;

import com.wuyaoyao.kgqa.entity.Record;
import com.wuyaoyao.kgqa.mapper.RecordMapper;
import com.wuyaoyao.kgqa.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    /**
     * 添加搜索历史记录
     * @param record
     */
    @Override
    public void addRecord(Record record) {
        String name = record.getName();
        String searchRecord = record.getSearchRecord();
        String searchTime = record.getSearchTime();
        String type = record.getType();
        recordMapper.addRecord(name, searchRecord, searchTime, type);
    }

    /**
     * 返回搜索历史列表
     * @param name
     * @return
     */
    @Override
    public List<Record> getRecord(String name) {
        return recordMapper.getRecord(name);
    }
}
