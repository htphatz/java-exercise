package com.trainning.exercise.exception;

public class StudentAlreadyExistedException extends RuntimeException {
    public StudentAlreadyExistedException(String message) {
        super(message);
    }
}
