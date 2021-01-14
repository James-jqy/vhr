package org.javaboy.vhr.controller;

import org.javaboy.vhr.bean.Hr;
import org.javaboy.vhr.utils.HrUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname PersonInfo
 * @Description TODO
 * @Date 2021/1/9 21:40
 * @Created by Jieqiyue
 */
@RestController
@RequestMapping("/person/info")
public class PersonInfo {

    @GetMapping("/")
    public Hr getHr(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = details.getRemoteAddress();
        System.out.println("remoteAddress = " + remoteAddress);
        return null;
    }

}
