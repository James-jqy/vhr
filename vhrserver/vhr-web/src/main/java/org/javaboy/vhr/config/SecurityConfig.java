package org.javaboy.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaboy.vhr.bean.Hr;
import org.javaboy.vhr.bean.RespBean;
import org.javaboy.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Classname SecurityConfig
 * @Description 权限管理基本思路：
 *  首先，我们在数据库中有资源menu表。这个表中有url地址这一栏。那么，这一栏是用户在前端发送的接口地址。就是说，接口地址的
 *  设计一定要遵从这个ant风格的地址。那么，我们在后端接收到一个前端请求的接口地址之后，我们将这个接口地址与数据库中menu表中
 *  的url这一栏进行比较，看看符合哪个ant风格的接口地址。从而我们能够得到menu的id。我们还有一个menu_role表。刚刚我们已经
 *  获得了menu的id，那么我们通过这个menu_role表就可以得到这个menu所需要的role（角色）。得到所需要的角色之后，我们就去拿到
 *  已经登录的用户的角色，看看这个已经登录的用户的角色是否在需要的角色里面。如果在的话，那么表明，这个用户有这个角色，也就是
 *  有这个权限能够访问这个接口。从而可以放行。
 *
 * @Date 2020/12/11 10:21
 * @Created by Jieqiyue
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    HrService hrService;
    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;
    @Autowired
    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();

        // 登录成功执行的方法。
        loginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                resp.setContentType("application/json; charset=UTF-8");
                PrintWriter writer = resp.getWriter();

                // 获得登陆成功的对象。
                Hr hr = (Hr)authentication.getPrincipal();
                // 返回给前端的hr对象要擦除密码字段。也可以在hr类的这个属性上加上@jsonignore，但是这样从json反序列化会hr对象也会忽略密码，可能会有问题。
                hr.setPassword(null);
                RespBean ok = RespBean.ok("登陆成功！", hr);
                String hrstring = new ObjectMapper().writeValueAsString(ok);
                writer.write(hrstring);

                writer.flush();
                writer.close();
            }
        });
        // 登录失败执行的方法。
        loginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {

                resp.setContentType("application/json; charset=UTF-8");
                PrintWriter writer = resp.getWriter();

                RespBean respBean = RespBean.error(e.getMessage());

                if (e instanceof LockedException){
                    respBean.setMsg("账户被锁定，请联系管理员！");
                }else if(e instanceof CredentialsExpiredException){
                    respBean.setMsg("密码过期，请联系管理员！");
                }else if(e instanceof AccountExpiredException){
                    respBean.setMsg("账户过期，请联系管理员！");
                }else if (e instanceof DisabledException){
                    respBean.setMsg("账户被禁用，请联系管理员！");
                }else if(e instanceof BadCredentialsException){
                    respBean.setMsg("用户名或者密码错误，请重新输入！");
                }

                writer.write(new ObjectMapper().writeValueAsString(respBean));

                writer.flush();
                writer.close();
            }
        });
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/doLogin");
        return loginFilter;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login","/css/**","/js/**","/verifyCode","/favicon.ico","/fonts/**","/img/**","/index.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        o.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                        return o;
                    }
                })
//                .and()
//                // 配置自动登录功能
//                .rememberMe()
//                // 自动登录的的时候的盐值
//                .key("javaboy")
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json; charset=UTF-8");
                        PrintWriter writer = resp.getWriter();

                        writer.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功！")));
                        writer.flush();
                        writer.close();
                    }
                })
                .permitAll()
                .and()
                .csrf().disable().exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json; charset=UTF-8");
                        resp.setStatus(401);
                        PrintWriter writer = resp.getWriter();

                        RespBean respBean = RespBean.error("访问失败");
                        if (e instanceof InsufficientAuthenticationException){
                            respBean.setMsg("请求失败，请联系管理员！");
                        }

                        writer.write(new ObjectMapper().writeValueAsString(respBean));
                        writer.flush();
                        writer.close();
                    }
                });
        http.addFilterAfter(loginFilter(),UsernamePasswordAuthenticationFilter.class);
    }
}
