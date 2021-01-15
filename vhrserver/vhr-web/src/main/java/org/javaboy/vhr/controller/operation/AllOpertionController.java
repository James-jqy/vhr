package org.javaboy.vhr.controller.operation;

import org.javaboy.vhr.annotations.Log;
import org.javaboy.vhr.bean.Employee;
import org.javaboy.vhr.bean.RespPageBean;
import org.javaboy.vhr.bean.SysOperLog;
import org.javaboy.vhr.bean.enums.BusinessType;
import org.javaboy.vhr.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Classname AllOpertion
 * @Description TODO
 * @Date 2021/1/14 13:56
 * @Created by Jieqiyue
 */
@RestController
@RequestMapping("/system/log")
public class AllOpertionController {
    @Autowired
    SysOperLogService sysOperLogService;

    @GetMapping("/")
    public RespPageBean getOperByPage(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer size){
        return sysOperLogService.getOperByPage(page,size);
    }


}
