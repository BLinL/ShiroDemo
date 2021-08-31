package com.eg.shiro.demo.controller;

import com.eg.shiro.demo.dao.UserDao;
import com.eg.shiro.demo.pojo.UserDo;
import com.eg.shiro.demo.pojo.dto.SimpleResponse;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  request_url                    method    param
 *  localhost:port/api/user/login  POST
 *  localhost:port/api/user        GET
 *  localhost:port/api/user/xxx    GET       path_variable: xxx
 *  localhost:port/api/user        POST      x-www-form-urlencoded: {accountName:"",password:""}
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserDao userDao;

    /**
     * 需要登录
     */
    @RequiresAuthentication
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public SimpleResponse<?> method1() {
        log.info("method1 需要认证 执行成功");
        return SimpleResponse.ok();
    }

    /**
     * 需要 admin 或 test角色
     */
    @RequiresRoles(value = {"admin", "test"} ,logical = Logical.OR)
    @RequestMapping(value = "/{username}", produces = "application/json", method = RequestMethod.GET)
    public SimpleResponse<?> getUserByName(@PathVariable("username") String userName) {
        log.info("user {}", userName);
//        编码实现权限检查
//        Subject subject = SecurityUtils.getSubject();
//        subject.checkRole("user");
        return SimpleResponse.ok();
    }


    /**
     * 需要 user:add 权限
     */
    @RequiresPermissions("user:add")
    @RequestMapping(method = RequestMethod.POST)
    public SimpleResponse<?> addUser(@Validated UserDo userDo) {
        log.info("user {}", userDo);
        userDao.addUser(userDo);
        return SimpleResponse.ok(userDo);
    }

    @RequiresPermissions("user:view")
    @RequestMapping("list")
    public SimpleResponse<?> list(){
        System.out.println("userDao = " + userDao);
        return SimpleResponse.ok(getUserList());
    }

    private List<UserDo> getUserList(){
        List<UserDo> users = userDao.getAllUser();
        return users;
    }
}
