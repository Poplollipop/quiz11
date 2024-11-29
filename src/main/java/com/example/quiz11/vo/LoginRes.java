package com.example.quiz11.vo;

public class LoginRes {
    String message;
    Boolean status;

    public LoginRes(){

    }

    public LoginRes(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }
    
}
