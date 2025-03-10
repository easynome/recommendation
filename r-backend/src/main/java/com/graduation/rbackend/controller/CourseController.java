package com.graduation.rbackend.controller;

import com.graduation.rbackend.entity.Course;
import com.graduation.rbackend.service.CourseService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处理课程相关的请求（如课程推荐、课程详情等）
 */
@Slf4j
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
//@CrossOrigin()
public class CourseController {


    private final CourseService courseService;

    // 获取所有课程

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }
//    @GetMapping
//    public List<Course> getAllCourses() {
//        return courseService.getAllCourses();
//    }

    // 根据课程id获取课程信息
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }
    // 添加课程
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.addCourse(course));
    }

    //更新课程信息
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id,@RequestBody Course courseDetails) {
        log.info("接收到的 JSON 数据: {}", courseDetails);
        return ResponseEntity.ok(courseService.updateCourse(id,courseDetails));
    }
    // 删除课程
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable Long id) {
        courseService.deleteCourseById(id);
        return ResponseEntity.ok().body("课程已删除");
    }
    // 根据教师id获取课程信息
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Course>> findByTeacherId(@PathVariable Long teacherId) {
        return ResponseEntity.ok(courseService.getCourseByTeacherId(teacherId));
    }
    // 根据课程名获取课程信息
    @GetMapping("/name/{courseName}")
    public ResponseEntity<Course> getCourseByName(@PathVariable String courseName) {
        return courseService.getCourseByName(courseName)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
