package com.example.enjoy_english.controller;

import com.example.enjoy_english.model.User;
import com.example.enjoy_english.service.MenuService;
import com.example.enjoy_english.service.UserService;
import com.example.enjoy_english.tools.PageResult;
import com.example.enjoy_english.tools.Result;
import com.example.enjoy_english.tools.VerificationCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

@RestController
public class UserManagementController {
    @Resource
    private MenuService menuService;
    @Resource
    private UserService userService;

    //获取验证码
    @GetMapping("/verificationCode")
    public void verificationCode(HttpSession session, HttpServletResponse response) throws IOException {
        VerificationCode verificationCode = new VerificationCode();
        BufferedImage image = verificationCode.getImage();
        String text = verificationCode.getText();
        session.setAttribute("verificationCode", text);
        VerificationCode.output(image, response.getOutputStream());
    }

    //查询所有用户资料
    @GetMapping("/management/getUsers")
    public PageResult getUsers(@PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<User> userPage = userService.findAll(pageable);
        return new PageResult().success(null, userPage.getContent(), userPage.getNumber(), userPage.getSize());
    }

    //查询用户个人资料
    @GetMapping("/api/getUser")
    public Result getUser(@RequestParam String accno){
        String current_accno = SecurityContextHolder.getContext().getAuthentication().getName();
        //普通用户只能查询自身资料
        if (!isAdmin() && !current_accno.equals(accno)){
            return new Result().error("权限不足");
        }
        return new Result().success(null, userService.findByAccno(accno));
    }

    //注册新用户
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        return userService.addUser(user);
    }

    //更新用户信息
    @PostMapping("/api/updateUser")
    public Result updateUser(@RequestBody User user){
        String current_accno = SecurityContextHolder.getContext().getAuthentication().getName();
        //管理员可修改所有用户的资料，用户只能修改自己的资料
        if (isAdmin() || current_accno.equals(user.getAccno())){
            return userService.updateUser(user, isAdmin());
        } else {
            return new Result().error("权限不足");
        }
    }

    //删除用户个人资料
    @GetMapping("/management/deleteUser")
    public Result deleteUser(@RequestParam String accno){
        return userService.deleteByAccno(accno);
    }

    private String getRole(){
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String role = null;
        for (SimpleGrantedAuthority authority : authorities){
            role = authority.getAuthority();
        }
        return role;
    }

    private boolean isAdmin(){
        String role = getRole();
        if (role != null && role.equals("ROLE_ADMIN")){
            return true;
        }
        return false;
    }

}
