package com.graduation.rbackend.repository;

import com.graduation.rbackend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 存储课程数据、查询课程等
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

    // 根据课程名查找
    Optional<Course> findByCourseName(String CourseName);
    //根据教师ID查找课程
    List<Course> findByTeacherId(Long teacherId);
    //根据课程名称模糊查询
    List<Course> findByCourseNameContaining(String courseName);

//    List<Course> findByTeacherId(Long teacherId);
}
