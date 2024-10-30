package com.SchoolApplication.UC.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Curse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nameSubject;
    private String yearCurse;
    private int maxCapacity;

    @OneToMany(mappedBy = "curse" , fetch = FetchType.EAGER)
    Set<CurseSchedule> curseSchedules = new HashSet<>();

    @OneToMany(mappedBy = "curse" , fetch = FetchType.EAGER)
    Set<StudentCurse> studentCurses = new HashSet<>();

    @OneToMany(mappedBy = "curse" , fetch = FetchType.EAGER)
    Set<TeacherCurse> teacherCurses = new HashSet<>();

    public Curse(String nameSubject, String yearCurse, int maxCapacity) {
        this.nameSubject = nameSubject;
        this.yearCurse = yearCurse;
        this.maxCapacity = maxCapacity;
    }

    public Curse() {
    }

    public Set<StudentCurse> getStudentCurses() {
        return studentCurses;
    }

    public void setStudentCurses(Set<StudentCurse> studentCurses) {
        this.studentCurses = studentCurses;
    }

    public Set<TeacherCurse> getTeacherCurses() {
        return teacherCurses;
    }

    public void setTeacherCurses(Set<TeacherCurse> teacherCurses) {
        this.teacherCurses = teacherCurses;
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

    public String getYearCurse() {
        return yearCurse;
    }

    public void setYearCurse(String yearCurse) {
        this.yearCurse = yearCurse;
    }

    public Set<CurseSchedule> getCurseSchedules() {
        return curseSchedules;
    }

    public void setCurseSchedules(Set<CurseSchedule> curseSchedules) {
        this.curseSchedules = curseSchedules;
    }


    public void addCurseSchedule(CurseSchedule curseSchedule) {
        curseSchedule.setCurse(this);
        curseSchedules.add(curseSchedule);
    }


    public List<Schedule> getSchedules(){
        return curseSchedules.stream().map(c -> c.getSchedule()).collect(Collectors.toList());
    }

    public void addStudentCurse(StudentCurse studentCurse) {
        studentCurse.setCurse(this);
        studentCurses.add(studentCurse);
    }

    public List<Student> getStudents(){
        return studentCurses.stream().map(c -> c.getStudent()).collect(Collectors.toList());
    }

    public void addTeacherCurse(TeacherCurse teacherCurse) {
        teacherCurse.setCurse(this);
        teacherCurses.add(teacherCurse);
    }

    public List<Teacher> getTeachers(){
        return teacherCurses.stream().map(c -> c.getTeacher()).collect(Collectors.toList());
    }




}
