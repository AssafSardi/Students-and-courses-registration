package com.checkpoint.StudentsCourses.repository;

import com.checkpoint.StudentsCourses.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> getCourseByName(String name);
}
