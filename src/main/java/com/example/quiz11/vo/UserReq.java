package com.example.quiz11.vo;

import com.example.quiz11.entity.User;

public class UserReq extends User{
    // 建構子
    public UserReq(int userId,String userName, String email, String password, boolean userPermit) {
        super(userId, userPermit, userName, email, password);  // 呼叫父類別的建構子
    }

}
