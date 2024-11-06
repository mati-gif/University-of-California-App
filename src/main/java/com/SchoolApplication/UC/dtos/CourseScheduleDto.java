package com.SchoolApplication.UC.dtos;


import com.SchoolApplication.UC.models.CourseSchedule;
import com.SchoolApplication.UC.models.Shift;

import java.util.List;

public class CourseScheduleDto {

    private Long id;
    private long scheduleId;
    private String dayOfWeek; // Renombrado en el modelo
    private String time;

//    private Shift shift;
//    private List<String> days;
//    private List<String> times;

//    private ScheduleDto moreInfoAboutSchedule;

    public CourseScheduleDto(CourseSchedule courseSchedule) {
        this.id = courseSchedule.getId();
        this.scheduleId = courseSchedule.getSchedule().getId();
        this.dayOfWeek = courseSchedule.getDayOfWeek();
        this.time = courseSchedule.getTime();

//        this.shift = courseSchedule.getSchedule().getShift();
//        this.days = courseSchedule.getSchedule().getDays();
//        this.times = courseSchedule.getSchedule().getTimes();

//        this.moreInfoAboutSchedule = new ScheduleDto(courseSchedule.getSchedule());
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

//    public ScheduleDto getMoreInfoAboutSchedule() {
//        return moreInfoAboutSchedule;
//    }


//        public Shift getShift() {
//        return shift;
//    }
//
//    public List<String> getDays() {
//        return days;
//    }
//
//    public List<String> getTimes() {
//        return times;
//    }
}
