package com.wuyaoyao.kgqa.service;

import com.wuyaoyao.kgqa.entity.Record;

import java.util.List;

public interface RecordService {
    /**
     * 添加搜索历史记录
     *
     * @param record
     */
    void addRecord(Record record);

    /**
     * 返回搜索历史列表
     *
     * @param name
     * @return
     */
    List<Record> getRecord(String name);
}
