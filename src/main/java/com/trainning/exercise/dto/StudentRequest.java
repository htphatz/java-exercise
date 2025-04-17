package com.trainning.exercise.dto;

import com.trainning.exercise.validator.InvalidAge;
import com.trainning.exercise.validator.PhoneMaxSize;
import com.trainning.exercise.validator.PhoneStarWith;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class StudentRequest {
    @NotNull(message = "Email cannot be null")
    @Email(message = "Wrong email format")
    private String email;

    private String name;
    private String address;

    @PhoneStarWith(startWithFormat = "+84")
    @PhoneMaxSize(maxSize = 12)
    private String phone;

    @InvalidAge(ageMin = 18, ageMax = 80)
    private Instant birthday;
}
