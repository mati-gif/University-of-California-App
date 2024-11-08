package com.SchoolApplication.UC.dtos;

import com.SchoolApplication.UC.models.Course;

import java.util.Set;
import java.util.stream.Collectors;

public class CourseDtoForTeacher {
    private long id;

    private String nameSubject;
    private String yearCourse;
    private int maxCapacity;
    private Set<StudentDtoForTeacher> students;  // Agregar lista de estudiantes


    public CourseDtoForTeacher(Course course) {
        this.id = course.getId();
        this.nameSubject = course.getNameSubject();
        this.yearCourse = course.getYearCourse();
        this.maxCapacity = course.getMaxCapacity();

        // Convertir `StudentCourse` a `StudentDto` para obtener estudiantes inscritos
        this.students = course.getStudentCourses().stream()
                .map(studentCourse -> new StudentDtoForTeacher(studentCourse.getStudent()))
                .collect(Collectors.toSet());




    }



    public long getId() {
        return id;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public String getYearCourse() {
        return yearCourse;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public Set<StudentDtoForTeacher> getStudents() {
        return students;
    }


}
