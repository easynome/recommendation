package com.graduation.rbackend.service;

import com.graduation.rbackend.model.Course;
import com.graduation.rbackend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 提供课程查询、分类推荐等功能
 */
@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    // 保存课程
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }
    // 获取所有课程
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    // 根据教师id获取课程
    public List<Course> findByTeacherId(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }
    // 根据课程名搜索课程
    public List<Course> findByCourseName(String courseName) {
        return courseRepository.findByCourseNameContaining(courseName);
    }

    // 根据id获取课程
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).get();
    }

    // 根据id删除课程
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }
}
