package com.example.quiz11.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "ques")
@IdClass(value = QuesId.class)
public class Ques {

    @Id
    @Column(name = "ques_id")
    private int quesId;

    @Id
    @Column(name = "quiz_id")
    private int quizId;

    @Column(name = "ques_name")
    private String quesName;

    @Column(name = "type")
    private String type;

    @Column(name = "required")
    private boolean required;

    @Column(name = "options")
    private String options;

    public Ques() {

    }

    public Ques(String options, int quesId, String quesName, int quizId, boolean required, String type) {
        this.options = options;
        this.quesId = quesId;
        this.quesName = quesName;
        this.quizId = quizId;
        this.required = required;
        this.type = type;
    }

    public int getQuesId() {
        return quesId;
    }

    public int getQuizId() {
        return quizId;
    }

    public String getQuesName() {
        return quesName;
    }

    public String getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public String getOptions() {
        return options;
    }

}
