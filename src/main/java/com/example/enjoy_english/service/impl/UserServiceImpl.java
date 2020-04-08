package com.example.enjoy_english.service.impl;

import com.example.enjoy_english.model.User;
import com.example.enjoy_english.repository.UserRepository;
import com.example.enjoy_english.service.UserService;
import com.example.enjoy_english.tools.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByAccno(String accno) {
        return userRepository.findByAccno(accno);
    }

    @Override
    public Result addUser(User user) {
        if (user.getAccno() == null || user.getAccno().equals("") || user.getAccno().length() > 20){
            return new Result().error("用户名格式错误");
        }
        if (userRepository.findByAccno(user.getAccno()) != null){
            return new Result().error("用户ID已存在");
        }
        //如果未设置密码，则默认与用户ID相同
        if (user.getPassword() == null || user.getPassword().equals("")){
            user.setPassword(user.getAccno());
        } else if (user.getPassword().length() > 32){
            return new Result().error("密码格式错误");
        }
        //默认用户名与用户ID相同
        if (user.getName() == null || user.getName().equals("")){
            user.setName(user.getAccno());
        }
        if (user.getTelno() != null && user.getTelno().length() != 11){
            return new Result().error("电话号码格式错误");
        }
        if (!"1".equals(String.valueOf(user.getRole())) && (user.getRole() != 0 || user.getRole() != 1)){
            return new Result().error("用户角色错误");
        }
        if (user.getRegisterdatetime() == null){
            user.setRegisterdatetime(new Timestamp(new Date().getTime()));
        }
        return new Result(1, "注册成功", userRepository.save(user));
    }

    @Transactional
    @Override
    public int deleteByAccno(String accno) {
        return userRepository.deleteByAccno(accno);
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User updateUser(User user){
        return userRepository.save(user);
    }
}
