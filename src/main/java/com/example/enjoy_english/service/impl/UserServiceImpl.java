package com.example.enjoy_english.service.impl;

import com.example.enjoy_english.model.User;
import com.example.enjoy_english.repository.UserRepository;
import com.example.enjoy_english.service.UserService;
import com.example.enjoy_english.tools.PageResult;
import com.example.enjoy_english.tools.Result;
import com.example.enjoy_english.tools.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Result findAll(Pageable pageable){
        Page<User> userPage = userRepository.findAll(pageable);
        List<User> userList = userPage.getContent();
        List<Map> resultUserList = new ArrayList<>();
        for (User user : userList){
            if (user.getRole() == UserRoles.ADMIN.getRole()){
                // 跳过管理员账号
                continue;
            }
            resultUserList.add( formatUser(user) );
        }
        return new PageResult().success(
                null, resultUserList, userPage.getTotalPages(), userPage.getNumber(), userPage.getSize());
    }

    @Override
    public User findUser(String accno) {
        return userRepository.findByAccno(accno);
    }

    @Override
    public Result findByAccno(String accno){
        User user = userRepository.findByAccno(accno);
        if (user == null){
            return new Result().error("不存在用户名为 " + accno + " 的用户");
        }
        return new Result().success(null, formatUser(user));
    }

    @Override
    @Transactional
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
        //设置用户角色为普通用户
        user.setRole( UserRoles.NORMAL.getRole() );
        //设置注册时间
        user.setRegisterdatetime(new Timestamp(new Date().getTime()));
        User newUser = userRepository.save(user);
        if (newUser == null){
            return new Result().error("注册失败");
        }
        return new Result(1, "注册账户成功", formatUser(newUser));
    }

    @Transactional
    @Override
    public Result deleteByAccno(List<String> accnoList) {
        int statu = userRepository.deleteByAccno(accnoList);
        if (statu <= 0){
            return new Result().error("删除失败");
        }
        return new Result().success("已删除", accnoList);
    }

    @Transactional
    @Override
    public Result updateUser(User user){
        User oldUser = userRepository.findByAccno(user.getAccno());
        if (oldUser == null){
            return new Result().error("账号不存在");
        }

        if (user.getPassword() != null){
            if (user.getPassword().trim().equals("") || user.getPassword().length() > 32){
                return new Result().error("密码格式错误");
            }
            oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getName() != null){
            if (user.getName().trim().equals("") || user.getName().length() > 20){
                return new Result().error("名称格式错误");
            }
            oldUser.setName(user.getName());
        }

        if (user.getTelno() != null){
            if ( ! isTelphoneNum(user.getTelno())){
                return new Result().error("电话号码格式错误");
            }
            oldUser.setTelno(user.getTelno());
        }

        oldUser.setRole(1);

        User newUser = userRepository.save(oldUser);
        if (newUser == null){
            return new Result().error("更新用户信息失败");
        }
        return new Result().success("资料修改成功", formatUser(newUser));
    }

    private boolean isTelphoneNum(String str){
        Pattern pattern = Pattern.compile("[0-9]{11}");
        return pattern.matcher(str).matches();
    }

    private Map formatUser(User user){
        Map<String, String> resultUser = new HashMap<>();
        resultUser.put("accno", user.getAccno());
        resultUser.put("name", user.getName());
        resultUser.put("telno", user.getTelno());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        resultUser.put("registerdatetime", dateFormat.format(user.getRegisterdatetime()));
        return resultUser;
    }

}
