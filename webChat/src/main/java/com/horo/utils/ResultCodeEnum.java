package com.horo.utils;

/**
 * 统一返回结果状态信息类
 *
 */
public enum ResultCodeEnum {

    SUCCESS(200,"执行成功"),
    USERNAME_ERROR(501,"用户名有误"),
    PASSWORD_ERROR(503,"密码错误"),
    NOTLOGIN(504,"未登录"),
    USERNAME_USED(505,"用户名已被占用");

    private Integer code;
    private String message;
    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
