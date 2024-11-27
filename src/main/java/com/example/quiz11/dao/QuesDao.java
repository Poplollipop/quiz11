package com.example.quiz11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz11.entity.Ques;

@Repository
public interface QuesDao extends JpaRepository<Ques, String> {

}
