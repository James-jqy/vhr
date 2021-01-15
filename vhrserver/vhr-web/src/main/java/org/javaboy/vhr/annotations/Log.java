package org.javaboy.vhr.annotations;

import org.javaboy.vhr.bean.enums.BusinessType;

import java.lang.annotation.*;

/**
 * @Classname Log
 * @Description TODO
 * @Date 2021/1/14 9:38
 * @Created by Jieqiyue
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}
