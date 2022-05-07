package com.wuyaoyao.kgqa.service.impl;

import com.wuyaoyao.kgqa.entity.User;
import com.wuyaoyao.kgqa.mapper.UserMapper;
import com.wuyaoyao.kgqa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录验证
     * @param user
     * @return
     */
    @Override
    public String selectUserName(User user) {
        String name = user.getName();
        String password = user.getPassword();

        String result = "-1";
        // 将输入的密码使用md5加密
        String passwordMD5 = passwordMD5(name, password);

        // 用户不存在
        if (userMapper.selectName(name) == null) {
            result = "0";
            return result;
            //  用户存在，但密码输入错误
        } else if (!userMapper.selectPassword(name).equals(passwordMD5)) {
            result = "1";
            return result;
            //  登录成功
        } else if (userMapper.selectPassword(name).equals(passwordMD5)) {
            result = name;
            return result;
        }
        return result;
    }

    /**
     * 验证用户是否存在
     * @param name
     * @return 0是不存在 1是存在
     */
    @Override
    public String selectName(String name) {
        String result = "1";
        // 用户不存在
        if (userMapper.selectName(name) == null) {
            result = "0";
            return result;
        }
        return result;
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @Override
    public String addUser(User user) {
        String userName = user.getName();
        String phone = user.getPhone();
        // 用户存在
        if (userMapper.selectName(userName) != null)
            return "0";
        String userPassword = user.getPassword();
        System.out.println(userName + "***" + userPassword);
        String passwordMD5 = passwordMD5(userName, userPassword);
        userMapper.addUser(userName, passwordMD5, phone);
        return "1";
    }


    /**
     * 把密码用MD5加密
     *
     * @param userName
     * @param userPassword
     * @return
     */
    public String passwordMD5(String userName, String userPassword) {
        // 需要加密的字符串
        String src = userName + userPassword;
        try {
            // 加密对象，指定加密方式
            MessageDigest md5 = MessageDigest.getInstance("md5");
            // 准备要加密的数据
            byte[] b = src.getBytes();
            // 加密：MD5加密一种被广泛使用的密码散列函数，
            // 可以产生出一个128位（16字节）的散列值（hash value），用于确保信息传输完整一致
            byte[] digest = md5.digest(b);
            // 十六进制的字符
            char[] chars = new char[]{'0', '1', '2', '3', '4', '5',
                    '6', '7', 'A', 'B', 'C', 'd', 'o', '*', '#', '/'};
            StringBuffer sb = new StringBuffer();
            // 处理成十六进制的字符串(通常)
            // 遍历加密后的密码，将每个元素向右位移4位，然后与15进行与运算(byte变成数字)
            for (byte bb : digest) {
                sb.append(chars[(bb >> 4) & 15]);
                sb.append(chars[bb & 15]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
