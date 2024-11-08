package com.SchoolApplication.UC.dtos;


import com.SchoolApplication.UC.models.Attendance;
import com.SchoolApplication.UC.models.Course;
import com.SchoolApplication.UC.models.Enrollment;
import com.SchoolApplication.UC.models.StatusAttendance;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceDto {

    private long id;

    private LocalDateTime date;
    private StatusAttendance statusAttendance;
    private double monthlyAttendancePercentage;
    private Set<EnrollmentDto> enrollments; // Cambiado a List<EnrollmentDto>
    private Set<CourseDto> moreInfoAboutCourse;




    public AttendanceDto(Attendance attendance) {
        this.id = attendance.getId();
        this.date = attendance.getDate();
        this.statusAttendance = attendance.getStatusAttendance();

        this.monthlyAttendancePercentage = attendance.getStudentCourse().getMonthlyAttendancePercentage();
        this.enrollments = convertStudentCoursesToDto(attendance.getStudentCourse().getEnrollments());

        this.moreInfoAboutCourse = convertCourseToDto(attendance.getStudentCourse().getCourse());
    }


    private Set<CourseDto> convertCourseToDto(Course courses) {
        return Collections.singleton(new CourseDto(courses));
    }

    private Set<EnrollmentDto> convertStudentCoursesToDto(Set<Enrollment> enrollments) {
        return enrollments.stream()
                .map(EnrollmentDto::new)
                .collect(Collectors.toSet());
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

    public double getMonthlyAttendancePercentage() {
        return monthlyAttendancePercentage;
    }

    public Set<EnrollmentDto> getEnrollments() {
        return enrollments;
    }

    public Set<CourseDto> getMoreInfoAboutCourse() {
        return moreInfoAboutCourse;
    }
}
