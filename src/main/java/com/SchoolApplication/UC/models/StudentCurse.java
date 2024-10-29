package com.SchoolApplication.UC.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class StudentCurse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime registrationDate;
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curse_id")
    private Curse curse;

    public StudentCurse(LocalDateTime registrationDate, Status status) {
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public StudentCurse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Curse getCurse() {
        return curse;
    }

    public void setCurse(Curse curse) {
        this.curse = curse;
    }
}
