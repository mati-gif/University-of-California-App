package com.SchoolApplication.UC.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

//Tabla intermedia entre StudentCourse y CourseSchedule esto sirve porque un curso puede tener distintas horas de cursado entoces de esta forma estoy asociadno uno de esos horarios de cursado a un curso ya asociado a un alumno
@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String statusEnrollment;// Opcional, para representar el estado de la inscripción (e.g., "active", "withdrawn").



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_course_id")  // Clave foránea hacia StudentCourse
    private StudentCourse studentCourse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_schedule_id")  // Clave foránea hacia CourseSchedule
    private CourseSchedule courseSchedule;

    public Enrollment(String statusEnrollment) {
        this.statusEnrollment = statusEnrollment;
    }

    public Enrollment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatusEnrollment() {
        return statusEnrollment;
    }

    public void setStatusEnrollment(String statusEnrollment) {
        this.statusEnrollment = statusEnrollment;
    }

    public StudentCourse getStudentCourse() {
        return studentCourse;
    }

    public void setStudentCourse(StudentCourse studentCourse) {
        this.studentCourse = studentCourse;
    }

    public CourseSchedule getCourseSchedule() {
        return courseSchedule;
    }

    public void setCourseSchedule(CourseSchedule courseSchedule) {
        this.courseSchedule = courseSchedule;
    }



}
