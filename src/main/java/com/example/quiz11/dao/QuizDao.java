package com.example.quiz11.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quiz11.entity.Quiz;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {

    @Query(value = "select id, name, description, start_date, end_date, published from quiz q where q.id = ?1", nativeQuery = true)
    Optional<Quiz> getById(@Param("id") int quizId);

    @Query(value = "select q from quiz q where q.id = :id", nativeQuery = true)
    Quiz getById1(@Param("id") int id);

    @Transactional
    @Modifying
    @Query(value = "delete from quiz where id in(?1)", nativeQuery = true)
    public void deleteByIdIn(List<Integer> idlist);

    @Query(value = "select id, name, description, start_date, end_date, published from quiz where name like %?1% " //
            + " and start_date >= ?2 and end_date <= ?3", nativeQuery = true)
    public List<Quiz> getByConditions(String name, LocalDate starDate, LocalDate endDate);

    // JPA 語法
    // public Quiz findByIdAndPublishedTrue(int quizId);

    // 上下行同義
    // SQL 語法 published = true 可以 published is true; null 也適用
    @Query(value = "select id, name, description, start_date, end_date, published from quiz where id = ?1 and published = true", nativeQuery = true)
    public Quiz getByIdAndPublishedTrue(int quizId);


    // 新增填寫時間必須在開始時間及結束時間範圍內
    // @Query(value = "select id, name, description, start_date, end_date, published
    // from quiz where id = ?1 and published = true and start_date <= ?2 and
    // end_date >= ?3", nativeQuery = true)
    // public Quiz getByIdAndPublishedTrueBetween(int quizId, LocalDate fillinDate);
}
