package com.checkpoint.StudentsCourses.exception;

public class MissingStudentAttributeException extends RuntimeException{
    public MissingStudentAttributeException() {
        super("Student must contain name and email attributes");
    }
}
