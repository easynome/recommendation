package com.graduation.rbackend.controller;

import com.graduation.rbackend.entity.Teacher;
import com.graduation.rbackend.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {


    private final TeacherService teacherService;

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
        Teacher savedTeacher = teacherService.saveTeacher(teacher);
        return ResponseEntity.ok(savedTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacherById(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/department/{department}")
    public List<Teacher> findByDepartment(@PathVariable String department) {
        return teacherService.findByDepartment(department);
    }


}
