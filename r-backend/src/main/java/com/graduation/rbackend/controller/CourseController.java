package com.graduation.rbackend.controller;

import com.graduation.rbackend.model.Course;
import com.graduation.rbackend.service.CourseService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处理课程相关的请求（如课程推荐、课程详情等）
 */
@RestController
@RequestMapping("/api/courses")
//@CrossOrigin()
public class CourseController {

    @Autowired
    private CourseService courseService;

    // 获取所有课程
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // 根据课程id获取课程信息
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    // 添加课程
    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        return courseService.saveCourse(course);
    }
    // 删除课程
    @DeleteMapping
    public void deleteCourse(@RequestBody Course course) {
        courseService.deleteCourseById(course.getId());
    }
    // 根据教师id获取课程信息
    @GetMapping("/teacher/{id}")
    public List<Course> findByTeacherId(@PathVariable Long id) {
        return courseService.findByTeacherId(id);
    }
    // 根据课程名获取课程信息
    @GetMapping("/name/{courseName}")
    public List<Course> findByCourseName(@PathVariable String courseName) {
        return courseService.findByCourseName(courseName);
    }
}
