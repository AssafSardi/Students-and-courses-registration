package com.checkpoint.StudentsCourses.service;

import com.checkpoint.StudentsCourses.exception.*;
import com.checkpoint.StudentsCourses.model.Course;
import com.checkpoint.StudentsCourses.model.Student;
import com.checkpoint.StudentsCourses.repository.CourseRepository;
import com.checkpoint.StudentsCourses.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Course> getRegisteredCourses(String username) {
        logger.info("Getting registered courses for student " + username);

        Student student = studentRepository.findByEmail(username).orElseThrow(
                () -> new StudentNotFoundException(username));

        return student.getCourses().stream().toList();
    }

    @Transactional
    public void registerCourse(String username, Long courseId) {
        logger.info("Registering " + username + " for course " + courseId);

        Student student = studentRepository.findByEmail(username).orElseThrow(
                () -> new StudentNotFoundException(username));
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId));

        // check if student is already registered for the course
        if (student.getCourses().contains(course) && course.getStudents().contains(student)) {
            throw new AlreadyRegisterForCourseException(courseId);
        }

        // check if student is registered for 2 courses already
        if (student.getCourses().size() >= 2) {
            throw new RegisterForMaxCoursesException();
        }

        // check if course is full
        if (course.getStudents().size() >= course.getMaxStudents()) {
            throw new CourseIsFullException(courseId, course.getMaxStudents());
        }

        student.getCourses().add(course);
        course.getStudents().add(student);
        studentRepository.save(student);

        logger.info("Registered " + username + " for course " + courseId);
    }

    @Transactional
    public void cancelRegistration(String username, Long courseId) {
        logger.info("Cancelling " + username + " registration for course " + courseId);

        Student student = studentRepository.findByEmail(username).orElseThrow(
                () -> new StudentNotFoundException(username));
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId));

        // remove relation between student and course
        student.getCourses().remove(course);
        course.getStudents().remove(student);
        studentRepository.save(student);

        logger.info("Canceled " + username + " registration for course " + courseId);
    }
}
