package com.checkpoint.StudentsCourses.controller;

import com.checkpoint.StudentsCourses.dto.CourseDTO;
import com.checkpoint.StudentsCourses.mapper.CourseMapper;
import com.checkpoint.StudentsCourses.model.Course;
import com.checkpoint.StudentsCourses.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String hello() {
        return "Hello student " + SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/course")
    public ResponseEntity<List<CourseDTO>> getRegisteredCourse(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(studentService.getRegisteredCourses(user.getUsername()).stream().map(CourseMapper::toDTO).collect(Collectors.toList()));
    }

    @PostMapping("/course/register/{courseId}")
    public ResponseEntity<Map<String,Object>> registerCourse(@AuthenticationPrincipal UserDetails user, @PathVariable Long courseId) {
        studentService.registerCourse(user.getUsername(), courseId);
        Map<String,Object> response = new HashMap<>();
        response.put("message", "Course registered successfully");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/course/cancel/{courseId}")
    public ResponseEntity<Map<String,Object>> cancelRegistration(@AuthenticationPrincipal UserDetails user, @PathVariable Long courseId) {
        studentService.cancelRegistration(user.getUsername(), courseId);
        Map<String,Object> response = new HashMap<>();
        response.put("message", "Course registration cancelled successfully");

        return ResponseEntity.ok(response);
    }

}
