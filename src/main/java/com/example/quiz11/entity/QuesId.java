package com.example.quiz11.entity;

import java.io.Serializable;

public class QuesId implements Serializable {

    private int quizId;

    private int quesId;

    public int getQuizId() {
        return quizId;
    }

    public int getQuesId() {
        return quesId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setQuesId(int quesId) {
        this.quesId = quesId;
    }

}
