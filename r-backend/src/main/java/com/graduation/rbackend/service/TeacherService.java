package com.graduation.rbackend.service;


import com.graduation.rbackend.entity.Teacher;

import com.graduation.rbackend.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    //查询所有教师
    public List<Teacher> getAllTeachers()
    {
        return teacherRepository.findAll();
    }
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }
    //根据院系查询教师
    public List<Teacher> findByDepartment(String department)
    {
        return teacherRepository.findByDepartment(department);
    }
    //根据id删除教师
    //保存教师
    public Teacher saveTeacher(Teacher teacher)
    {
        return teacherRepository.save(teacher);
    }


    public void deleteTeacherById(Long id)
    {
        teacherRepository.deleteById(id);
    }


    public Optional<Teacher> getTeacherByUsername(String username) {
        return teacherRepository.findByUsername(username);
    }
}
