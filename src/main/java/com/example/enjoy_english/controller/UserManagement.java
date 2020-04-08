package com.example.enjoy_english.controller;

import com.example.enjoy_english.model.User;
import com.example.enjoy_english.service.UserService;
import com.example.enjoy_english.tools.Result;
import com.example.enjoy_english.tools.VerificationCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class UserManagement {
    @Resource
    private UserService userService;

    //获取验证码
    @GetMapping("/verificationCode")
    public void verificationCode(HttpSession  session, HttpServletResponse response) throws IOException {
        VerificationCode verificationCode = new VerificationCode();
        BufferedImage image = verificationCode.getImage();
        String text = verificationCode.getText();
        session.setAttribute("verificationCode", text);
        VerificationCode.output(image, response.getOutputStream());
    }

    //注册新用户
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        return userService.addUser(user);
    }



}
