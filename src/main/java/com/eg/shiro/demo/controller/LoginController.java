package com.eg.shiro.demo.controller;

import com.eg.shiro.demo.dao.UserDao;
import com.eg.shiro.demo.pojo.UserDo;
import com.eg.shiro.demo.pojo.dto.SimpleResponse;
import com.eg.shiro.demo.pojo.enums.ResultCode;
import com.eg.shiro.demo.uitls.EncryptUtil;
import com.eg.shiro.demo.uitls.JWTUtil;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class LoginController {

    @Autowired
    private UserDao userDao;

    Logger log = LoggerFactory.getLogger(LoginController.class);

    @ResponseBody
    @PostMapping(value = "login")
    public SimpleResponse<?> login(UserDo userDo) {
        String accountName = userDo.getAccountName();
        String password = userDo.getPassword();
        UserDo dbUser = userDao.getUserByName(accountName);
        SimpleResponse<?> response;
        log.info("user login request {}", userDo);
        if (dbUser == null) {
            throw new UnknownAccountException("用户不存在");
        } else {
            String encryptPass = EncryptUtil.encrypt(password, dbUser.getSalt());
            if(!encryptPass.equals(dbUser.getPassword())){
                System.out.println("input pass:" + encryptPass);
                System.out.println("db    pass:" + dbUser.getPassword());
                throw new UnauthenticatedException("密码错误");
            }
        }

        response = new SimpleResponse<>(ResultCode.SUCCESS);
        return response.ok(JWTUtil.createToken(accountName));
    }
}
