//package com.graduation.rbackend.test;
//
//import com.graduation.rbackend.entity.Course;
//import com.graduation.rbackend.entity.Teacher;
//import com.graduation.rbackend.repository.CourseRepository;
//import com.graduation.rbackend.service.CourseService;
//import org.junit.jupiter.api.BeforeEach;
//import org.testng.annotations.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class CourseServiceTest {
//    @Mock
//    private CourseRepository courseRepository;
//    @InjectMocks
//    private CourseService courseService;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }
//    @Test
//    void testGetAllCourses(){
//        Teacher teacher=new Teacher(1L, "John Doe", "John123", "john@example.com", "Computer Science", "Professor");
//        List<Course> courses= Arrays.asList(
//                new Course(1L, "Java Programming", 3, teacher),
//                new Course(2L, "Data Structures", 3, teacher)
//
//        );
//        when(courseRepository.findAll()).thenReturn(courses);
//        // 调用service方法
//        List<Course> result=courseService.getAllCourses();
//
//        //验证结果
//        assertEquals(courses,result);
//        assertEquals(2,result.size());
//        assertEquals("Java Programming",result.get(0).getCourseName());
//        verify(courseRepository,times(1)).findAll();
//    };
//
//}
