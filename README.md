Assaf Sardi - Spring Boot and MySQL project

# Overview:

The objective of this assignment is to evaluate your skills in database design, backend development, and creating a functional web application. You will develop a web application using Java Spring Boot and MySQL to manage a system where students can register for courses.

# Requirements:

## Database Design:

Design a relational database in MySQL.

The main entities are Student (should include at least: id, name, email) and Course (should include at least: id, name, description)

## Application Features:

### Admin Features:

1. Login: by email and password (you can pre generate it)
2. CRUD on Students: Admin should be able to create, read, update, and delete student records.
   - When creating a new student, an auto-generated `special_key` should be assigned for the student to use during login.
3. CRUD on Courses: Admin should be able to create, read, update, and delete course records.
4. Get all Students: Admin should be able to get all students (and for every student, the list of courses they are registered for).
5. Get all Courses: Admin should be able to get all courses (and for every course, the list of students registered to it).
   
### Student Features:

1. Login: Students should log in using their special key provided by the admin.
2. Register for a Course:
   - Students can register for up to 2 courses.
   - A course cannot have more than 30 students.
3. Cancel Registration: Students should be able to cancel their registration for a course.


# Start MySQL
Run the following scripts:
```shell
mysql.server start
```
```shell
mysql -u root -p
```

```shell
SHOW DATABASES;
CREATE DATABASE students_courses_db;
USE students_courses_db;
SHOW tables;
```
### DB Scheme
Will auto generated - no need to execute 
```shell
CREATE TABLE sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_key VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE admin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
  
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    special_key VARCHAR(255) NOT NULL
);
  
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE students_courses (
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);
```
And finally, stop the server
```shell
mysql.server stop
```

# Instructions for how to run
1. Run the application using Java 17
2. DB tables will be automatically generated.
3. At the next section of this file, you'll find all http requests written, ready to run. Just need to change credentials.
4. First, login as admin. Admin is pre-generated at application startup. His credentials are defined at `StudentsCoursesApplication.java`.
   - Once logged in, you will get a `session-key`. use it for all the other admin's operations on students and courses.
5. After creating a student, you'll get his `special_key` (In Console, not in Terminal).
   - Use student's `email` and `special_key` to login.
   - After login you can perform operation on courses as student like register and cancel.



# HTTP Requests
(Instead of Postman collection)

## ADMIN
**login**
```shell
curl -X POST http://localhost:8080/login \
  -H 'Content-Type: application/json' \
  -d '{"username": "admin@gmail.com", "password": "admin_password"}'
```
### Operations on student:
#### POST create student
```shell
curl -s -X POST http://localhost:8080/admin/student \
  -H "Session-Key: 18645b98-6e8e-4ad4-9199-9f37b7bf2491" \
  -H 'Content-Type: application/json' \
  -d '{"name": "r", "email": "r@r.com"}' | jq
```
#### GET all students
```shell
curl -s http://localhost:8080/admin/student \
  -H "Session-Key: 18645b98-6e8e-4ad4-9199-9f37b7bf2491" | jq
```
#### GET student by id
```shell
curl -s http://localhost:8080/admin/student/id/15 \
  -H "Session-Key: 697b895f-bb6b-4ba4-81f2-8eb0c18980c8" | jq
```
#### GET student by email
```shell
curl -s http://localhost:8080/admin/student/email/bob%40gmail.com \
  -H "Session-Key: 697b895f-bb6b-4ba4-81f2-8eb0c18980c8" | jq
```
#### PUT update student
```shell
curl -s -X PUT http://localhost:8080/admin/student/19 \
    -H "Session-Key: 697b895f-bb6b-4ba4-81f2-8eb0c18980c8" \
  -H 'Content-Type: application/json' \
  -d '{"name": "i8"}' | jq
```
#### DELETE student by id
```shell
curl -s -X DELETE http://localhost:8080/admin/student/id/21 \
  -H "Session-Key: a7c20100-351a-43a7-80e7-20de3e39e014" | jq
```
#### DELETE student by email
```shell
curl -s -X DELETE http://localhost:8080/admin/student/email/fox1%40gmail.com \
  -H "Session-Key: f7e8c3da-a029-444b-b847-11d579d83130" | jq
```

### Operations on course
#### POST create course
```shell
curl -s -X POST http://localhost:8080/admin/course \
  -H "Session-Key: 66231c36-2cd3-4a12-8e84-2b5a925b9d53" \
  -H 'Content-Type: application/json' \
  -d '{"name": "Physics", "description": "intro"}' | jq
```
#### GET all courses
```shell
curl -s http://localhost:8080/admin/course \
  -H "Session-Key: a7c20100-351a-43a7-80e7-20de3e39e014" | jq
```
#### GET course by id
```shell
curl -s http://localhost:8080/admin/course/id/9 \
  -H "Session-Key: 697b895f-bb6b-4ba4-81f2-8eb0c18980c8" | jq
```
#### GET course by name
```shell
curl -s http://localhost:8080/admin/course/name/css \
  -H "Session-Key: 697b895f-bb6b-4ba4-81f2-8eb0c18980c8" | jq
```
#### Put update course
```shell
curl -s -X PUT http://localhost:8080/admin/course/9 \
  -H "Session-Key: 697b895f-bb6b-4ba4-81f2-8eb0c18980c8" \
  -H 'Content-Type: application/json' \
  -d '{"name": "Physics_2" "description": "sports"}' | jq
```
#### DELETE course by id
```shell
curl -s -X DELETE http://localhost:8080/admin/course/id/10 \
  -H "Session-Key: 697b895f-bb6b-4ba4-81f2-8eb0c18980c8" | jq
```
#### DELETE course by name
```shell
curl -s -X DELETE http://localhost:8080/admin/course/name/Gym \
  -H "Session-Key: f7e8c3da-a029-444b-b847-11d579d83130" | jq
```

## Student
login
```shell
curl -X POST http://localhost:8080/login \
  -H 'Content-Type: application/json' \
  -d '{"username": "k@k.com", "password": "df4938ed-c293-4586-a57f-cbd6e720a793"}'
```
### Operations on course
#### GET registered courses
```shell
curl -s http://localhost:8080/student/course \
  -H "Session-Key: d6382b55-157e-4b24-87d7-97f2f11f5841" | jq
```
#### POST register for a course
```shell
curl -s -X POST http://localhost:8080/student/course/register/3 \
  -H "Session-Key: d6382b55-157e-4b24-87d7-97f2f11f5841" | jq
```
#### DELETE cancel registration for a course
```shell
curl -s -X DELETE http://localhost:8080/student/course/cancel/1 \
  -H "Session-Key: d6382b55-157e-4b24-87d7-97f2f11f5841" | jq
```