package com.example.quiz11.service;

import java.util.List;

import com.example.quiz11.entity.Quiz;
import com.example.quiz11.vo.BasicRes;
import com.example.quiz11.vo.CreateUpdateReq;
import com.example.quiz11.vo.DeleteReq;
import com.example.quiz11.vo.SearchReq;
import com.example.quiz11.vo.SearchRes;

public interface QuizService {

    public BasicRes create(CreateUpdateReq req);

    public BasicRes update(CreateUpdateReq req);

    public BasicRes delete(DeleteReq req);

    public SearchRes serach(SearchReq req);

    public List<Quiz> findAllSurveys();

}
