package org.javaboy.vhr.config;

import org.javaboy.vhr.bean.Menu;
import org.javaboy.vhr.bean.Role;
import org.javaboy.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * @Classname MyFilter
 * @Description 这个类的作用，主要是根据用户传来的请求地址，分析出请求需要的角色。
 * @Date 2020/12/16 16:11
 * @Created by Jieqiyue
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> allMenusWithRole = menuService.getAllMenusWithRole();

        for (Menu menu : allMenusWithRole) {
            if (antPathMatcher.match(menu.getUrl(),requestUrl)){
                List<Role> roles = menu.getRoles();
                String [] rolesArr = new String[roles.size()];
                //我们做权限管理用的是name,但是nameZh是用来展示给用户看的。
                for (int i = 0; i < roles.size(); i++) {
                    rolesArr[i] = roles.get(i).getName();
                }

                return SecurityConfig.createList(rolesArr);
            }
        }
        // 此处的返回，会在下一步被使用。
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
