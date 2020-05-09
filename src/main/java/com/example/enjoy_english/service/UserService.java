package com.example.enjoy_english.service;

import com.example.enjoy_english.model.User;
import com.example.enjoy_english.tools.Result;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    // 用于Security安全验证
    User findUser(String accno);
    // 根据用户名查询用户
    Result findByAccno(String accno);
    // 添加用户
    Result addUser(User user);
    // 更新用户信息
    Result updateUser(User user);
    // 删除用户
    Result deleteByAccno(List<String> accnoList);
    // 查询所有用户
    Result findAll(Pageable pageable);
}
