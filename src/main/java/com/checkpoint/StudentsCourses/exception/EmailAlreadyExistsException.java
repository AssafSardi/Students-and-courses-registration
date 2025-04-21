package com.checkpoint.StudentsCourses.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Already exists student with email: " + email);
    }
}
