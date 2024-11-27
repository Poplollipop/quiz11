package com.example.quiz11.vo;

import java.time.LocalDate;
import java.util.List;

import com.example.quiz11.entity.Ques;

public class CreateReq {

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean isPublish;
    
    // 使用List表示包含多個題目
    private List<Ques> quesList;

}
