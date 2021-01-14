package org.javaboy.vhr.controller;

import org.javaboy.vhr.bean.Hr;
import org.javaboy.vhr.service.HrService;
import org.javaboy.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname ChatController
 * @Description TODO
 * @Date 2021/1/5 20:23
 * @Created by Jieqiyue
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    HrService hrService;

    @GetMapping("/hrs")
    public List<Hr> getAllHrs(){
        return hrService.getAllHrsExceptCurrentHr(HrUtils.getCurrentHr().getId());
    }
}
