package com.SchoolApplication.UC.dtos;


import com.SchoolApplication.UC.models.Shift;
import com.SchoolApplication.UC.models.Status;
import com.SchoolApplication.UC.models.StudentCourse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class StudentCourseDto {

    private Long id;
    private long courseId;
    private LocalDateTime registrationDate;
    private Status status;
    private double monthlyAttendancePercentage;
    private CourseDto moreInfoAboutCourse;
    private List<EnrollmentDto> enrollments; // Cambiado a List<EnrollmentDto>
//



    public StudentCourseDto(StudentCourse studentCourse) {
        this.id = studentCourse.getId(); //Asignando el valor del Id del objeto studentCourse al id de studentCourseDto
        this.courseId = studentCourse.getCourse().getId();
        this.registrationDate = studentCourse.getRegistrationDate();
        this.status = studentCourse.getStatus();

        this.monthlyAttendancePercentage = studentCourse.getMonthlyAttendancePercentage();
        this.moreInfoAboutCourse = new CourseDto(studentCourse.getCourse());

        // Obtener inscripciones (enrollments) relacionadas
        this.enrollments = studentCourse.getEnrollments().stream()
                .map(EnrollmentDto::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public long getCourseId() {
        return courseId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public Status getStatus() {
        return status;
    }

    public double getMonthlyAttendancePercentage() {
        return monthlyAttendancePercentage;
    }

    public CourseDto getMoreInfoAboutCourse() {
        return moreInfoAboutCourse;
    }

    public List<EnrollmentDto> getEnrollments() {
        return enrollments;
    }
}
