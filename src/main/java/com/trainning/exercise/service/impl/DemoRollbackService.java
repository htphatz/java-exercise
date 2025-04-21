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

    /**
     * Demonstrates a rollback scenario when an error occurs during the process.
     *
     * This method performs the following steps:
     * 1. Adds a new student to the database using the provided request data.
     * 2. Checks if the student's age is less than 20. If so, an InvalidAgeException is thrown.
     * 3. Registers health insurance for the newly added student.
     * 4. If an exception is thrown at any step, the transaction is rolled back to maintain data consistency.
     *
     * @param request The StudentRequest object containing the details of the student to be added.
     * @throws InvalidAgeException If the student's age is less than 20.
     */
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
