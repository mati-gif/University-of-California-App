package com.SchoolApplication.UC.dtos;

import java.util.List;

public record CreateCourseDto(String nameSubject, String yearCourse, Integer maxCapacity,
                              List<CourseScheduleDto> schedules) {
}
