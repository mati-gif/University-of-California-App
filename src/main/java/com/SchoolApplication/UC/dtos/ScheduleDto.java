package com.SchoolApplication.UC.dtos;


import com.SchoolApplication.UC.models.Schedule;
import com.SchoolApplication.UC.models.Shift;

import java.util.List;

public class ScheduleDto {

    private long id;
    private Shift shift;
    private List<String> days;
    private List<String> times;

    public ScheduleDto(Schedule schedule) {
        this.id = schedule.getId();
        this.shift = schedule.getShift();
        this.days = schedule.getDays();
        this.times = schedule.getTimes();
    }


    public ScheduleDto() {
    }

    public long getId() {
        return id;
    }

    public Shift getShift() {
        return shift;
    }

    public List<String> getDays() {
        return days;
    }

    public List<String> getTimes() {
        return times;
    }
}
