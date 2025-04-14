package com.trainning.exercise.service.impl;

import com.trainning.exercise.entity.Student;
import com.trainning.exercise.exception.StudentAlreadyExistedException;
import com.trainning.exercise.exception.StudentNotFoundException;
import com.trainning.exercise.repository.StudentRepository;
import com.trainning.exercise.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;

    @Override
    public Student addStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new StudentAlreadyExistedException("Student with email " + student.getEmail() + " already existed.");
        }
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + id + " has not existed in DB."));
    }

    @Override
    public Student updateStudent(String id, Student student) {
        Student existingStudent = getStudentById(id);

        existingStudent.setEmail(student.getEmail());
        existingStudent.setName(student.getName());
        existingStudent.setAddress(student.getAddress());
        existingStudent.setBirthday(student.getBirthday());

        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudent(String id) {
        Student existingStudent = getStudentById(id);
        studentRepository.delete(existingStudent);
    }
}
