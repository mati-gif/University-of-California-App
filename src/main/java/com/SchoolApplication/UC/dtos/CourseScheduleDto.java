package com.SchoolApplication.UC.dtos;


import com.SchoolApplication.UC.models.CourseSchedule;

public class CourseScheduleDto {

    private Long id;
    private long scheduleId;
    private String dayOfWeek; // Renombrado en el modelo
    private String time;


    public CourseScheduleDto(CourseSchedule courseSchedule) {
        this.id = courseSchedule.getId();
        this.scheduleId = courseSchedule.getSchedule().getId();
        this.dayOfWeek = courseSchedule.getDayOfWeek();
        this.time = courseSchedule.getTime();
    }

    public Long getId() {
        return id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public long getScheduleId() {
        return scheduleId;
    }
}
