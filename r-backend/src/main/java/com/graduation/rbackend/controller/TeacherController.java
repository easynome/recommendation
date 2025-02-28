package com.graduation.rbackend.controller;

import com.graduation.rbackend.model.Teacher;
import com.graduation.rbackend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // 查询所有老师
    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }
    // 根据id查询老师
    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }
    // 根据系别查询老师
    @GetMapping("/department/{department}")
    public List<Teacher> findByDepartment(@PathVariable String department) {
        return teacherService.findByDepartment(department);
    }
    // 添加老师
    @PostMapping
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        return teacherService.saveTeacher(teacher);
    }
    //根据id删除老师
    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
    }


}
