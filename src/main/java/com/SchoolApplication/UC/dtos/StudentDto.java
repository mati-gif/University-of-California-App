package com.SchoolApplication.UC.dtos;


import com.SchoolApplication.UC.models.Student;
import com.SchoolApplication.UC.models.StudentCurse;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private Set<StudentCurseDto> curses ; // Declara una variable privada de tipo Set<StudentCurseDto> llamada curses. Tambien se puede decir : Ceclara un set llamado curses que tendra objetos de tipo StudentCurseDto.

    public StudentDto(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
        this.curses = convertStudentCursesToDto(student.getStudentCurses());

    }

    private Set<StudentCurseDto> convertStudentCursesToDto(Set<StudentCurse> curses){
        return curses.stream()
                .map(StudentCurseDto::new)
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

    public Set<StudentCurseDto> getCurses() {
        return curses;
    }
}
