package org.javaboy.vhr.controller.config;

import org.javaboy.vhr.bean.Menu;
import org.javaboy.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname SystemConfigController
 * @Description TODO
 * @Date 2020/12/12 18:45
 * @Created by Jieqiyue
 */

@RestController
@RequestMapping("/system/config")
public class SystemConfigController {

    @Autowired
    MenuService menuService;
    /**
     * 注意，此处不能使用前端传来的id作为参数，前端的参数不可信。
     * @return
     */
    @GetMapping("/menu")
    public List<Menu> getMenusByHrId(){
        return menuService.getMenusByHrId();
    }
}
