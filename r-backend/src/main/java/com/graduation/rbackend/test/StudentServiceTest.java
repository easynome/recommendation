//package com.graduation.rbackend.test;
//
//import com.graduation.rbackend.entity.Student;
//import com.graduation.rbackend.repository.StudentRepository;
//import com.graduation.rbackend.service.StudentService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
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
//public class StudentServiceTest {
//    @Mock
//    private StudentRepository studentRepository;
//
//    @InjectMocks
//    private StudentService studentService;
//
//    //初始化@Mock注解修饰的mock对象，并初始化@InjectMocks注解修饰的类中的mock对象
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }
//    @Test
//    void testGetAllStudents(){
//        // 模拟返回数据
//        List<Student> students= Arrays.asList(
//                new Student(1L,"John","John123","john@example.com","Computer Science",3),
//                new Student(2L,"Jane","Jane123","jane@example.com","Computer Science",2)
//        );
//        when(studentRepository.findAll()).thenReturn(students);
//
//        // 调用service
//        List<Student> result = studentService.getAllStudents();
//
//        assertEquals(2,result.size());
//        verify(studentRepository, times(1))
//                .findAll();
//    }
//
//    @Test
//    void testSaveStudents(){
//        //模拟保存学生
//        Student student = new Student(1L,"John","John123","john@example.com","Computer Science",2);
//        when(studentRepository.save(any(Student.class))).thenReturn(student);
//
//        // 调用service
//        Student result = studentService.saveStudent(student);
//
//        //验证结果
//        assertEquals(student,result);
//        verify(studentRepository,times(1)).save(student);//验证调用次数
//    }
//}
