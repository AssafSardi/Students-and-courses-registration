package com.checkpoint.StudentsCourses.exception;

public class NullObjectException extends RuntimeException {
    public NullObjectException(String className) {
        super(className + "can't be null");
    }
}
