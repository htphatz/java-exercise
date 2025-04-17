package com.trainning.exercise.validator.handle;

import com.trainning.exercise.validator.PhoneStarWith;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneStartWithHandle implements ConstraintValidator<PhoneStarWith, String> {
    String startWithFormat = "";

    @Override
    public void initialize(PhoneStarWith constraintAnnotation) {
        startWithFormat = constraintAnnotation.startWithFormat();
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return phone != null && phone.startsWith(startWithFormat);
    }
}
