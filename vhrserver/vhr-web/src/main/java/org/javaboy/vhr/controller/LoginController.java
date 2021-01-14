package org.javaboy.vhr.controller;

import org.javaboy.vhr.bean.RespBean;
import org.javaboy.vhr.config.VerificationCode;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Classname LoginController
 * @Description TODO
 * @Date 2020/12/11 11:09
 * @Created by Jieqiyue
 */
@RestController
public class LoginController {

    @GetMapping("/login")
    public RespBean login(){
        return RespBean.error("尚未登陆，请登录");
    }

    @GetMapping("/verifyCode")
    public void verifyCode(HttpSession session, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        session.setAttribute("verify_code",text);
        VerificationCode.output(image,resp.getOutputStream());
    }
}
