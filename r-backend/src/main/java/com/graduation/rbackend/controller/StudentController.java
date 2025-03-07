package com.graduation.rbackend.controller;

import com.graduation.rbackend.entity.Student;
import com.graduation.rbackend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {


    private final StudentService studentService;


    // 获取所有学生
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
    // 根据id获取学生(防止空指针异常）
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    // 添加学生
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student savedStudent = studentService.saveStudent(student);;
        return ResponseEntity.ok(savedStudent);
    }
    // 删除学生
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    // 根据专业查询学生
    @GetMapping("/major/{major}")
    public List<Student> findByMajor(@PathVariable String major) {
        return studentService.findByMajor(major);
    }

}
