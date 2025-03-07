package com.graduation.rbackend.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "admins")
public class Admin extends BaseUser {
    private String permissionLevel;
    //管理员权限级别（例如：普通管理员、超级管理员）

}
