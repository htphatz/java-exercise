package com.trainning.exercise.exception;

public class EmailAlreadyExistedException extends RuntimeException {
    public EmailAlreadyExistedException(String message) {
        super(message);
    }
}
