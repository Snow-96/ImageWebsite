package com.csci430.website.entity;

import com.csci430.website.vo.RegisterVO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private String birth;

    public User() {
    }

    public User(RegisterVO registerVO) {
        this.email = registerVO.getEmail();
        this.password = registerVO.getPassword();
        this.lastName = registerVO.getLastName();
        this.firstName = registerVO.getFirstName();
        this.birth = registerVO.getBirth();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
