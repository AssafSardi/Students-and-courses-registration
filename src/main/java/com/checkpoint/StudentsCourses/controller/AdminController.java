package com.checkpoint.StudentsCourses.controller;

import com.checkpoint.StudentsCourses.dto.CourseDTO;
import com.checkpoint.StudentsCourses.dto.StudentDTO;
import com.checkpoint.StudentsCourses.mapper.CourseMapper;
import com.checkpoint.StudentsCourses.mapper.StudentMapper;
import com.checkpoint.StudentsCourses.model.Course;
import com.checkpoint.StudentsCourses.model.Student;
import com.checkpoint.StudentsCourses.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String hello() {
        return "Hello Admin " + SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // Operations on Student
    @PostMapping("/student")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(StudentMapper.toDTO(adminService.createStudent(student)));
    }

    @GetMapping("/student")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(adminService.getAllStudents().stream().map(StudentMapper::toDTO).toList());
    }

    @GetMapping("/student/id/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(StudentMapper.toDTO(adminService.getStudentById(id)));
    }

    @GetMapping("/student/email/{email}")
    public ResponseEntity<StudentDTO> getStudentByEmail(@PathVariable String email) {
        return ResponseEntity.ok(StudentMapper.toDTO(adminService.getStudentByEmail(email)));
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity.ok(StudentMapper.toDTO(adminService.updateStudent(id, student)));
    }

    @DeleteMapping("/student/id/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudentById(@PathVariable Long id) {
        adminService.deleteStudentById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Student with id: " + id + " was deleted successfully");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/student/email/{email}")
    public ResponseEntity<Map<String, Object>> deleteStudentByEmail(@PathVariable String email) {
        adminService.deleteStudentByEmail(email);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Student with email: " + email + " was deleted successfully");

        return ResponseEntity.ok(response);
    }

    // Operations on Course
    @PostMapping("/course")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(CourseMapper.toDTO(adminService.createCourse(course)));
    }

    @GetMapping("/course")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(adminService.getAllCourses().stream().map(CourseMapper::toDTO).toList());
    }

    @GetMapping("/course/id/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(CourseMapper.toDTO(adminService.getCourseById(id)));
    }

    @GetMapping("/course/name/{name}")
    public ResponseEntity<CourseDTO> getCourseByName(@PathVariable String name) {
        return ResponseEntity.ok(CourseMapper.toDTO(adminService.getCourseByName(name)));
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return ResponseEntity.ok(CourseMapper.toDTO(adminService.updateCourse(id, course)));
    }

    @DeleteMapping("/course/id/{id}")
    public ResponseEntity<Map<String, Object>> deleteCourseById(@PathVariable Long id) {
        adminService.deleteCourseById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Course with id: " + id + " was deleted successfully");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/course/name/{name}")
    public ResponseEntity<Map<String, Object>> deleteCourseByName(@PathVariable String name) {
        adminService.deleteCourseByName(name);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Course with name: " + name + " was deleted successfully");

        return ResponseEntity.ok(response);
    }

}
