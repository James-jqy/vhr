package org.javaboy.vhr.annotations;

import java.lang.annotation.*;

/**
 * 自定义注解防止表单重复提交
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmit
{

}
