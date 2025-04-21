package com.checkpoint.StudentsCourses.exception;

public class CourseNameAlreadyExistsException extends RuntimeException {
    public CourseNameAlreadyExistsException(String name) {
        super("Already exists course with name: " + name);
    }
}
