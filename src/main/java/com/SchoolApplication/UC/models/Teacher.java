package com.SchoolApplication.UC.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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
    private Set<TeacherCurse> teacherCurses = new HashSet<>();


    public Set<TeacherCurse> getTeacherCurses() {
        return teacherCurses;
    }

    public void setTeacherCurses(Set<TeacherCurse> teacherCurses) {
        this.teacherCurses = teacherCurses;
    }



    public void addTeacherCurse(TeacherCurse teacherCurse) {
        teacherCurse.setTeacher(this);
        teacherCurses.add(teacherCurse);
    }

    public List<Curse> getCurses() {
        return teacherCurses.stream().map(c -> c.getCurse()).collect(Collectors.toList());
    }
}
