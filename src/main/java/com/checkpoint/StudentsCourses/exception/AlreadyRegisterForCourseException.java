package com.checkpoint.StudentsCourses.exception;

public class AlreadyRegisterForCourseException extends RuntimeException {
    public AlreadyRegisterForCourseException(Long id) {
        super("Student is already registered for course: " + id);
    }
}
