package com.example.quiz11.vo;

public class SurveyRes<T> extends BasicRes {
    private T data;

    public SurveyRes() {
        super();
    }

    public SurveyRes(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
