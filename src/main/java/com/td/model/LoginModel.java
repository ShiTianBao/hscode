package com.td.model;

public class LoginModel {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
