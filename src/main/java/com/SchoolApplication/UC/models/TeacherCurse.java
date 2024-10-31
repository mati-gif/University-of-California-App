package com.SchoolApplication.UC.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class TeacherCurse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private LocalDateTime assignmentDate;
    private RoleTeacher roleTeacher;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curse_id")
    private Curse curse;

//    @OneToMany(mappedBy = "teacherCurse", fetch = FetchType.EAGER)
//    private Set<Attendance> attendances = new HashSet<>();


    public TeacherCurse(LocalDateTime assignmentDate, RoleTeacher roleTeacher) {
        this.assignmentDate = assignmentDate;
        this.roleTeacher = roleTeacher;
    }

    public TeacherCurse() {
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

    public Curse getCurse() {
        return curse;
    }

    public void setCurse(Curse curse) {
        this.curse = curse;
    }

//    public Set<Attendance> getAttendances() {
//        return attendances;
//    }
//
//    public void setAttendances(Set<Attendance> attendances) {
//        this.attendances = attendances;
//    }

//    public void addAttendance(Attendance attendance) {
//        attendance.setTeacherCurse(this); // Establece el profesor de la asistencia
//        attendances.add(attendance);
//    }


//    public List<StudentCurse> getStudentCurses() {
//        return attendances.stream().map(a -> a.getStudentCurse()).collect(Collectors.toList());
//    }
}
