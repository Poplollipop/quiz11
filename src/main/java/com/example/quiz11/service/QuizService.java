package com.example.quiz11.service;

import java.util.List;
import java.util.Optional;

import com.example.quiz11.entity.Quiz;
import com.example.quiz11.vo.BasicRes;
import com.example.quiz11.vo.CreateUpdateReq;
import com.example.quiz11.vo.DeleteReq;
import com.example.quiz11.vo.FillinReq;
import com.example.quiz11.vo.SearchReq;
import com.example.quiz11.vo.SearchRes;

public interface QuizService {

    public BasicRes create(CreateUpdateReq req);

    public BasicRes update(CreateUpdateReq req);

    public BasicRes delete(DeleteReq req);

    public SearchRes search(SearchReq req);

    public List<Quiz> findAllSurveys();

    public BasicRes fillin(FillinReq req);

    public Optional<Quiz> getQuizById(int quizId);  // 根據 quizId 獲取問卷基本資料

    public BasicRes getQuestionsByQuizId(int quizId);
}
