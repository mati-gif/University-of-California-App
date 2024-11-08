package com.SchoolApplication.UC.dtos;


import com.SchoolApplication.UC.models.Attendance;
import com.SchoolApplication.UC.models.StatusAttendance;

import java.time.LocalDateTime;

public class AttendanceDto {

    private long id;

    private LocalDateTime date;
    private StatusAttendance statusAttendance;

    public AttendanceDto(Attendance attendance) {
        this.id = attendance.getId();
        this.date = attendance.getDate();
        this.statusAttendance = attendance.getStatusAttendance();
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public StatusAttendance getStatusAttendance() {
        return statusAttendance;
    }
}
