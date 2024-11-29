package com.example.quiz11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz11.service.QuizService;
import com.example.quiz11.vo.BasicRes;
import com.example.quiz11.vo.CreateUpdateReq;
import com.example.quiz11.vo.DeleteReq;
import com.example.quiz11.vo.SearchReq;
import com.example.quiz11.vo.SearchRes;

@CrossOrigin(value="http://localhost:4200/")
@RestController
@RequestMapping(path = "quiz")
public class QuizServiceController {

    @Autowired
    private QuizService quizService;

    @PostMapping(path = "/create")
    public BasicRes create(@RequestBody CreateUpdateReq req) {
        return quizService.create(req);
    }

    @PostMapping(path = "/update")
    public BasicRes update(@RequestBody CreateUpdateReq req) {
        return quizService.update(req);
    }

    @PostMapping(path = "/delete")
    public BasicRes delete(DeleteReq req) {
        return quizService.delete(req);
    }

    @PostMapping(path = "/serach")
    public SearchRes serach(SearchReq req) {
        return quizService.serach(req);
    }

}
