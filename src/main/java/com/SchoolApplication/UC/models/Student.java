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
    Set<StudentCurse> studentCurses = new HashSet<>();

    public Set<StudentCurse> getStudentCurses() {
        return studentCurses;
    }

    public void setStudentCurses(Set<StudentCurse> studentCurses) {
        this.studentCurses = studentCurses;
    }


    public void addStudentCurse(StudentCurse studentCurse) {
        studentCurse.setStudent(this); //asigna la instancia actual de Student,Esto asegura que StudentCurse tenga una referencia clara al Student al que está asociado
        studentCurses.add(studentCurse);
    }

    public List<Curse> getCurses() {
        return studentCurses.stream().map(c -> c.getCurse()).collect(Collectors.toList());
    }

}
