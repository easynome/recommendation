package com.graduation.rbackend.service;

import com.graduation.rbackend.entity.Course;
import com.graduation.rbackend.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 提供课程查询、分类推荐等功能
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    // 保存课程
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }
    // 获取所有课程
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    // 根据id获取课程
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).get();
    }

    //添加课程
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    //更新课程
    public Course updateCourse(Long id,Course courseDetails) {
        log.info("🟠 接收到的 Course 数据: {}", courseDetails);
        if (courseDetails.getCourseName() == null) {
            log.warn("❗ courseName 为空，可能的原因：JSON 键名拼写错误 / Lombok 未生效 / 数据未成功解析");
        }

        Course course = getCourseById(id);
//        if(courseDetails.getCourseName()==null ||
//        courseDetails.getCourseName().isEmpty()){
//            throw new IllegalArgumentException("课程名称不能为空");
//        }
        course.setCourseName(courseDetails.getCourseName());
        course.setDescription(courseDetails.getDescription());
        course.setTeacherId(courseDetails.getTeacherId());
        course.setCredits(courseDetails.getCredits());
        return courseRepository.save(course);
    }
    // 根据id删除课程
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }


    // 根据教师id获取课程
    public List<Course> getCourseByTeacherId(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }
    // 根据课程名搜索课程
    public Optional<Course> getCourseByName(String courseName) {
        return courseRepository.findByCourseName(courseName);
    }


}
