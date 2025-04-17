package com.trainning.exercise.validator.handle;

import com.trainning.exercise.mapper.StudentMapper;
import com.trainning.exercise.validator.InvalidAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;

public class InvalidAgeHandle implements ConstraintValidator<InvalidAge, Instant> {
    int ageMin = 0;
    int ageMax = 0;

    @Override
    public void initialize(InvalidAge constraintAnnotation) {
        ageMin = constraintAnnotation.ageMin();
        ageMax = constraintAnnotation.ageMax();
    }

    @Override
    public boolean isValid(Instant birthday, ConstraintValidatorContext context) {
        if (birthday == null) {
            return false;
        }

        int age = StudentMapper.convertBirthdayToAge(birthday);

        return age >= ageMin && age <= ageMax;
    }
}
