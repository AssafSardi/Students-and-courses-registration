package com.checkpoint.StudentsCourses.exception;

public class MissingCourseAttributeException extends RuntimeException {
    public MissingCourseAttributeException() {
        super("Course must contain name and description attributes");
    }
}
