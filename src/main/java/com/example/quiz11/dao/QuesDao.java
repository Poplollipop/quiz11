package com.example.quiz11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quiz11.entity.Ques;
import com.example.quiz11.entity.Quiz;

@Repository
public interface QuesDao extends JpaRepository<Ques, String> {


    @Query("select q FROM ques q where q.id = :quesId")
    Ques getById(@Param("id") int quesId);

}
