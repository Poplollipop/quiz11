package com.example.quiz11.vo;

import java.util.List;

import com.example.quiz11.entity.Quiz;

public class SearchRes extends BasicRes {

    private List<Quiz> quizList;

    public SearchRes() {

    }

    public SearchRes(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    public SearchRes(int code, String message, List<Quiz> quizList) {
        super(code, message);
        this.quizList = quizList;
    }

    public SearchRes(int code, String message) {
        super(code, message);
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

}
