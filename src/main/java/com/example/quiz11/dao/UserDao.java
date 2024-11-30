package com.example.quiz11.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz11.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);

}
