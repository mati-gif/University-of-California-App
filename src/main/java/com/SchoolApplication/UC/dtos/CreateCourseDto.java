package com.SchoolApplication.UC.dtos;

import com.SchoolApplication.UC.models.CourseSchedule;

import java.util.List;
import java.util.Set;

public record CreateCourseDto(String nameSubject, String yearCourse, Integer maxCapacity,
                              List<CourseScheduleDto> schedules) {

//    Set<CourseScheduleDto> schedules,
}
