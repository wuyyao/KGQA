package com.wuyaoyao.kgqa.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select name from user where name = #{name}")
    public String selectName(String name);

    @Select("select password from user where name = #{name}")
    public String selectPassword(String name);

    @Insert("insert into user(name, password, phone) values(#{name}, #{password}, #{phone})")
    public void addUser(String name, String password, String phone);
}
