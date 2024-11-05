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
//    private  int attendancePorcentage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_course_id")
    private StudentCourse studentCourse;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "teacher_course_id")
//    private TeacherCourse teacherCourse;


    public Attendance(LocalDateTime date, StatusAttendance statusAttendance) {
        this.date = date;
        this.statusAttendance = statusAttendance;
//        this.attendancePorcentage = attendancePorcentage;
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

    public StudentCourse getStudentCourse() {
        return studentCourse;
    }

    public void setStudentCourse(StudentCourse studentCourse) {
        this.studentCourse = studentCourse;
//        studentCourse.addAttendance(this); // Actualiza el StudentCourse cuando se asigna una nueva asistencia
    }



}
