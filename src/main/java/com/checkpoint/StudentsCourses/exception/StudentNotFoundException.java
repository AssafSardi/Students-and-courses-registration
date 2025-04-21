package com.checkpoint.StudentsCourses.exception;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(Long id) {
        super("Not found student with id: " + id);
    }

    public StudentNotFoundException(String email) {
        super("Not found student with email: " + email);
    }
}
