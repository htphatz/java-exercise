package com.trainning.exercise.validator.handle;

import com.trainning.exercise.validator.PhoneMaxSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneMaxSizeHandle implements ConstraintValidator<PhoneMaxSize, String> {
    int maxSize = 0;

    @Override
    public void initialize(PhoneMaxSize constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return phone != null && phone.length() < maxSize;
    }
}
