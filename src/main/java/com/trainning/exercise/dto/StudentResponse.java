package com.trainning.exercise.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentResponse {
    private String name;
    private String email;
    private String address;
    private int age;
}
