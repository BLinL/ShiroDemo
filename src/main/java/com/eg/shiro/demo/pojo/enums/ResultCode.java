package com.eg.shiro.demo.pojo.enums;

/*自定义返回状态*/
public enum ResultCode {

    FAIL(-1,"请求失败"),
    SUCCESS(200,"请求成功"),
    LOGIN_OK(1,"登录成功"),
    UNKNOWN_ACCOUNT(2,"账户不存在"),
    PASSWORD_ERR(3,"密码错误"),
    UNAUTHENTIC(401,"认证失败"),
    LOCKED_USER(4,"用户被锁定"),
    REGUMENT_ILLEGAL_ERR(5,"非法参数");

    private int code;
    private String msg;

    ResultCode(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
