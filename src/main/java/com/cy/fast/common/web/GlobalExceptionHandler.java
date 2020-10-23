package com.cy.fast.common.web;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cy.fast.common.vo.JsonResult;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     *运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public JsonResult dohandleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return new JsonResult(e);
    }
    /**
     *shiro框架异常
     */
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public JsonResult doHandleShiroException(ShiroException e) {
        JsonResult r = new JsonResult();
        r.setState(0);
        if (e instanceof UnknownAccountException) {
            r.setMessage("账户不存在");
        } else if (e instanceof LockedAccountException) {
            r.setMessage("账户已被禁用");
        } else if (e instanceof IncorrectCredentialsException) {
            r.setMessage("密码不正确");
        } else if (e instanceof AuthorizationException) {
            r.setMessage("没有此操作权限");
        } else {
            r.setMessage("系统维护中");
        }
        e.printStackTrace();
        return r;
    }
}
