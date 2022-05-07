package com.wuyaoyao.kgqa.controller;

import com.wuyaoyao.kgqa.entity.User;
import com.wuyaoyao.kgqa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody HashMap<String, String> map) {
        User user = new User();
        user.setName(map.get("name"));
        user.setPassword(map.get("password"));
        user.setPhone(map.get("phone"));
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody HashMap<String, String> map) {
        User user = new User();
        user.setName(map.get("userName"));
        user.setPassword(map.get("userPassword"));
        return userService.selectUserName(user);
    }

    @ResponseBody
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String confirm(@RequestBody HashMap<String, String> map) {
        System.out.println(map.get("name"));
        String name = map.get("name");
        return userService.selectName(name);
    }
}
