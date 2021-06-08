package com.dictionary.demo.controller;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public class MemberForm {
    private String email;
    private String password;
    private String confirm_password;
    private String lname;
    private String fname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String gender;

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

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isSamePassword(){
        return this.password.equals(this.confirm_password);
    }
}
