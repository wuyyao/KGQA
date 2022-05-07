package com.wuyaoyao.kgqa.service;

import com.wuyaoyao.kgqa.entity.User;

public interface UserService {
    /**
     * 登录验证
     * @param user
     * @return
     */
    String selectUserName(User user);

    /**
     * 验证用户是否存在
     * @param name
     * @return
     */
    String selectName(String name);

    /**
     * 添加用户
     * @param user
     * @return
     */
    String addUser(User user);
}
