package com.SchoolApplication.UC.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class CourseSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dayOfWeek; // Renombrado en el modelo
    private String time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")//Esta seria la clave foranea de la tabla course
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;//Esta seria la clave foranea de la tabla schedule


    @OneToMany(mappedBy = "courseSchedule", fetch = FetchType.EAGER)
    private Set<Enrollment> enrollments = new HashSet<>();



    public CourseSchedule(String dayOfWeek, String time) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
    }

    public CourseSchedule() {
    }


    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void addEnrollment(Enrollment enrollment){
        enrollment.setCourseSchedule(this);
        enrollments.add(enrollment);
    }
}
