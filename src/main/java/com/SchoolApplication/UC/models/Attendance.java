package com.SchoolApplication.UC.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime date;
    private StatusAttendance statusAttendance;
    private  int attendancePorcentage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_curse_id")
    private StudentCurse studentCurse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_curse_id")
    private TeacherCurse teacherCurse;

    public Attendance(LocalDateTime date, StatusAttendance statusAttendance,int attendancePorcentage) {
        this.date = date;
        this.statusAttendance = statusAttendance;
        this.attendancePorcentage = attendancePorcentage;
    }

    public Attendance() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public StatusAttendance getStatusAttendance() {
        return statusAttendance;
    }

    public void setStatusAttendance(StatusAttendance statusAttendance) {
        this.statusAttendance = statusAttendance;
    }

    public StudentCurse getStudentCurse() {
        return studentCurse;
    }

    public void setStudentCurse(StudentCurse studentCurse) {
        this.studentCurse = studentCurse;
    }

    public TeacherCurse getTeacherCurse() {
        return teacherCurse;
    }

    public void setTeacherCurse(TeacherCurse teacherCurse) {
        this.teacherCurse = teacherCurse;
    }

    public int getAttendancePorcentage() {
        return attendancePorcentage;
    }

    public void setAttendancePorcentage(int attendancePorcentage) {
        this.attendancePorcentage = attendancePorcentage;
    }

}
