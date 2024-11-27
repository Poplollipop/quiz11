package com.example.quiz11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz11.entity.Quiz;

@Repository
public interface QuizDao extends JpaRepository<Quiz, String> {

}
