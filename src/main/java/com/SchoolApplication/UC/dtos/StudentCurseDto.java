package com.SchoolApplication.UC.dtos;


import com.SchoolApplication.UC.models.Status;
import com.SchoolApplication.UC.models.StudentCurse;

import java.time.LocalDateTime;

public class StudentCurseDto {

    private Long id;
    private long curseId;
    private LocalDateTime registrationDate;
    private Status status;
    private double monthlyAttendancePercentage;

    public StudentCurseDto(StudentCurse studentCurse) {
        this.id = studentCurse.getId(); //Asignando el valor del Id del objeto studentCurse al id de studentCurseDto
        this.curseId = studentCurse.getCurse().getId();
        this.registrationDate = studentCurse.getRegistrationDate();
        this.status = studentCurse.getStatus();
        this.monthlyAttendancePercentage = studentCurse.getMonthlyAttendancePercentage();
    }

    public Long getId() {
        return id;
    }

    public long getCurseId() {
        return curseId;
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
