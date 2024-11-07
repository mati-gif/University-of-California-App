package com.SchoolApplication.UC.dtos;

import com.SchoolApplication.UC.models.RoleTeacher;
import com.SchoolApplication.UC.models.TeacherCourse;

import java.time.LocalDateTime;

public class TeacherCourseDto {

    private long id;


    private LocalDateTime assignmentDate;
    private RoleTeacher roleTeacher;
    private TeacherDto teacher;


    public TeacherCourseDto(TeacherCourse teacherCourse) {
        this.id = teacherCourse.getId();
        this.assignmentDate = teacherCourse.getAssignmentDate();
        this.roleTeacher = teacherCourse.getRoleTeacher();

        this.teacher = new TeacherDto(teacherCourse.getTeacher());
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getAssignmentDate() {
        return assignmentDate;
    }

    public RoleTeacher getRoleTeacher() {
        return roleTeacher;
    }

    public TeacherDto getTeacher() {
        return teacher;
    }
}
