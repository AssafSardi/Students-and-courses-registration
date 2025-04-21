package com.checkpoint.StudentsCourses.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Not found course with id: " + id);
    }

    public CourseNotFoundException(String name) {
        super("Not found course with name: " + name);
    }
}
