package com.SchoolApplication.UC.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "studentCurse", fetch = FetchType.EAGER)
    private Set<Attendance> attendances = new HashSet<>();

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


    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void addAttendance(Attendance attendance) {
        attendance.setStudentCurse(this); // Establece el estudiante de la asistencia
        attendances.add(attendance);
    }

    public List<TeacherCurse> getCurseTeachers() {
        return attendances.stream().map(a -> a.getTeacherCurse()).collect(Collectors.toList());
    }
}
