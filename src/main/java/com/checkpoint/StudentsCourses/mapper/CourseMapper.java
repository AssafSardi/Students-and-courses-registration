package com.checkpoint.StudentsCourses.mapper;

import com.checkpoint.StudentsCourses.dto.CourseDTO;
import com.checkpoint.StudentsCourses.model.Course;
import com.checkpoint.StudentsCourses.model.Student;

import java.util.Set;
import java.util.stream.Collectors;

public class CourseMapper {
    public static CourseDTO toDTO(Course course) {
        Set<Long> studentIds = course.getStudents().stream()
                .map(Student::getId).collect(Collectors.toSet());

        return new CourseDTO(course.getId(), course.getName(), course.getDescription(), studentIds);
    }

    public static Course toEntity(CourseDTO courseDTO, Set<Student> students) {
        Course course = new Course();
        course.setId(courseDTO.getId());
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setStudents(students);

        return course;
    }
}
