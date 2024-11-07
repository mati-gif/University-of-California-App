package com.SchoolApplication.UC.dtos;

import com.SchoolApplication.UC.models.CourseSchedule;

public class CourseScheduleDtoForCourseController {

        private Long id;
        private ScheduleDto moreInfoAboutSchedule;


    public CourseScheduleDtoForCourseController(CourseSchedule courseSchedule) {

        this.id = courseSchedule.getId();

        this.moreInfoAboutSchedule = new ScheduleDto(courseSchedule.getSchedule());
    }

    public Long getId() {
        return id;
    }

    public ScheduleDto getMoreInfoAboutSchedule() {
        return moreInfoAboutSchedule;
    }
}
