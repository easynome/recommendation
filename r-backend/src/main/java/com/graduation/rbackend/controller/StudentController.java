package com.graduation.rbackend.controller;

import com.graduation.rbackend.model.Student;
import com.graduation.rbackend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;


    // 获取所有学生
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
    // 根据id获取学生
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // 添加学生
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }
    // 删除学生
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
    }

    // 根据专业查询学生
    @GetMapping("/{major}")
    public List<Student> findByMajor(@PathVariable String major) {
        return studentService.findByMajor(major);
    }

}
