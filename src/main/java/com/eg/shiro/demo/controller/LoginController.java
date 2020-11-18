package com.eg.shiro.demo.controller;

import com.eg.shiro.demo.pojo.UserDo;
import com.eg.shiro.demo.pojo.dto.SimpleResponse;
import com.eg.shiro.demo.pojo.enums.ResultCode;
import com.eg.shiro.demo.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/user")
public class LoginController {

    Logger log = LoggerFactory.getLogger(LoginController.class);

    @ResponseBody
    @PostMapping(value = "login")
    public SimpleResponse<?> login(@RequestBody @Validated UserDo userDo) {
        String accountName = userDo.getAccountName();
        String password = userDo.getPassword();
        log.info("user login request {}" , userDo);

        Subject subject = SecurityUtils.getSubject();

        SimpleResponse<?> response = null;
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(accountName, password);
            try {
                subject.login(token);
                log.info("isAuthenticated? {} {} ", subject.isAuthenticated(),"登录成功！");
                response = new SimpleResponse<>(ResultCode.LOGIN_OK, ResultCode.LOGIN_OK.getMsg());
            } catch (UnknownAccountException uae) {
                response = new SimpleResponse<>(ResultCode.UNKNOWN_ACCOUNT, ResultCode.UNKNOWN_ACCOUNT.getMsg());
                log.info("认证失败 {}",uae.getMessage());
            } catch (IncorrectCredentialsException ice) {
                response = new SimpleResponse<>(ResultCode.PASSWORD_ERR, ResultCode.PASSWORD_ERR.getMsg());
                log.info("认证失败 {}",ice.getMessage());
            } catch (LockedAccountException lae) {
                response = new SimpleResponse<>(ResultCode.UNKNOWN_ACCOUNT, "账户被锁定");
                log.info("认证失败 {}",lae.getMessage());
            } catch (AuthenticationException ae) {
                response = new SimpleResponse<>(ResultCode.FAIL, ae.getMessage());
                log.info("认证失败 {}",ae.getMessage());
            }
        }else{
            log.info("已登录！");
            response = SimpleResponse.ok();
        }

        return response;
    }
}
