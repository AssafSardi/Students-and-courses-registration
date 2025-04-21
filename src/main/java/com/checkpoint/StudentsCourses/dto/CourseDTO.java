package com.checkpoint.StudentsCourses.dto;

import java.util.Set;

public class CourseDTO {
    private Long id;
    private String name;
    private String description;
    private Set<Long> studentIds;

    public CourseDTO() {}

    public CourseDTO(Long id, String name, String description, Set<Long> studentIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.studentIds = studentIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Set<Long> studentIds) {
        this.studentIds = studentIds;
    }
}
