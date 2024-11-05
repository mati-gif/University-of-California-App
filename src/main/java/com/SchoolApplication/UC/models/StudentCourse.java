package com.SchoolApplication.UC.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime registrationDate;
    private Status status;
    private double monthlyAttendancePercentage;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "studentCourse", fetch = FetchType.EAGER)
    private Set<Attendance> attendances = new HashSet<>();

    public StudentCourse(LocalDateTime registrationDate, Status status) {
        this.registrationDate = registrationDate;
        this.status = status;

    }

    public StudentCourse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }


    public double getMonthlyAttendancePercentage() {
        return monthlyAttendancePercentage;
    }

    public void setMonthlyAttendancePercentage(double monthlyAttendancePercentage) {
        this.monthlyAttendancePercentage = monthlyAttendancePercentage;
    }


    //Reveer la logica para añadir una  asistencia , No esta funcionando bien .
    public void addAttendance(Attendance attendance) {
        attendance.setStudentCourse(this);
        attendances.add(attendance);
        updateAttendancePercentage(); // Actualiza el porcentaje de asistencia acumulado
    }

    private void updateAttendancePercentage() {
        // Filtrar por mes actual
        long diasAsistidos = attendances.stream()
                .filter(att -> att.getDate().getMonth() == LocalDateTime.now().getMonth() &&
                        att.getStatusAttendance() == StatusAttendance.PRESENT)
                .count();

        long diasTotales = attendances.stream()
                .filter(att -> att.getDate().getMonth() == LocalDateTime.now().getMonth())
                .count();

        // Calcular el porcentaje
        this.monthlyAttendancePercentage = diasTotales > 0 ? (diasAsistidos * 100.0) / diasTotales : 0.0;
    }

}
