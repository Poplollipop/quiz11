package com.example.quiz11.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "feedback")
@IdClass(value=FeedbackId.class)
public class Feedback {
    @Id
    @Column(name = "quiz_id")
    private int quizId;

    @Id
    @Column(name = "ques_id")
    private int quesId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    // 答案有多個，陣列轉字串
    @Column(name = "answer")
    private String answer;

    @Column(name = "fillin_date")
    private LocalDateTime fillinDate;

    public Feedback() {

    }

    public Feedback(int quizId, int quesId, String name, String phone, String email, int age, String answer,
            LocalDateTime fillinDate) {
        this.quizId = quizId;
        this.quesId = quesId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.answer = answer;
        this.fillinDate = fillinDate;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getquesId() {
        return quesId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getAnswer() {
        return answer;
    }

    public LocalDateTime getFillinDate() {
        return fillinDate;
    }

}
