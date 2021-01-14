package org.javaboy.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaboy.vhr.bean.RespBean;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Classname VerificationCodeFilter
 * @Description TODO
 * @Date 2021/1/2 19:51
 * @Created by Jieqiyue
 */
@Component
public class VerificationCodeFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if ("POST".equals(request.getMethod()) && "/doLogin".equals(request.getServletPath())){
            // 如果是post请求，并且是登陆请求的话，需要做验证码的判断。
            String verifyText = (String)request.getSession().getAttribute("verify_code");
            String code = request.getParameter("code");

            if (verifyText == null){
                // 如果验证码不正确的话。返回错误消息。
                response.setContentType("application/json; charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(new ObjectMapper().writeValueAsString(RespBean.error("验证码未成功获取！")));
                writer.flush();
                writer.close();
                return ;
            }
            if (code == null || "".equals(code) || !code.toLowerCase().equals(verifyText.toLowerCase())){
                // 如果验证码不正确的话。返回错误消息。
                response.setContentType("application/json; charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(new ObjectMapper().writeValueAsString(RespBean.error("验证码错误填写错误！")));
                writer.flush();
                writer.close();
                return ;
            }else{
                // 如果验证码正确的话
                filterChain.doFilter(servletRequest, servletResponse);
            }

        }else{
            // 否则，不是登陆请求，则直接放过
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
