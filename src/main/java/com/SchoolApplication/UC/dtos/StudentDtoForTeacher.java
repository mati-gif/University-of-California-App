package com.SchoolApplication.UC.dtos;

import com.SchoolApplication.UC.models.Student;

public class StudentDtoForTeacher {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;


    public StudentDtoForTeacher(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
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
}
