package com.trainning.exercise.controller;

import com.trainning.exercise.dto.APIResponse;
import com.trainning.exercise.entity.Student;
import com.trainning.exercise.service.impl.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Schema(description = "Student API")
public class StudentController {
    private final StudentService studentService;

    @PostMapping("student")
    @Operation(summary = "Add a new student")
    public APIResponse<Student> addStudent(@Valid @RequestBody Student student) {
        Student data = studentService.addStudent(student);
        return APIResponse.<Student>builder().data(data).build();
    }

    @GetMapping
    @Operation(summary = "Find all students")
    public APIResponse<List<Student>> findAllStudents() {
        List<Student> data = studentService.getAllStudents();
        return APIResponse.<List<Student>>builder().data(data).build();
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
