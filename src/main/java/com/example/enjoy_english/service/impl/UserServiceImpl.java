package com.example.enjoy_english.service.impl;

import com.example.enjoy_english.model.User;
import com.example.enjoy_english.repository.UserRepository;
import com.example.enjoy_english.service.UserService;
import com.example.enjoy_english.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Pattern;


@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @Override
    public User findByAccno(String accno) {
        return userRepository.findByAccno(accno);
    }

    @Override
    public Result addUser(User user) {
        //校验用户名
        if (user.getAccno() == null || user.getAccno().trim().equals("") || user.getAccno().length() > 20){
            return new Result().error("用户名格式错误");
        }
        if (userRepository.findByAccno(user.getAccno()) != null){
            return new Result().error("用户名已存在");
        }
        //校验密码，如果未设置密码，则默认与用户ID相同
        if (user.getPassword() == null || user.getPassword().trim().equals("")){
            user.setPassword(passwordEncoder.encode(user.getAccno()));
        } else if (user.getPassword() != null && user.getPassword().length() <= 32){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            return new Result().error("密码格式错误");
        }
        //默认用户名与用户ID相同
        if (user.getName() == null || user.getName().trim().equals("")){
            user.setName(user.getAccno());
        }
        //校验电话号码
        if (user.getTelno() != null && ! isTelphoneNum(user.getTelno())){
            return new Result().error("电话号码格式错误");
        }
        //设置用户角色
        user.setRole(1);
        //设置注册时间
        user.setRegisterdatetime(new Timestamp(new Date().getTime()));
        return new Result(1, "注册账户成功", userRepository.save(user));
    }

    @Transactional
    @Override
    public Result deleteByAccno(String accno) {
        userRepository.deleteByAccno(accno);
        return new Result().success("账号" + accno + "已删除", null);
    }

    @Transactional
    @Override
    public Result updateUser(User user, boolean isAdmin){
        User new_user = userRepository.findByAccno(user.getAccno());
        if (new_user == null){
            return new Result().error("账号不存在");
        }
        if (user.getPassword() != null){
            if (user.getPassword().trim().equals("") || user.getPassword().length() > 32){
                return new Result().error("密码格式错误");
            }
            new_user.setPassword(user.getPassword());
        }
        if (user.getName() != null){
            if (user.getName().length() > 20){
                return new Result().error("名称格式错误");
            }
            new_user.setName(user.getName());
        }
        if (user.getTelno() != null){
            if ( ! isTelphoneNum(user.getTelno())){
                return new Result().error("电话号码格式错误");
            }
            new_user.setTelno(user.getTelno());
        }
        new_user.setRole(1);
//        if (isAdmin){
//            new_user.setRole(user.getRole());
//        }
        return new Result().success("资料修改成功",new_user);
    }

    private boolean isTelphoneNum(String str){
        Pattern pattern = Pattern.compile("[0-9]{11}");
        return pattern.matcher(str).matches();
    }
}
