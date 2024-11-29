package com.example.quiz11.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.quiz11.constants.ResMessage;
import com.example.quiz11.dao.UserDao;
import com.example.quiz11.entity.User;
import com.example.quiz11.service.UserService;
import com.example.quiz11.vo.BasicRes;
import com.example.quiz11.vo.UserReq;

@Service
public class UserImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public BasicRes addAccount(UserReq req) {
        BasicRes checkResult = checkParams(req);
        if (checkResult != null) {
            return checkResult;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User(
                req.getUserId(),
                req.getUserPermit(),
                req.getUserName(),
                req.getEmail(),
                encoder.encode(req.getPassword()));
       
            userDao.save(user);
            return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
       
    }

    private BasicRes checkParams(UserReq req) {
        if (!StringUtils.hasText(req.getUserName()) || !StringUtils.hasText(req.getEmail()) || //
                !StringUtils.hasText(req.getPassword())) {
            return new BasicRes(ResMessage.ACCOUNT_PARAM_ERROR.getCode(), ResMessage.ACCOUNT_PARAM_ERROR.getMessage());
        }
        Optional<User> opName = userDao.findByUserName(req.getUserName());

        if (opName.isPresent()) {
            return new BasicRes(ResMessage.ACCOUNT_ALREADY_EXIST.getCode(),
                    ResMessage.ACCOUNT_ALREADY_EXIST.getMessage());
        }

        Optional<User> opEmail = userDao.findByEmail(req.getEmail());

        if (opEmail.isPresent()) {
            return new BasicRes(ResMessage.EMAIL_ALREADY_EXISTS.getCode(), ResMessage.EMAIL_ALREADY_EXISTS.getMessage());
        }
        return null;
    }

}
