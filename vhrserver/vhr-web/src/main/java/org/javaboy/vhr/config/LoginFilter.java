package org.javaboy.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname LoginFilter
 * @Description 自己的登陆验证逻辑。继承自UsernamePasswordAuthenticationFilter，这样在用户用的是用户名密码
 * 登陆的时候，必然要经过这个过滤器。做登陆验证码的工作，和使这个登陆支持json格式的数据登陆。（security默认是表单登陆）
 * @Date 2021/1/2 21:10
 * @Created by Jieqiyue
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        // 从session中获取验证码，可能会为null，当没有请求验证码接口的时候，session中是没有这个值的。
        String verify_code = (String) request.getSession().getAttribute("verify_code");

        // application/json;charset=UTF-8 application/json;charset=UTF-8
        if ( request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) ||
                request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ){
            Map<String, String> loginData = new HashMap<>();

            try {
                loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
            }finally {
                String code = loginData.get("code");
                checkCode(response, code, verify_code);
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
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }else {
            checkCode(response,request.getParameter("code"),verify_code);
            return super.attemptAuthentication(request, response);
        }
    }

    private void checkCode(HttpServletResponse response, String code, String verify_code) {
        if (code == null || verify_code == null || "".equals(code) || !verify_code.toLowerCase().equals(code.toLowerCase())) {
            //验证码不正确
            throw new AuthenticationServiceException("验证码不正确");
        }
    }
}
