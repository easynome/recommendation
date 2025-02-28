package com.graduation.rbackend.service;


import com.graduation.rbackend.model.Teacher;
import com.graduation.rbackend.repository.TeacherRepositoey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepositoey teacherRepository;

    //保存教师
    public Teacher saveTeacher(Teacher teacher)
    {
        return teacherRepository.save(teacher);
    }
    //查询所有教师
    public List<Teacher> getAllTeachers()
    {
        return teacherRepository.findAll();
    }
    //根据院系查询教师
    public List<Teacher> findByDepartment(String department)
    {
        return teacherRepository.findByDepartment(department);
    }
    //根据id删除教师
    public void deleteTeacherById(Long id)
    {
        teacherRepository.deleteById(id);
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).get();
    }
}
