package com.SchoolApplication.UC.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TeacherCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private LocalDateTime assignmentDate;
    private RoleTeacher roleTeacher;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

//    @OneToMany(mappedBy = "teacherCourse", fetch = FetchType.EAGER)
//    private Set<Attendance> attendances = new HashSet<>();


    public TeacherCourse(LocalDateTime assignmentDate, RoleTeacher roleTeacher) {
        this.assignmentDate = assignmentDate;
        this.roleTeacher = roleTeacher;
    }

    public TeacherCourse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(LocalDateTime assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public RoleTeacher getRoleTeacher() {
        return roleTeacher;
    }

    public void setRoleTeacher(RoleTeacher roleTeacher) {
        this.roleTeacher = roleTeacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

//    public Set<Attendance> getAttendances() {
//        return attendances;
//    }
//
//    public void setAttendances(Set<Attendance> attendances) {
//        this.attendances = attendances;
//    }

//    public void addAttendance(Attendance attendance) {
//        attendance.setTeacherCourse(this); // Establece el profesor de la asistencia
//        attendances.add(attendance);
//    }


//    public List<StudentCourse> getStudentCourses() {
//        return attendances.stream().map(a -> a.getStudentCourse()).collect(Collectors.toList());
//    }
}
