package com.trainning.exercise.service.impl;

import com.trainning.exercise.dto.PageDto;
import com.trainning.exercise.dto.StudentRequest;
import com.trainning.exercise.dto.StudentResponse;
import com.trainning.exercise.entity.Student;
import com.trainning.exercise.exception.EmailAlreadyExistedException;
import com.trainning.exercise.exception.InvalidAgeException;
import com.trainning.exercise.exception.StudentAlreadyExistedException;
import com.trainning.exercise.exception.StudentNotFoundException;
import com.trainning.exercise.mapper.StudentMapper;
import com.trainning.exercise.repository.SearchRepository;
import com.trainning.exercise.repository.StudentRepository;
import com.trainning.exercise.service.IStudentService;
import jakarta.transaction.Transactional;
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

    /**
     * Adds a new student to the database.
     *
     * The method is transactional and will roll back if an InvalidAgeException is thrown.
     *
     * @param request The StudentRequest object containing the details of the student to be added.
     * @return The saved Student entity.
     * @throws StudentAlreadyExistedException If a student with the given email already exists.
     */
    @Override
    @Transactional(rollbackOn = { InvalidAgeException.class })
    public Student addStudent(StudentRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new StudentAlreadyExistedException("Student with email " + request.getEmail() + " already existed.");
        }
        Student student = Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .phone(request.getPhone())
                .birthday(request.getBirthday())
                .build();
        return studentRepository.save(student);
    }

    /**
     * Retrieves all students from the database.
     *
     * @return A list of all Student entities.
     */
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Retrieves all students from the database.
     *
     * @return A list of all Student dtos.
     */
    @Override
    public List<StudentResponse> getAllStudentsDto() {
        return getAllStudents().stream().map(StudentMapper.toDto()).collect(Collectors.toList());
    }

    /**
     * Searches for students using an annotation-based query.
     *
     * This method retrieves a paginated list of students from the database based on the provided search criteria (name, email, address).
     * It uses the StudentRepository to execute the query and maps the results to a PageDto containing StudentResponse objects.
     *
     * @param pageNumber The page number to retrieve (1-based index).
     * @param pageSize The number of records per page.
     * @param name The name of the student to search for (optional).
     * @param email The email of the student to search for (optional).
     * @param address The address of the student to search for (optional).
     * @return A PageDto containing the paginated list of StudentResponse objects.
     */
    @Override
    public PageDto<StudentResponse> searchStudentsByAnnotationQuery(Integer pageNumber, Integer pageSize, String name, String email, String address) {
        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Student> students = studentRepository.searchStudents(name, email, address, pageable);
        return PageDto.of(students).map(StudentMapper.toDto());
    }

    /**
     * Searches for students using entity manager.
     *
     * This method retrieves a paginated list of students from the database based on the provided search criteria (name, email, address).
     * It uses the EntityManager to execute the query and maps the results to a PageDto containing StudentResponse objects.
     *
     * @param pageNumber The page number to retrieve (1-based index).
     * @param pageSize The number of records per page.
     * @param name The name of the student to search for (optional).
     * @param email The email of the student to search for (optional).
     * @param address The address of the student to search for (optional).
     * @return A PageDto containing the paginated list of StudentResponse objects.
     */
    @Override
    public PageDto<StudentResponse> searchStudentsByEntityManager(Integer pageNumber, Integer pageSize, String name, String email, String address) {
        Page<Student> students = searchRepository.searchStudentsByEntityManager(pageNumber, pageSize, name, email, address);
        return PageDto.of(students).map(StudentMapper.toDto());
    }

    /**
     * Searches for students using JdbcTemplate.
     *
     * This method retrieves a paginated list of students from the database based on the provided search criteria (name, email, address).
     * It uses the JdbcTemplate to execute the query and maps the results to a PageDto containing StudentResponse objects.
     *
     * @param pageNumber The page number to retrieve (1-based index).
     * @param pageSize The number of records per page.
     * @param name The name of the student to search for (optional).
     * @param email The email of the student to search for (optional).
     * @param address The address of the student to search for (optional).
     * @return A PageDto containing the paginated list of StudentResponse objects.
     */
    @Override
    public PageDto<StudentResponse> searchStudentsByJdbcTemplate(Integer pageNumber, Integer pageSize, String name, String email, String address) {
        Page<Student> students = searchRepository.searchStudentsByJdbcTemplate(pageNumber, pageSize, name, email, address);
        return PageDto.of(students).map(StudentMapper.toDto());
    }

    /**
     * Retrieves a student by their unique identifier.
     *
     * @param id The unique identifier of the student to retrieve.
     * @return The Student entity associated with the given ID.
     * @throws StudentNotFoundException If no student is found with the given ID.
     */
    @Override
    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + id + " has not existed in DB."));
    }

    /**
     * Updates an existing student's information in the database.
     *
     * @param id The unique identifier of the student to update.
     * @param student The Student object containing the updated details.
     * @return The updated Student entity.
     * @throws EmailAlreadyExistedException If the provided email is already in use by another student.
     * @throws StudentNotFoundException If no student is found with the given ID.
     */
    @Override
    public Student updateStudent(String id, Student student) {
        Student existingStudent = getStudentById(id);

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new EmailAlreadyExistedException("Email " + student.getEmail() + " already existed");
        }

        existingStudent.setEmail(student.getEmail());
        existingStudent.setName(student.getName());
        existingStudent.setAddress(student.getAddress());
        existingStudent.setBirthday(student.getBirthday());

        return studentRepository.save(existingStudent);
    }

    /**
     * Deletes a student from the database.
     *
     * @param id The unique identifier of the student to delete.
     * @throws StudentNotFoundException If no student is found with the given ID.
     */
    @Override
    public void deleteStudent(String id) {
        Student existingStudent = getStudentById(id);
        studentRepository.delete(existingStudent);
    }
}
