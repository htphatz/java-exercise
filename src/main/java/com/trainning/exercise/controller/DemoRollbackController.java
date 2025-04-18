package com.trainning.exercise.controller;

import com.trainning.exercise.dto.StudentRequest;
import com.trainning.exercise.service.impl.DemoRollbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rollbacks")
@RequiredArgsConstructor
@Tag(name = "Demo rollback APIs")
public class DemoRollbackController {
    private final DemoRollbackService demoRollbackService;

    @PostMapping
    @Operation(summary = "Demo rollback when error occurs")
    public ResponseEntity<Void> addStudent(@Valid @RequestBody StudentRequest request) {
        demoRollbackService.demoRollbackWhenError(request);
        return ResponseEntity.ok().build();
    }
}
