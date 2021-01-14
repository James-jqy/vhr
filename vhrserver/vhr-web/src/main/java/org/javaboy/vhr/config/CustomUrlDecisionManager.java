package org.javaboy.vhr.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Classname MyDesicionManager
 * @Description 这个类和MyFilter是相互联系的。这个类是获取到已经登录的用户的权限，和上面一个类获取到接口需要要的权限作比较的类。
 * @Date 2020/12/17 19:02
 * @Created by Jieqiyue
 */

@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {

    // 第三个参数collection是上一个MyFilter中返回的需要的角色(访问这个资源所需要的角色)。
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection) {
            String attribute = configAttribute.getAttribute();
            // 如果上一步返回的“ROLE_LOGIN”，那么代表是一个为匹配上需要角色的路径。
            if ("ROLE_LOGIN".equals(attribute)){
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登陆，请登录！");
                }else{
                    return;
                }
            }
            // 获取到已经登录用户的权限。到这一步，用户还是有可能是匿名（未登录）用户。
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(attribute)){
                    return ;
                }
            }

        }

        throw new AccessDeniedException("权限不足，请联系管理员。");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
