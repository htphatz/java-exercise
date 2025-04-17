package com.trainning.exercise.validator;

import com.trainning.exercise.validator.handle.PhoneStartWithHandle;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { PhoneStartWithHandle.class })
public @interface PhoneStarWith {
    String message() default "Wrong phone start with format";

    String startWithFormat();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
