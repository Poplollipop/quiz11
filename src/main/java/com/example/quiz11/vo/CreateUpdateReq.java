package com.example.quiz11.vo;

import java.time.LocalDate;
import java.util.List;

import com.example.quiz11.entity.Ques;
import com.example.quiz11.entity.Quiz;

public class CreateUpdateReq extends Quiz {

    // 使用List表示包含多個題目

    private List<Ques> quesList;

    public CreateUpdateReq() {

    }

    public CreateUpdateReq(int id, String name, String description, //
            LocalDate startDate, LocalDate endDate, boolean published,
            List<Ques> quesList) {
        super(id, name, description, startDate, endDate, published);
        this.quesList = quesList;
    }

    public List<Ques> getQuesList() {
        return quesList;
    }

}
