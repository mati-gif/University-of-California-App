package com.SchoolApplication.UC.dtos;

import com.SchoolApplication.UC.models.Course;
import com.SchoolApplication.UC.models.CourseSchedule;
import com.SchoolApplication.UC.models.TeacherCourse;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseDto {
    private long id;

    private String nameSubject;
    private String yearCourse;
    private int maxCapacity;
//    private Set<CourseScheduleDto> schedules = new HashSet<>();//aca se crea una variable privada de tipo Set<CourseScheduleDto> llamada courseScheduleDtos. Tambien se puede decir : Ceclara un set llamado courseScheduleDtos que tendra objetos de tipo CourseScheduleDto.

    private Set<TeacherCourseDto> teacherCourses;



    public CourseDto(Course course) {
        this.id = course.getId();
        this.nameSubject = course.getNameSubject();
        this.yearCourse = course.getYearCourse();
        this.maxCapacity = course.getMaxCapacity();

        System.out.println(convertCourseSchedulesToDto(course.getCourseSchedules()) + " " +  "hola funciono el sout");

        System.out.println(course.getCourseSchedules() + " " +  "hola funciono el sout");

//        this.schedules = convertCourseSchedulesToDto(course.getCourseSchedules());
        this.teacherCourses = course.getTeacherCourses().stream().map(TeacherCourseDto::new).collect(Collectors.toSet());
    }

    private Set<CourseScheduleDto> convertCourseSchedulesToDto(Set<CourseSchedule> schedules){

        System.out.println("Schedules en CourseDto: " + schedules);
        return schedules.stream()
                .map(CourseScheduleDto::new)
                .collect(Collectors.toSet());
    }

    public CourseDto() {
    }

    public long getId() {
        return id;
    }

    public String getNameSubject() {
        return nameSubject;
    }

//    public Set<CourseScheduleDto> getSchedules() {
//        return schedules;
//    }

    public String getYearCourse() {
        return yearCourse;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public Set<TeacherCourseDto> getTeacherCourses() {
        return teacherCourses;
    }
}
