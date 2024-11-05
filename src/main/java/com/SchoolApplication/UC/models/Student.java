package com.SchoolApplication.UC.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
public class Student extends Usuarioo {



    // Constructor parametrizado incluyendo atributos heredados
    public Student(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password); // Constructor de Usuarioo,hereda esas propiedades
    }

    // Constructor vacío necesario para JPA
    public Student() {
        super(); // Llama al constructor vacío de Usuarioo
    }

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    Set<StudentCourse> studentCourses = new HashSet<>();

    public Set<StudentCourse> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(Set<StudentCourse> studentCourses) {
        this.studentCourses = studentCourses;
    }


    public void addStudentCourse(StudentCourse studentCourse) {
        studentCourse.setStudent(this); //asigna la instancia actual de Student,Esto asegura que StudentCourse tenga una referencia clara al Student al que está asociado
        studentCourses.add(studentCourse);
    }

    public List<Course> getCourses() {
        return studentCourses.stream().map(c -> c.getCourse()).collect(Collectors.toList());
    }

}
