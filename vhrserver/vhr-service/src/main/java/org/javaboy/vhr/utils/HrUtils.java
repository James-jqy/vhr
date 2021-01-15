package org.javaboy.vhr.utils;

import org.javaboy.vhr.bean.Hr;
// service 模块不能导入web模块
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Classname HrUtils
 * @Description TODO
 * @Date 2020/12/22 10:45
 * @Created by Jieqiyue
 */

public class HrUtils {
    public static Hr getCurrentHr(){
        return (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
