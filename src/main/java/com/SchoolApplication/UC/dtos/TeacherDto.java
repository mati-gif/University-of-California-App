package com.SchoolApplication.UC.dtos;

import com.SchoolApplication.UC.models.Teacher;

public class TeacherDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;


    public TeacherDto(Teacher teacher) {
        this.id = teacher.getId();
        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
        this.email = teacher.getEmail();
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
