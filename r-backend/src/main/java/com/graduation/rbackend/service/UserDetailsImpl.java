package com.graduation.rbackend.service;

import com.graduation.rbackend.entity.Admin;
import com.graduation.rbackend.entity.Student;
import com.graduation.rbackend.entity.Teacher;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

//    public UserDetailsImpl(BaseUser user) {
//        this.id = user.getId();
//        this.username = user.getUsername();
//        this.password = user.getPassword();
//        this.email = user.getEmail();
//        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
//    }

    public UserDetailsImpl(Student student) {
        this.id = student.getId();
        this.username = student.getUsername();
        this.password = student.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + student.getRole().name()));
        this.email = student.getEmail();
    }

    // Teacher 和 Admin 的构造器重载
    public UserDetailsImpl(Teacher teacher) {
        this.id = teacher.getId();
        this.username = teacher.getUsername();
        this.password = teacher.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + teacher.getRole().name()));
        this.email = teacher.getEmail();
    }

    public UserDetailsImpl(Admin admin) {
        this.id = admin.getId();
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + admin.getRole().name()));
        this.email = admin.getEmail();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}