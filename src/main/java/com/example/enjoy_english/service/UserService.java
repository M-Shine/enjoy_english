package com.example.enjoy_english.service;

import com.example.enjoy_english.model.User;
import com.example.enjoy_english.tools.Result;

import java.util.List;

public interface UserService {
    // 根据acc_no查询用户信息
    User findByAccno(String accno);
    // 添加用户
    Result addUser(User user);
    // 更新用户信息
    User updateUser(User user);
    // 删除用户
    int deleteByAccno(String accno);
    // 查询所有用户
    List<User> findAll();
}
