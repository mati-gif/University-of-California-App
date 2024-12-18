package com.SchoolApplication.UC.dtos;

import com.SchoolApplication.UC.models.CourseSchedule;

import java.util.List;
import java.util.Set;

public record CreateCourseDto(String nameSubject, String yearCourse, Integer maxCapacity,
                              Long scheduleId, String dayOfWeek, String time, String shift,Long courseId) {

//    Set<CourseScheduleDto> schedules,
}
