package com.xxx.server.exception;

import com.xxx.server.pojo.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public RespBean sqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("该数据有关联操作,删除失败");
        }
        return RespBean.error("数据库异常，操作失败");
    }
}
