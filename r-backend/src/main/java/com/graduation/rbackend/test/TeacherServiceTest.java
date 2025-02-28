package com.graduation.rbackend.test;


import com.graduation.rbackend.model.Teacher;
import com.graduation.rbackend.repository.TeacherRepositoey;
import com.graduation.rbackend.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TeacherServiceTest {
    @Mock
    private TeacherRepositoey teacherRepository;
    @InjectMocks
    private TeacherService teacherService;
    @Test
    public void testFindByDepartment() {

        List<Teacher>teachers= Arrays.asList(
                new Teacher(1L,"Dr.Smith","Smith123","Smith@example.com","Computer Science"),
                new Teacher(2L,"Dr.John","John123","John@example.com","Computer Science")


        );
        when(teacherRepository.findByDepartment("Computer Science"))
                .thenReturn(teachers);
        // 调用service
        List<Teacher> result=teacherService.findByDepartment("Computer Science");

        //验证结果
        assertEquals(2,result.size());
        assertEquals("Dr.Smith",result.get(0).getUsername());
        verify(teacherRepository,times(1)).findByDepartment("Computer Science");
    }
    @Test
    public void testSaveTeacher() {
        Teacher teacher=new Teacher(1L,"Dr.Smith","Smith123","Smith@example.com","Computer Science");
        when(teacherRepository.save(teacher)).thenReturn(teacher);
        // 调用service
        Teacher result=teacherService.saveTeacher(teacher);

        //验证结果
        assertEquals("Dr.Smith",result.getUsername());
        assertEquals("Smith123",result.getPassword());
        verify(teacherRepository,times(1)).save(teacher);
    }
}
