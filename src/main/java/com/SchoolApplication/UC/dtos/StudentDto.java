package com.SchoolApplication.UC.dtos;


import com.SchoolApplication.UC.models.Student;
import com.SchoolApplication.UC.models.StudentCourse;

import java.util.Set;
import java.util.stream.Collectors;

public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private Set<StudentCourseDto> courses ; // Declara una variable privada de tipo Set<StudentCourseDto> llamada courses. Tambien se puede decir : Ceclara un set llamado courses que tendra objetos de tipo StudentCourseDto.

    public StudentDto(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
        this.courses = convertStudentCoursesToDto(student.getStudentCourses());

    }

    private Set<StudentCourseDto> convertStudentCoursesToDto(Set<StudentCourse> courses) {
        return courses.stream()
                .map(StudentCourseDto::new)
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

    public Set<StudentCourseDto> getCourses() {
        return courses;
    }
}
