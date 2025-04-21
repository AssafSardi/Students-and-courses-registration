package com.checkpoint.StudentsCourses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, HttpStatus httpStatus) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", httpStatus.value());
        errorBody.put("error", httpStatus.getReasonPhrase());
        errorBody.put("message", ex.getMessage());

        return ResponseEntity.status(httpStatus).body(errorBody);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFound(StudentNotFoundException ex) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return handleException(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFound(CourseNotFoundException ex) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFound(CourseNameAlreadyExistsException ex) {
        return handleException(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NullObjectException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFound(NullObjectException ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingStudentAttributeException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFound(MissingStudentAttributeException ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingCourseAttributeException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFound(MissingCourseAttributeException ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyRegisterForCourseException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFound(AlreadyRegisterForCourseException ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegisterForMaxCoursesException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFound(RegisterForMaxCoursesException ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CourseIsFullException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFound(CourseIsFullException ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }
}
