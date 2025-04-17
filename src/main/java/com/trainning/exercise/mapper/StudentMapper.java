package com.trainning.exercise.mapper;

import com.trainning.exercise.dto.StudentResponse;
import com.trainning.exercise.entity.Student;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Objects;
import java.util.function.Function;

@Component
public class StudentMapper {
    public static Function<Student, StudentResponse> toDto() {
        return student -> StudentResponse.builder()
                .name(student.getName())
                .email(student.getEmail())
                .address(student.getAddress())
                .age(convertBirthdayToAge(student.getBirthday()))
                .build();
    }

    private static int convertBirthdayToAge(Instant instant) {
        if (Objects.isNull(instant)) {
            return 0;
        }

        LocalDate birthday = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate current = LocalDate.now();
        return Period.between(birthday, current).getYears();
    }
}
