package com.SchoolApplication.UC.dtos;


import com.SchoolApplication.UC.models.Status;
import com.SchoolApplication.UC.models.StudentCourse;

import java.time.LocalDateTime;

public class StudentCourseDto {

    private Long id;
    private long courseId;
    private LocalDateTime registrationDate;
    private Status status;
    private double monthlyAttendancePercentage;

    public StudentCourseDto(StudentCourse studentCourse) {
        this.id = studentCourse.getId(); //Asignando el valor del Id del objeto studentCourse al id de studentCourseDto
        this.courseId = studentCourse.getCourse().getId();
        this.registrationDate = studentCourse.getRegistrationDate();
        this.status = studentCourse.getStatus();
        this.monthlyAttendancePercentage = studentCourse.getMonthlyAttendancePercentage();
    }

    public Long getId() {
        return id;
    }

    public long getCourseId() {
        return courseId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public Status getStatus() {
        return status;
    }

    public double getMonthlyAttendancePercentage() {
        return monthlyAttendancePercentage;
    }
}
