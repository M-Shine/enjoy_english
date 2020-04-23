package com.example.enjoy_english.controller;

import com.example.enjoy_english.model.User;
import com.example.enjoy_english.service.FeedbackService;
import com.example.enjoy_english.service.UserService;
import com.example.enjoy_english.tools.Result;
import com.example.enjoy_english.tools.VerificationCode;
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
import java.util.Map;

@RestController
public class UserManagementController {
    @Resource
    private UserService userService;
    @Resource
    private FeedbackService feedbackService;

    //获取验证码
    @GetMapping("/verificationCode")
    public void verificationCode(HttpSession session, HttpServletResponse response) throws IOException {
        VerificationCode verificationCode = new VerificationCode();
        BufferedImage image = verificationCode.getImage();
        session.setAttribute("verificationCode", verificationCode.getText());
        VerificationCode.output(image, response.getOutputStream());
    }

    //查询所有用户资料
    @GetMapping("/management/getUser")
    public Result getUsers(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return userService.findAll(pageable);
    }

    //查询用户个人资料
    @GetMapping("/api/getUserByAccno")
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

    //查询用户反馈记录
    @GetMapping("/management/getFeedback")
    public Result getFeedback(@PageableDefault(page = 0, size = 10) Pageable pageable,
                              String accno, String startDatetime, String endDatetime, String keyword,
                              @RequestParam(defaultValue = "0") float minReward,
                              @RequestParam(defaultValue = "0") float maxReward){
        return feedbackService.findAll(pageable, accno, startDatetime, endDatetime, minReward, maxReward, keyword);
    }

    //增加反馈记录
    @PostMapping("/api/addFeedback")
    public Result addFeedback(@RequestBody Map<String, Object> data){
        String current_accno = SecurityContextHolder.getContext().getAuthentication().getName();
        if (data.get("accno") == null){
            return new Result().error("账号不存在");
        }
        if (isAdmin() || current_accno.equals(data.get("accno"))){
            return feedbackService.addFeedback(data);
        } else {
            return new Result().error("权限不足");
        }
    }

    //修改反馈记录
    @PostMapping("/management/updateFeedback")
    public Result updateFeedback(@RequestBody Map<String, Object> data){
        return feedbackService.updateFeedback(data);
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
