package com.example.quiz11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz11.service.UserService;
import com.example.quiz11.vo.BasicRes;
import com.example.quiz11.vo.LoginReq;
import com.example.quiz11.vo.UserReq;

@RestController
@RequestMapping(path = "account")
@CrossOrigin(origins = "*")
public class UserServiceController {
    
    @Autowired
    private UserService userService;

    @PostMapping(path="/register")
    public BasicRes addAcount(@RequestBody UserReq req){
        return userService.addAccount(req);
    }

    @PostMapping(path="/login")
    public BasicRes login(@RequestBody LoginReq req){
        return userService.login(req);
    }

}
