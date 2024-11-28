package com.example.quiz11.vo;

import java.time.LocalDate;

public class SearchReq {

    private String name;

    private LocalDate starDate;

    private LocalDate endDate;

    public SearchReq() {

    }

    public SearchReq(String name, LocalDate starDate, LocalDate endDate) {
        this.name = name;
        this.starDate = starDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStarDate() {
        return starDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}
