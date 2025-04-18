package com.trainning.exercise.service.impl;

import com.trainning.exercise.dto.StudentRequest;
import com.trainning.exercise.entity.Student;
import com.trainning.exercise.exception.InvalidAgeException;
import com.trainning.exercise.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoRollbackService {
    @Autowired
    private HealthInsuranceService healthInsuranceService;

    @Autowired
    private StudentService studentService;

    public void demoRollbackWhenError(StudentRequest request) {
        // Add student
        Student newStudent = studentService.addStudent(request);

        // Check if age < 20
        int age = StudentMapper.convertBirthdayToAge(newStudent.getBirthday());
        if (age < 20) {
            throw new InvalidAgeException("Invalid age");
        }

        // Register insurance
        healthInsuranceService.registerInsurance(newStudent.getId());
    }
}
