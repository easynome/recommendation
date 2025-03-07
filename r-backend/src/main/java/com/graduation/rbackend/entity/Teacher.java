package com.graduation.rbackend.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher extends BaseUser {
    private String position;
    private String department;


}
