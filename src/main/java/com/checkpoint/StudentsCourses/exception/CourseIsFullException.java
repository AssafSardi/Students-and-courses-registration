package com.checkpoint.StudentsCourses.exception;

public class CourseIsFullException extends RuntimeException {
    public CourseIsFullException(Long id, int max) {
        super("Course with id " + id + " is full and reached his max capacity of " + max + " students");
    }
}
