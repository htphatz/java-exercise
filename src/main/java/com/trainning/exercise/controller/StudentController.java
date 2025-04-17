package com.trainning.exercise.controller;

import com.trainning.exercise.dto.APIResponse;
import com.trainning.exercise.dto.PageDto;
import com.trainning.exercise.dto.StudentRequest;
import com.trainning.exercise.dto.StudentResponse;
import com.trainning.exercise.entity.Student;
import com.trainning.exercise.service.impl.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Student APIs")
public class StudentController {
    private final StudentService studentService;

    @PostMapping("student")
    @Operation(summary = "Add a new student")
    public APIResponse<Student> addStudent(@Valid @RequestBody StudentRequest request) {
        Student data = studentService.addStudent(request);
        return APIResponse.<Student>builder().data(data).build();
    }

    @GetMapping
    @Operation(summary = "Find all students")
    public APIResponse<List<Student>> findAllStudents() {
        List<Student> data = studentService.getAllStudents();
        return APIResponse.<List<Student>>builder().data(data).build();
    }

    @GetMapping("/dto")
    @Operation(summary = "Find all students dto")
    public APIResponse<List<StudentResponse>> findAllStudentsDto() {
        List<StudentResponse> data = studentService.getAllStudentsDto();
        return APIResponse.<List<StudentResponse>>builder().data(data).build();
    }

    @GetMapping("/search/query")
    @Operation(summary = "Search student by email, name, address,... by annotation @Query")
    public APIResponse<PageDto<StudentResponse>> searchStudentsQuery(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(name = "email", required = false) String name,
            @RequestParam(name = "name", required = false) String email,
            @RequestParam(name = "address", required = false) String address
    ) {
        PageDto<StudentResponse> data = studentService.searchStudentsByAnnotationQuery(pageNumber, pageSize, name, email, address);
        return APIResponse.<PageDto<StudentResponse>>builder().data(data).build();
    }

    @GetMapping("/search/entity-manager")
    @Operation(summary = "Search student by email, name, address,... by EntityManager")
    public APIResponse<PageDto<StudentResponse>> searchStudentsEntityManager(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(name = "email", required = false) String name,
            @RequestParam(name = "name", required = false) String email,
            @RequestParam(name = "address", required = false) String address
    ) {
        PageDto<StudentResponse> data = studentService.searchStudentsByEntityManager(pageNumber, pageSize, name, email, address);
        return APIResponse.<PageDto<StudentResponse>>builder().data(data).build();
    }

    @GetMapping("/search/jdbc-template")
    @Operation(summary = "Search student by email, name, address,... by JdbcTemplate")
    public APIResponse<PageDto<StudentResponse>> searchStudentsJdbcTemplate(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(name = "email", required = false) String name,
            @RequestParam(name = "name", required = false) String email,
            @RequestParam(name = "address", required = false) String address
    ) {
        PageDto<StudentResponse> data = studentService.searchStudentsByJdbcTemplate(pageNumber, pageSize, name, email, address);
        return APIResponse.<PageDto<StudentResponse>>builder().data(data).build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Find student by id")
    public APIResponse<Student> findStudentById(@Valid @PathVariable("id") String id) {
        Student data = studentService.getStudentById(id);
        return APIResponse.<Student>builder().data(data).build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Update student by id")
    public APIResponse<Student> updateStudentById(
            @Valid @PathVariable("id") String id,
            @Valid @RequestBody Student student) {
        Student data = studentService.updateStudent(id, student);
        return APIResponse.<Student>builder().data(data).build();
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete student by id")
    public APIResponse<Void> deleteStudentById(@Valid @PathVariable("id") String id) {
        studentService.deleteStudent(id);
        return APIResponse.<Void>builder().build();
    }
}
