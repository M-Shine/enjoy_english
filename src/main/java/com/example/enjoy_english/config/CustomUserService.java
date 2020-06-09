package com.example.enjoy_english.config;

import com.example.enjoy_english.model.User;
import com.example.enjoy_english.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置用户权限
 */

@Service
public class CustomUserService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUser(username);
        // 查询不到用户
        if (user == null){
            throw new UsernameNotFoundException("用户不存在！");
        }
        String role;
        if (user.getRole() == 0){   //管理员
            role = "ROLE_ADMIN";
        } else {    //一般用户
            role = "ROLE_USER";
        }
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        authorityList.add(new SimpleGrantedAuthority(role));
        return new UserPrincipal(user, authorityList);
    }
}
