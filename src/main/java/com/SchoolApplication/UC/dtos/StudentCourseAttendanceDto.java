package com.SchoolApplication.UC.dtos;

import java.util.List;

public class StudentCourseAttendanceDto {
    private Long studentCourseId; // ID de la inscripción del estudiante en el curso
    private List<AttendanceDto> attendances; // Lista de asistencias con fecha y estado

    // Otros atributos


    public StudentCourseAttendanceDto(Long studentCourseId, List<AttendanceDto> attendances) {
        this.studentCourseId = studentCourseId;
        this.attendances = attendances;
    }

    public Long getStudentCourseId() {
        return studentCourseId;
    }

    public List<AttendanceDto> getAttendances() {
        return attendances;
    }
}
