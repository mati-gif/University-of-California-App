package com.SchoolApplication.UC.dtos;


import com.SchoolApplication.UC.models.Course;
import com.SchoolApplication.UC.models.CourseSchedule;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseDtoForCourseController {

    private long id;
    private String nameSubject;
    private String yearCourse;
    private int maxCapacity;
    private Set<CourseScheduleDto> schedules = new HashSet<>();



    public CourseDtoForCourseController(Course course) {
        this.id = course.getId();
        this.nameSubject = course.getNameSubject();
        this.yearCourse = course.getYearCourse();
        this.maxCapacity = course.getMaxCapacity();
        this.schedules = convertCourseSchedulesToDto(course.getCourseSchedules());
    }


    private Set<CourseScheduleDto> convertCourseSchedulesToDto(Set<CourseSchedule> schedules){

        return schedules.stream()
                .map(CourseScheduleDto::new)
                .collect(Collectors.toSet());
    }

    public Set<CourseScheduleDto> getSchedules() {
        return schedules;
    }

    public long getId() {
        return id;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public String getYearCourse() {
        return yearCourse;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
