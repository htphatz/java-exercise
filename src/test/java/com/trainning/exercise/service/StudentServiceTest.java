package com.trainning.exercise.service;

import com.trainning.exercise.dto.StudentRequest;
import com.trainning.exercise.entity.Student;
import com.trainning.exercise.exception.StudentNotFoundException;
import com.trainning.exercise.repository.StudentRepository;
import com.trainning.exercise.service.impl.StudentService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Java training JUnit5")
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeAll
    static void setup() {
        System.out.println("BeforeAll");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("BeforeEach");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("AfterAll");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("AfterEach");
    }

    @Test
    void whenGetAll_thenReturnList() {
        // Given
        List<Student> expectedStudent = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            expectedStudent.add(new Student());
        }

        // When
        Mockito.when(studentRepository.findAll()).thenReturn(expectedStudent);
        List<Student> actualStudents = studentRepository.findAll();

        // Then
        assertNotNull(actualStudents);
        assertEquals(expectedStudent.size(), actualStudents.size());
    }

    @Test
    void givenStudentData_whenCreateStudent_thenReturnNewStudent() {
        // Given
        StudentRequest studentRequest = StudentRequest.builder()
                .name("student")
                .email("student@gmail.com")
                .address("123ABC")
                .phone("+8484848484")
                .build();

        Student student = Student.builder()
                .name("student")
                .email("student@gmail.com")
                .address("123ABC")
                .phone("+8484848484")
                .build();

        // When
        Mockito.when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Then
        assertEquals(student.getName(), studentRequest.getName());
        assertEquals(student.getEmail(), studentRequest.getEmail());
        assertEquals(student.getAddress(), studentRequest.getAddress());
        assertEquals(student.getBirthday(), studentRequest.getBirthday());
    }

    @Test
    void givenStudentId_whenFindById_thenReturnStudent() {
        // Given
        String studentId = UUID.randomUUID().toString();
        Student expectedStudent = Student.builder()
                .name("student")
                .email("student@gmail.com")
                .address("123ABC")
                .phone("+8484848484")
                .birthday(Instant.now())
                .build();

        Student actualStudent = Student.builder()
                .name("student")
                .email("student@gmail.com")
                .address("123ABC")
                .phone("+8484848484")
                .birthday(Instant.now())
                .build();

        // When
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(expectedStudent));

        // Then
        assertEquals(expectedStudent.getName(), actualStudent.getName());
        assertEquals(expectedStudent.getEmail(), actualStudent.getEmail());
        assertEquals(expectedStudent.getAddress(), actualStudent.getAddress());
        assertEquals(expectedStudent.getBirthday(), actualStudent.getBirthday());
    }

    @Test
    void givenStudentId_whenFindById_thenExceptionInThrow() {
        // Given
        String studentId = UUID.randomUUID().toString();
        Mockito.when(studentRepository.findById(studentId)).thenThrow(new StudentNotFoundException("Student with id "+ studentId + " has not existed in DB."));

        // When
        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(studentId));

        // Then
        assertNotNull(exception);
        assertEquals(exception.getMessage(), "Student with id "+ studentId + " has not existed in DB.");
    }

    @Test
    void givenStudentId_whenDelete_thenExceptionInThrow() {
        // Given
        String studentId = "123";
        Mockito.when(studentRepository.findById(studentId)).thenThrow(new StudentNotFoundException("Student not found"));

        // When
        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(UUID.randomUUID().toString()));

        // Then
        assertNotNull(exception);
        assertEquals(exception.getMessage(), "Student not found");
    }
}
