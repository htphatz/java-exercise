package com.trainning.exercise.service;

import com.trainning.exercise.dto.PageDto;
import com.trainning.exercise.dto.StudentRequest;
import com.trainning.exercise.dto.StudentResponse;
import com.trainning.exercise.entity.Student;

import java.util.List;

public interface IStudentService {
    Student addStudent(StudentRequest request);
    List<Student> getAllStudents();
    List<StudentResponse> getAllStudentsDto();
    PageDto<StudentResponse> searchStudentsByAnnotationQuery(Integer pageNumber, Integer pageSize, String name, String email, String address);
    PageDto<StudentResponse> searchStudentsByEntityManager(Integer pageNumber, Integer pageSize, String name, String email, String address);
    PageDto<StudentResponse> searchStudentsByJdbcTemplate(Integer pageNumber, Integer pageSize, String name, String email, String address);
    Student getStudentById(String id);
    Student updateStudent(String id, Student student);
    void deleteStudent(String id);
}
