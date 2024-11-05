package com.SchoolApplication.UC.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nameSubject;
    private String yearCourse;
    private int maxCapacity;

    @OneToMany(mappedBy = "course" , fetch = FetchType.EAGER)
    Set<CourseSchedule> courseSchedules = new HashSet<>();

    @OneToMany(mappedBy = "course" , fetch = FetchType.EAGER)
    Set<StudentCourse> studentCourses = new HashSet<>();

    @OneToMany(mappedBy = "course" , fetch = FetchType.EAGER)
    Set<TeacherCourse> teacherCourses = new HashSet<>();

    public Course(String nameSubject, String yearCourse, int maxCapacity) {
        this.nameSubject = nameSubject;
        this.yearCourse = yearCourse;
        this.maxCapacity = maxCapacity;
    }

    public Course() {
    }

    public Set<StudentCourse> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(Set<StudentCourse> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public Set<TeacherCourse> getTeacherCourses() {
        return teacherCourses;
    }

    public void setTeacherCourses(Set<TeacherCourse> teacherCourses) {
        this.teacherCourses = teacherCourses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getYearCourse() {
        return yearCourse;
    }

    public void setYearCourse(String yearCourse) {
        this.yearCourse = yearCourse;
    }

    public Set<CourseSchedule> getCourseSchedules() {
        return courseSchedules;
    }

    public void setCourseSchedules(Set<CourseSchedule> courseSchedules) {
        this.courseSchedules = courseSchedules;
    }

    public void addCourseSchedule(CourseSchedule courseSchedule) {
        courseSchedule.setCourse(this);
        courseSchedules.add(courseSchedule);
    }


    public List<Schedule> getSchedules(){
        return courseSchedules.stream().map(c -> c.getSchedule()).collect(Collectors.toList());
    }

    public void addStudentCourse(StudentCourse studentCourse) {
        studentCourse.setCourse(this);
        studentCourses.add(studentCourse);
    }

    public List<Student> getStudents(){
        return studentCourses.stream().map(c -> c.getStudent()).collect(Collectors.toList());
    }

    public void addTeacherCourse(TeacherCourse teacherCourse) {
        teacherCourse.setCourse(this);
        teacherCourses.add(teacherCourse);
    }

    public List<Teacher> getTeachers(){
        return teacherCourses.stream().map(c -> c.getTeacher()).collect(Collectors.toList());
    }




}
