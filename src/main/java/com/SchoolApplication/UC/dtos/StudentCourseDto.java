package com.SchoolApplication.UC.dtos;


import com.SchoolApplication.UC.models.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentCourseDto {

    private Long id;
    private long courseId;
    private LocalDateTime registrationDate;
    private Status status;
    private double monthlyAttendancePercentage;
//    private CourseDto moreInfoAboutCourse;
    private Set<CourseDto> moreInfoAboutCourse;
    private Set<EnrollmentDto> enrollments; // Cambiado a List<EnrollmentDto>
//    private Set<TeacherCourseDto> teacherCourses;





    public StudentCourseDto(StudentCourse studentCourse) {
        this.id = studentCourse.getId(); //Asignando el valor del Id del objeto studentCourse al id de studentCourseDto
        this.courseId = studentCourse.getCourse().getId();
        this.registrationDate = studentCourse.getRegistrationDate();
        this.status = studentCourse.getStatus();

        this.monthlyAttendancePercentage = studentCourse.getMonthlyAttendancePercentage();
//        this.moreInfoAboutCourse = new CourseDto(studentCourse.getCourse());

//        this.teacherCourses = studentCourse.getCourse().getTeacherCourses().stream().map(TeacherCourseDto::new).collect(Collectors.toSet());

        this.moreInfoAboutCourse = convertCourseToDto(studentCourse.getCourse());

        // Obtener inscripciones (enrollments) relacionadas
        this.enrollments = convertStudentCoursesToDto(studentCourse.getEnrollments());
    }

    private Set<EnrollmentDto> convertStudentCoursesToDto(Set<Enrollment> enrollments) {
        return enrollments.stream()
                .map(EnrollmentDto::new)
                .collect(Collectors.toSet());
    }

    private Set<CourseDto> convertCourseToDto(Course courses) {
        return Collections.singleton(new CourseDto(courses));
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

//    public CourseDto getMoreInfoAboutCourse() {
//        return moreInfoAboutCourse;
//    }

    public Set<EnrollmentDto> getEnrollments() {
        return enrollments;
    }

    public Set<CourseDto> getMoreInfoAboutCourse() {
        return moreInfoAboutCourse;
    }

//    public Set<TeacherCourseDto> getTeacherCourses() {
//        return teacherCourses;
//    }
}
