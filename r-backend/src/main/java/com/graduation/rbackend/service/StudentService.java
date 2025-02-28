package com.graduation.rbackend.service;

import com.graduation.rbackend.model.Student;
import com.graduation.rbackend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * 保存学生信息
     * @param student
     * @return
     */
    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    /**
     * 根据id获取学生信息
     * @param id
     * @return
     */
    public Student getStudentById(Long id){
        return studentRepository.findById(id).get();
    }
    /**
     * 获取所有学生信息
     * @return
     */
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    /**
     * 根据专业查询学生信息
     * @param major
     * @return
     */
    public List<Student> findByMajor(String major){
        return studentRepository.findByMajor(major);
    }

    /**
     * 根据id删除学生信息
     * @param id
     */
    public void deleteStudentById(Long id){
        studentRepository.deleteById(id);
    }



}
