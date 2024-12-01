package com.example.quiz11.service;

import com.example.quiz11.vo.BasicRes;
import com.example.quiz11.vo.LoginReq;
import com.example.quiz11.vo.UserReq;

public interface UserService {
    

    public BasicRes addAccount(UserReq req);

    public BasicRes login(LoginReq req);

}