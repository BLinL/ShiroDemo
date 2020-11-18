package com.eg.shiro.demo.pojo.dto;

import com.eg.shiro.demo.pojo.enums.ResultCode;

/*自定义返回实体*/
public class SimpleResponse<T> {

    private int code;
    private T data;
    private String message;

    private static final String SUCCESS = "请求成功";
    private static final String BAD_REQUEST = "请求失败";
    private static final String NOT_FOUND = "请求找不到";

    public SimpleResponse() {
    }

    public SimpleResponse(String message) {
        this.code = ResultCode.SUCCESS.getCode();
        this.message = message;
    }

    public SimpleResponse(ResultCode code, String message) {
        this.code = code.getCode();
        this.message = message;
    }

    public SimpleResponse(ResultCode code, T data, String message) {
        this.code = code.getCode();
        this.data = data;
        this.message = message;
    }

    public static <T> SimpleResponse<T> ok() {
        SimpleResponse<T> response = new SimpleResponse<>();
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(SUCCESS);
        return response;
    }

    public static <T> SimpleResponse<T> ok(T data) {
        SimpleResponse<T> response = new SimpleResponse<>();
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(SUCCESS);
        response.setData(data);
        return response;
    }

    public static <T> SimpleResponse<T> ok(T data, String message) {
        SimpleResponse<T> response = new SimpleResponse<>();
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> SimpleResponse<T> fail(T data) {
        SimpleResponse<T> response = new SimpleResponse<>();
        response.setCode(ResultCode.FAIL.getCode());
        response.setMessage(BAD_REQUEST);
        response.setData(data);
        return response;
    }

    public static <T> SimpleResponse<T> fail(T data, String message) {
        SimpleResponse<T> response = new SimpleResponse<>();
        response.setCode(ResultCode.FAIL.getCode());
        response.setMessage(message);
        response.setData(data);
        return response;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
