package com.example.enjoy_english.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        //客户端MAC_ADDRESS
        String mac_address = null;
        //系统生成的验证码
        String verificationCode = (String) request.getSession().getAttribute("verificationCode");
        // 处理JSON形式的登录请求
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)){
            Map<String, String> loginData = new HashMap<>();
            try {
                loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
            } finally {
                //用户输入的验证码
                String code = loginData.get("code");
                //验证码校验
                checkCode(code, verificationCode);
                //存储用户MAC_ADDRESS
                mac_address = loginData.get("mac_address");
                if (!isMacAddress(mac_address)){
                    throw new AuthenticationServiceException("设备ID验证失败");
                }
                request.getSession().setAttribute("mac_address", mac_address);
            }

            String username = loginData.get(getUsernameParameter());
            String password = loginData.get(getPasswordParameter());

            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        }else {
            // 处理key/value形式的登录请求
            mac_address = request.getParameter("mac_address");
            if (!isMacAddress(mac_address)){
                throw new AuthenticationServiceException("设备ID验证失败");
            }
            request.getSession().setAttribute("mac_address", mac_address);
            checkCode(request.getParameter("code"), verificationCode);
            return super.attemptAuthentication(request, response);
        }
    }

    private void checkCode(String code, String verificationCode) {
        if (verificationCode == null || code == null || "".equals(code) || !verificationCode.toLowerCase().equals(code.toLowerCase())) {
            throw new AuthenticationServiceException("验证码输入错误");
        }
    }

    private boolean isMacAddress(String mac_address) {
        String patternMac = "^([A-Fa-f0-9]{2}[-,:]){5}[A-Fa-f0-9]{2}$";
        Pattern pa = Pattern.compile(patternMac);
        boolean isMac = pa.matcher(mac_address).find();
        if (isMac) {
            return true;
        } else {
            return false;
        }
    }
}
