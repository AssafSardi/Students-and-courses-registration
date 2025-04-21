package com.checkpoint.StudentsCourses.service;

import com.checkpoint.StudentsCourses.exception.*;
import com.checkpoint.StudentsCourses.model.Course;
import com.checkpoint.StudentsCourses.model.Student;
import com.checkpoint.StudentsCourses.repository.AdminRepository;
import com.checkpoint.StudentsCourses.repository.CourseRepository;
import com.checkpoint.StudentsCourses.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;


    public AdminService(AdminRepository adminRepository, StudentRepository studentRepository, CourseRepository courseRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Operations on Students
    @Transactional
    public Student createStudent(Student student) {
        logger.info("creating a new student");

        if (student == null) {
            throw new NullObjectException(Student.class.getSimpleName());
        }
        else if (student.getName() == null || student.getEmail() == null) {
            throw new MissingStudentAttributeException();
        }
        // if email is unique, generate special_key and create student
        else if (studentRepository.findByEmail(student.getEmail()).isEmpty()) {
            logger.info("assigning a special_key for student");

            String uuid = UUID.randomUUID().toString();
            System.out.println("special_key for student " + student.getEmail() + ": " + uuid);
            student.setSpecialKey(passwordEncoder.encode(uuid));

            return studentRepository.save(student);
        }
        else {
            throw new EmailAlreadyExistsException(student.getEmail());
        }
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new StudentNotFoundException(email));
    }

    @Transactional
    public Student updateStudent(Long id, Student updatedStudent) {
        logger.info("updating student with id " + id);

        if (updatedStudent == null) {
            throw new NullObjectException(Student.class.getSimpleName());
        }

        // get student by id
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isPresent()) {
            Student existingStudent = studentOptional.get();

            // if email is unique, update student with existing details
            if (existingStudent.getEmail().equals(updatedStudent.getEmail()) ||
                    (studentRepository.findByEmail(updatedStudent.getEmail()).isEmpty() &&
                            adminRepository.findByEmail(updatedStudent.getEmail()).isEmpty())) {
                // no null name
                if (updatedStudent.getName() != null) {
                    existingStudent.setName(updatedStudent.getName());
                }

                // no null email
                if (updatedStudent.getEmail() != null) {
                    existingStudent.setEmail(updatedStudent.getEmail());
                }

                return studentRepository.save(existingStudent);
            }
            else {
                throw new EmailAlreadyExistsException(updatedStudent.getEmail());
            }
        }
        else {
            throw new StudentNotFoundException(id);
        }
    }

    @Transactional
    public void deleteStudentById(Long id) {
        logger.info("deleting student with id " + id);

        // get student by id
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        // remove student from every course that he is registered to
        for (Course course : student.getCourses()) {
            course.getStudents().remove(student);
        }

        student.getCourses().clear();
        studentRepository.delete(student);

        logger.info("deleted student with id " + id);
    }

    @Transactional
    public void deleteStudentByEmail(String email) {
        logger.info("deleting student with email " + email);
        deleteStudentById(getStudentByEmail(email).getId());
        logger.info("deleted student with email " + email);
    }

    // Operations on Courses
    @Transactional
    public Course createCourse(Course course) {
        logger.info("creating a new course");

        if (course == null) {
            throw new NullObjectException(Course.class.getSimpleName());
        }
        else if (course.getName() == null || course.getName().isEmpty() || course.getDescription() == null) {
            throw new MissingCourseAttributeException();
        }
        // if course name is unique, create course
        else if (courseRepository.getCourseByName(course.getName()).isEmpty()) {
            return courseRepository.save(course);
        }
        else {
            throw new CourseNameAlreadyExistsException(course.getName());
        }
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new CourseNotFoundException(id));
    }

    public Course getCourseByName(String name) {
        return courseRepository.getCourseByName(name).orElseThrow(
                () -> new CourseNotFoundException(name));
    }

    @Transactional
    public Course updateCourse(Long id, Course updatedCourse) {
        logger.info("updating course with id " + id);

        if (updatedCourse == null) {
            throw new NullObjectException(Course.class.getSimpleName());
        }

        // get course by id
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isPresent()) {
            Course existingCourse = courseOptional.get();
            // if course name is unique. update course with existing details
            if (existingCourse.getName().equals(updatedCourse.getName()) ||
                    courseRepository.getCourseByName(updatedCourse.getName()).isEmpty()) {

                // No null or empty course name
                if (updatedCourse.getName() != null && !updatedCourse.getName().isEmpty()) {
                    existingCourse.setName(updatedCourse.getName());
                }

                // no null description
                if (updatedCourse.getDescription() != null) {
                    existingCourse.setDescription(updatedCourse.getDescription());
                }

                return courseRepository.save(existingCourse);
            }
            else {
                throw new CourseNameAlreadyExistsException(updatedCourse.getName());
            }
        }
        else {
            throw new CourseNotFoundException(id);
        }
    }

    @Transactional
    public void deleteCourseById(Long id) {
        logger.info("deleting course with id " + id);

        // get course by id
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        // remove course from all the students that are registered for the course
        for (Student student : course.getStudents()) {
            student.getCourses().remove(course);
        }

        course.getStudents().clear();
        courseRepository.delete(course);

        logger.info("deleted course with id " + id);
    }

    @Transactional
    public void deleteCourseByName(String name) {
        logger.info("deleting course with name " + name);
        deleteCourseById(getCourseByName(name).getId());
        logger.info("deleted course with name " + name);
    }
}
