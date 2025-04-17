package com.trainning.exercise.service.impl;

import com.trainning.exercise.dto.PageDto;
import com.trainning.exercise.dto.StudentResponse;
import com.trainning.exercise.entity.Student;
import com.trainning.exercise.exception.StudentAlreadyExistedException;
import com.trainning.exercise.exception.StudentNotFoundException;
import com.trainning.exercise.mapper.StudentMapper;
import com.trainning.exercise.repository.SearchRepository;
import com.trainning.exercise.repository.StudentRepository;
import com.trainning.exercise.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;
    private final SearchRepository searchRepository;

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
    public List<StudentResponse> getAllStudentsDto() {
        return getAllStudents().stream().map(StudentMapper.toDto()).collect(Collectors.toList());
    }

    @Override
    public PageDto<StudentResponse> searchStudentsByAnnotationQuery(Integer pageNumber, Integer pageSize, String name, String email, String address) {
        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Student> students = studentRepository.searchStudents(name, email, address, pageable);
        return PageDto.of(students).map(StudentMapper.toDto());
    }

    @Override
    public PageDto<StudentResponse> searchStudentsByEntityManager(Integer pageNumber, Integer pageSize, String name, String email, String address) {
        Page<Student> students = searchRepository.searchStudentsByEntityManager(pageNumber, pageSize, name, email, address);
        return PageDto.of(students).map(StudentMapper.toDto());
    }

    @Override
    public PageDto<StudentResponse> searchStudentsByJdbcTemplate(Integer pageNumber, Integer pageSize, String name, String email, String address) {
        Page<Student> students = searchRepository.searchStudentsByJdbcTemplate(pageNumber, pageSize, name, email, address);
        return PageDto.of(students).map(StudentMapper.toDto());
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
