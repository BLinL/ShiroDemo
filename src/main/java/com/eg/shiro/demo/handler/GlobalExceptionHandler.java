package com.eg.shiro.demo.handler;

import com.eg.shiro.demo.pojo.dto.SimpleResponse;
import com.eg.shiro.demo.pojo.enums.ResultCode;
import com.eg.shiro.demo.shiro.realm.CustomRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/*全局异常处理*/
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomRealm.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public SimpleResponse<?> handleIllegalArgumentException(IllegalArgumentException iae){
        return new SimpleResponse<>(ResultCode.FAIL,iae.getMessage());
    }

    /*捕获访问需要授权的页面时抛出的异常*/
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public SimpleResponse<?> handleUnauthorizedException(UnauthorizedException ue){
        LOG.info("授权失败 您没有权限");
        return new SimpleResponse<>(ResultCode.UNAUTHENTIC,ResultCode.UNAUTHENTIC.getMsg());
    }

    /*捕获访问需要认证的页面时抛出的异常*/
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthenticatedException.class, AuthenticationException.class})
    public SimpleResponse<?> handleUnauthorizedException(AuthorizationException ae){
        LOG.info("认证失败 请登录");
        return new SimpleResponse<>(ResultCode.UNAUTHENTIC,ResultCode.UNAUTHENTIC.getMsg());
    }

    /*请求参数验证不合法捕获抛出的异常*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public SimpleResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errorMsg = new ArrayList<>();

        for (FieldError error : fieldErrors) {
            errorMsg.add(error.getDefaultMessage());
        }
        return new SimpleResponse<>(ResultCode.FAIL,errorMsg.get(0));
    }
}
