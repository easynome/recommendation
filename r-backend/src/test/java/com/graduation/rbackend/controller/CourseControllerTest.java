package com.graduation.rbackend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.rbackend.config.SecurityConfig;
import com.graduation.rbackend.entity.Course;
import com.graduation.rbackend.security.jwt.JwtTokenProvider;
import com.graduation.rbackend.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
@WebMvcTest(CourseController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = true) // ✅ 启用 Security 过滤器
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc ;

    @MockitoBean
    private CourseService courseService;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    private List<Course> mockCourses;
    private static final String validToken="validToken";


    @BeforeEach
    void setUp() {
        mockCourses = List.of(
                new Course(1L, "Java入门", "基础Java编程", 3, 1L, "['编程','Java']", 4.5f, 10, "thumbnail1.jpg"),
                new Course(2L, "Spring Boot", "构建Web应用", 4, 2L, "['Web','Spring Boot']", 4.8f, 15, "thumbnail2.jpg")
        );
        log.info("✅ 正在 Mock JwtTokenProvider.validateToken(validToken) 为 true");
        when(jwtTokenProvider.validateToken(validToken)).thenReturn(true);
        log.info("✅ 正在 Mock JwtTokenProvider.getAuthentication(validToken) 为 ADMIN 权限");
        when(jwtTokenProvider.getAuthentication(validToken)).thenReturn(
                new UsernamePasswordAuthenticationToken("user",null,
                        Collections.singletonList(new
                                SimpleGrantedAuthority("ROLE_ADMIN")))
        );
    }

    // 测试获取所有课程
    @Test
    public void testGetAllCourses() throws Exception{
        when(courseService.getAllCourses()).thenReturn(mockCourses);

        mockMvc.perform(get("/api/courses")
                        .header("Authorization", "Bearer "+validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].course_name").value("Java入门"))
                .andExpect(jsonPath("$[1].course_name").value("Spring Boot"));
        verify(courseService,times(1)).getAllCourses();
    }

    // 测试获取单个课程(成功)
    @Test
    void testGetCourseById_Success() throws Exception{
        Course course=mockCourses.get(0);//模拟课程数据
        when(courseService.getCourseById(1L)).thenReturn(mockCourses.get(0));

        mockMvc.perform(get("/api/courses/{id}", 1L)
                        .header("Authorization","Bearer "+validToken)
//                        .with(jwt()) // ✅ 添加认证请求头，
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course_name").value("Java入门"));

        verify(courseService,times(1)).getCourseById(1L);
    }

    // 测试添加课程（无效数据）
    @Test
    public void testAddCourse_InvalidData() throws Exception{
        mockMvc.perform(post("/api/courses/add")
                        .header("Authorization","Bearer "+validToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"courseName\":\"\",\"description\":\"Introduction to Java\",\"credits\":3,\"teacherId\":1,\"tags\":\"programming\"}"))
                .andExpect(status().isBadRequest());
    }

    // 测试添加课程（无权限）
    @Test
    @WithMockUser(roles="STUDENT")
    public void testAddCourse_NoPermission() throws Exception{
        mockMvc.perform(post("/api/courses/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"courseName\":\"Java 101\",\"description\":\"Introduction to Java\",\"credits\":3,\"teacherId\":1,\"tags\":\"programming\"}"))
                .andExpect(status().isForbidden());
    }
//    @Test
//    @WithMockUser( roles = "ADMIN")
//    public void testGetCourseById_ExistingCourse() throws Exception{
//        when(courseService.getCourseById(1L)).thenReturn(validCourse);
//
//        mockMvc.perform(get("/api/courses/{id}", 1L))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.courseName").value("Java 101"));
//    }
    @Test
    void testUpdateCourse_Success() throws Exception{
        Course updateedCourse=new Course(1L, "Java高级", "深入Java变成", 3, 1L, "['Java',进阶']", 4.7f, 10, "thumbnail1.jpg");
        when(courseService.updateCourse(eq(1L),any(Course.class))).thenReturn(updateedCourse);

        mockMvc.perform(put("/api/courses/update/{id}", 1L)
                        .header("Authorization","Bearer "+validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateedCourse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course_name").value("Java高级"));

    }
    @Test
    void testDeleteCourseById_Success() throws Exception{
        doNothing().when(courseService).deleteCourseById(1L);

        mockMvc.perform(delete("/api/courses/delete/{id}", 1L)
                        .header("Authorization","Bearer "+validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("课程已删除"));

        verify(courseService,times(1)).deleteCourseById(1L);
    }
    @Test
    void testFindByTeacherId_Success() throws Exception{
        when(courseService.getCourseByTeacherId(1L)).thenReturn(mockCourses);

        mockMvc.perform(get("/api/courses/teacher/{teacherId}", 1L)
                .header("Authorization","Bearer "+validToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].course_name").value("Java入门"));

    }
    @Test
    void testGetCourseByName_Success() throws Exception{
        when(courseService.getCourseByName("Java入门")).thenReturn(Optional.of(mockCourses.get(0)));

        mockMvc.perform(get("/api/courses/name/{courseName}", "Java入门")
                .header("Authorization","Bearer "+validToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course_name").value("Java入门"));
    }
}
