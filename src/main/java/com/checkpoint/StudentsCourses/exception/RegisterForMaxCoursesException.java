package com.checkpoint.StudentsCourses.exception;

public class RegisterForMaxCoursesException extends RuntimeException {
    public RegisterForMaxCoursesException() {
        super("Student is already registered for 2 courses");
    }
}
