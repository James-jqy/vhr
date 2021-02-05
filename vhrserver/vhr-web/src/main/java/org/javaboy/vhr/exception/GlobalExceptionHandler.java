package org.javaboy.vhr.exception;

import org.javaboy.vhr.bean.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Logger;


/**
 * @Classname GlobalExceptionHandler
 * @Description TODO
 * @Date 2020/12/18 17:29
 * @Created by Jieqiyue
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public RespBean sqlException(SQLException e){
        if (e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.error("该数据有关联数据，操作失败！");
        }

        System.out.println(e.getMessage());
        return RespBean.error("数据库异常，操作失败！");
    }

    @ExceptionHandler(RepartSubmitException.class)
    public RespBean idempotentException(RepartSubmitException e) {
        return RespBean.error("请勿重复提交表单！");
    }
}
