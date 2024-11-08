package com.SchoolApplication.UC.dtos;

import com.SchoolApplication.UC.models.Teacher;

import java.util.Set;
import java.util.stream.Collectors;

public class TeacherDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<CourseDtoForTeacher> courses;



    public TeacherDto(Teacher teacher) {
        this.id = teacher.getId();
        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
        this.email = teacher.getEmail();

        // Convertir `TeacherCourse` a `CourseDto`
        this.courses = teacher.getTeacherCourses().stream()
                .map(teacherCourse -> new CourseDtoForTeacher(teacherCourse.getCourse()))
                .collect(Collectors.toSet());

    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<CourseDtoForTeacher> getCourses() {
        return courses;
    }
}
