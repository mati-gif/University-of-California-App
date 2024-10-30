package com.SchoolApplication.UC.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TeacherCurse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private LocalDateTime assignmentDate;
    private RoleTeacher roleTeacher;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curse_id")
    private Curse curse;


    public TeacherCurse(LocalDateTime assignmentDate, RoleTeacher roleTeacher) {
        this.assignmentDate = assignmentDate;
        this.roleTeacher = roleTeacher;
    }

    public TeacherCurse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(LocalDateTime assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public RoleTeacher getRoleTeacher() {
        return roleTeacher;
    }

    public void setRoleTeacher(RoleTeacher roleTeacher) {
        this.roleTeacher = roleTeacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Curse getCurse() {
        return curse;
    }

    public void setCurse(Curse curse) {
        this.curse = curse;
    }
}
