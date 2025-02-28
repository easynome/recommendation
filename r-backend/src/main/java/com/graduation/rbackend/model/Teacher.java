package com.graduation.rbackend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Teacher")
public class Teacher extends User {
    private String position;
    private String department;

    public Teacher(Long id, String name, String password, String email,String major, String position) {
    }

    public Teacher(Long id, String name, String password, String email, String position) {
    }

    public Teacher() {

    }


    //Getters and Setters
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
