package com.trainning.exercise.service;

import com.trainning.exercise.entity.Student;

import java.util.List;

public interface IStudentService {
    Student addStudent(Student student);
    List<Student> getAllStudents();
    Student getStudentById(String id);
    Student updateStudent(String id, Student student);
    void deleteStudent(String id);
}
