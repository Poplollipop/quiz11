package com.example.quiz11.vo;

// 此class 應為 DTO
public class Options {

    private int optionNumber;

    private String option;

    public Options() {

    }

    public Options(int optionNumber, String option) {
        this.optionNumber = optionNumber;
        this.option = option;
    }

    public int getOptionNumber() {
        return optionNumber;
    }

    public String getOption() {
        return option;
    }

}
