package com.trainning.exercise.validator;

import com.trainning.exercise.validator.handle.PhoneMaxSizeHandle;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { PhoneMaxSizeHandle.class })
public @interface PhoneMaxSize {
    String message() default "Phone max size";

    int maxSize();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
