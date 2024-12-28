package com.SchoolApplication.UC.controllers;

import com.SchoolApplication.UC.dtos.AttendanceDto;
import com.SchoolApplication.UC.models.Attendance;
import com.SchoolApplication.UC.models.Student;
import com.SchoolApplication.UC.models.StudentCourse;
import com.SchoolApplication.UC.repositories.AttendanceRepository;
import com.SchoolApplication.UC.repositories.StudentCourseRepository;
import com.SchoolApplication.UC.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentCourseRepository studentCourseRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAttendance() {
        List<Attendance> attendances = attendanceRepository.findAll();

        if (attendances.isEmpty() || attendances == null) {
            return new ResponseEntity("Attendances not found", HttpStatus.NOT_FOUND);
        }

        List<AttendanceDto> attendanceDtos = attendances
                .stream()
                .map(AttendanceDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity(attendanceDtos, HttpStatus.OK);
    }

    @GetMapping("/availableAttendancesOfStudent")
    public ResponseEntity<?> getAvailableAttendancesOfStudent(Authentication authentication) {
        // Obtener al estudiante autenticado
        Student student = studentRepository.findByEmail(authentication.getName());

        // Obtener las asistencias relacionadas con el estudiante a través de los StudentCourses
        List<Attendance> attendances = student.getStudentCourses().stream()
                .flatMap(sc -> sc.getAttendances().stream()) // Obtenemos las asistencias de cada StudentCourse
                .collect(Collectors.toList());

        // Convertir las asistencias a AttendanceDto
        List<AttendanceDto> attendanceDtos = attendances.stream()
                .map(AttendanceDto::new) // Convertimos cada Attendance a su DTO
                .collect(Collectors.toList());

        // Retornar la lista de AttendanceDto como respuesta
        return new ResponseEntity<>(attendanceDtos, HttpStatus.OK);
    }

    @PostMapping("/createNewAttendance/{studentCourseId}")
    public ResponseEntity<?> createAttendance(@PathVariable Long studentCourseId, @RequestBody AttendanceDto attendanceDto) {
        try {
            // Validar que el StudentCourse existe
            StudentCourse studentCourse = studentCourseRepository.findById(studentCourseId)
                    .orElseThrow(() -> new RuntimeException("StudentCourse not found"));

            // Crear una nueva asistencia
            Attendance attendance = new Attendance();
            attendance.setDate(attendanceDto.getDate());
            attendance.setStatusAttendance(attendanceDto.getStatusAttendance());
            studentCourse.addAttendance(attendance);

            // Guardar asistencia y actualizar el porcentaje
            attendanceRepository.save(attendance);
            studentCourseRepository.save(studentCourse);

            return new ResponseEntity<>(new AttendanceDto(attendance), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
