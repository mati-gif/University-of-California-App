package com.SchoolApplication.UC.dtos;

import com.SchoolApplication.UC.models.Enrollment;

public class EnrollmentDto {

    private Long id;
    private String statusEnrollment;
    private CourseScheduleDto courseSchedule; // Información sobre el horario específico del curso

    public EnrollmentDto(Enrollment enrollment) {
        this.id = enrollment.getId();
        this.statusEnrollment = enrollment.getStatusEnrollment();
        this.courseSchedule = new CourseScheduleDto(enrollment.getCourseSchedule());
    }

    public Long getId() {
        return id;
    }

    public String getStatusEnrollment() {
        return statusEnrollment;
    }

    public CourseScheduleDto getCourseSchedule() {
        return courseSchedule;
    }
}
