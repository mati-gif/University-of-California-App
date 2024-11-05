package com.SchoolApplication.UC.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Teacher extends Usuarioo {

    public Teacher(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    // Constructor vacío necesario para JPA
    public Teacher() {
        super(); // Llama al constructor vacío de Usuarioo
    }

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
     Set<TeacherCourse> teacherCourses = new HashSet<>();


    public Set<TeacherCourse> getTeacherCourses() {
        return teacherCourses;
    }

    public void setTeacherCourses(Set<TeacherCourse> teacherCurs) {
        this.teacherCourses = teacherCurs;
    }



    public void addTeacherCourse(TeacherCourse teacherCourse) {
        teacherCourse.setTeacher(this);
        teacherCourses.add(teacherCourse);
    }

    public List<Course> getCourses() {
        return teacherCourses.stream().map(c -> c.getCourse()).collect(Collectors.toList());
    }
}
