package com.wuyaoyao.kgqa.mapper;

import com.wuyaoyao.kgqa.entity.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecordMapper {
    @Select("select name from user where name = #{name}")
    public String selectName(String name);

    @Select("select name, searchRecord, searchTime, type from searchList where name = #{name}")
    public List<Record> getRecord(String name);

    @Insert("insert into searchList(name, searchRecord, searchTime, type) values(#{name}, #{searchRecord}, #{searchTime}, #{type})")
    public void addRecord(String name, String searchRecord, String searchTime, String type);
}
