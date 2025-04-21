package com.checkpoint.StudentsCourses.mapper;

import com.checkpoint.StudentsCourses.dto.StudentDTO;
import com.checkpoint.StudentsCourses.model.Course;
import com.checkpoint.StudentsCourses.model.Student;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentMapper {
    public static StudentDTO toDTO(Student student) {
        Set<Long> courseIds = student.getCourses().stream()
                .map(Course::getId).collect(Collectors.toSet());

        return new StudentDTO(student.getId(), student.getName(), student.getEmail(), courseIds);
    }

    public static Student toEntity(StudentDTO studentDTO, Set<Course> courses) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setCourses(courses);

        return student;
    }
}
