package com.example.quiz11.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quiz11.entity.Ques;

@Repository
public interface QuesDao extends JpaRepository<Ques, String> {

    @Query(value = "select q FROM ques q where q.id = :quesId", nativeQuery = true)
    Ques getById(@Param("quesId") int quesId);

    @Transactional
    @Modifying
    @Query(value = "delete from ques where quiz_id =?1", nativeQuery = true)
    public int deleteByQuizId(int quizId);

    @Transactional
    @Modifying
    @Query(value = "delete from ques where quiz_id in(?1)", nativeQuery = true)
    public void deleteByQuizIdIn(List<Integer> quizIdList);

    @Query(value = "select quiz_id, ques_id, ques_name, type, required, options FROM ques q where q.quiz_id = ?1", nativeQuery = true)
    public List<Ques> getByQuizId(int quizId);

}
