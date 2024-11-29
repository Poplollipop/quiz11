package com.example.quiz11.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "user_permit")
    private boolean userPermit;

    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "password")
    private String password;

    public User(){

    }

    public User(int userId, boolean userPermit, String userName, String email, String password) {
        this.userId = userId;
        this.userPermit = userPermit;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    

    public boolean getUserPermit() {
        return userPermit;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId() {
        return userId;
    }
    

}
