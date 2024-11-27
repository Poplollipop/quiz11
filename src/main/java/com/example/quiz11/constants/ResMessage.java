package com.example.quiz11.constants;

public enum ResMessage {

    // ;只有一個 , 做區隔
    SUCCESS(200, "Success!!!"),
    QUIZ_PARAM_ERROR(400,"Quiz Param Error!!!"),//
    QUES_PARAM_ERROR(400,"Ques Param Error!!!"),//
    QUES_TYPE_ERROR(400,"Ques Type Error!!!"),//
    DATE_ERROR(400,"Date Error!!!"),
    QUIZ_NOT_FOUND(404,"Quiz Not Found!!!"),
    QUES_NOT_FOUND(400,"Ques Not Found!!!");

    private int code;
    private String message;

    private ResMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
