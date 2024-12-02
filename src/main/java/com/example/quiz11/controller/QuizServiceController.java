package com.example.quiz11.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz11.entity.Quiz;
import com.example.quiz11.service.QuizService;
import com.example.quiz11.vo.BasicRes;
import com.example.quiz11.vo.CreateUpdateReq;
import com.example.quiz11.vo.DeleteReq;
import com.example.quiz11.vo.SearchReq;
import com.example.quiz11.vo.SearchRes;
import com.example.quiz11.vo.SurveyRes;

@RestController

@RequestMapping(path = "quiz")
@CrossOrigin(origins = "*")
public class QuizServiceController {

    @Autowired
    private QuizService quizService;

    @PostMapping(path = "/create")
    public BasicRes create(@RequestBody CreateUpdateReq req) {
        return quizService.create(req);
    }

    @PostMapping(path = "/update")
    public BasicRes update(@RequestBody CreateUpdateReq req) {
        System.out.println("update:" + req.getId());
        return quizService.update(req);
    }

    @Transactional
    @Modifying
    @PostMapping(path = "/delete")
    public BasicRes delete(@RequestBody DeleteReq req) {
        return quizService.delete(req);
    }

    @PostMapping(path = "/search")
    public SearchRes serach(@RequestBody SearchReq req) {
        return quizService.search(req);
    }

    // 獲取問卷全部資料
    @GetMapping
    public SurveyRes<List<Quiz>> getAllSurveys() {
        List<Quiz> surveys = quizService.findAllSurveys();
        return new SurveyRes<>(200, "Success", surveys);
    }

     // 獲取問卷的基本資料
    @GetMapping(path = "/{quizId}")
    public Optional<Quiz> getQuiz(@PathVariable int quizId) {
        return quizService.getQuizById(quizId);
    }

    // 獲取問卷的問題及選項
    @GetMapping(path = "/{quizId}/questions")
    public BasicRes getQuestions(@PathVariable int quizId) {
        return quizService.getQuestionsByQuizId(quizId);
    }
}
