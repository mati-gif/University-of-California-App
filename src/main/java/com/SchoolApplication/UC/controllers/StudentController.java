package com.SchoolApplication.UC.controllers;

import com.SchoolApplication.UC.dtos.StudentCourseDto;
import com.SchoolApplication.UC.dtos.StudentDto;
import com.SchoolApplication.UC.models.*;
import com.SchoolApplication.UC.repositories.CourseRepository;
import com.SchoolApplication.UC.repositories.EnrollmentRepository;
import com.SchoolApplication.UC.repositories.StudentCourseRepository;
import com.SchoolApplication.UC.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
     StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentCourseRepository studentCourseRepository;

    @Autowired
    EnrollmentRepository enrollmentRepository;
    @GetMapping("/hello")
    public String hello() {
        return "hello esto funciona";
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllStudents() {

        List<Student>allStudents = studentRepository.findAll();
        System.out.println(allStudents);
        List<StudentDto> allStudentsDto = allStudents.stream()
                .map(StudentDto::new) // Convierte cada Student en un StudentDto
                .collect(Collectors.toList());

        return new ResponseEntity<>(allStudentsDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {

        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
        }
        StudentDto studentDto = new StudentDto(student);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PutMapping("/confirmCourse/{id}")
    public ResponseEntity<?> confirmCourse(@PathVariable Long id, Authentication authentication) {
        Student student = studentRepository.findByEmail(authentication.getName());

        try{
            // Validar que el curso existe
            Course course = courseRepository.findById(id).orElseThrow(() ->
                    new RuntimeException("Course not found"));

            // Validar si el estudiante ya está registrado en ese curso
            boolean alreadyRegistered = student.getStudentCourses()
                    .stream()
                    .anyMatch(sc -> sc.getCourse().getId() == course.getId());

            if (alreadyRegistered) {
                return new ResponseEntity<>("Student already registered for this course", HttpStatus.BAD_REQUEST);
            }
              String status = "Active";
            // Crear una nueva relación entre el estudiante y el curso
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setStudent(student);
            studentCourse.setCourse(course);
            studentCourse.setRegistrationDate(LocalDateTime.now());
            studentCourse.setStatus(Status.APPROVED); // Estado inicial
            studentCourse.setMonthlyAttendancePercentage(0.0);

            // Guardar el StudentCourse primero para obtener su ID
            studentCourseRepository.save(studentCourse);

            // Crear y configurar el nuevo Enrollment
            Enrollment enrollment = new Enrollment();
            enrollment.setStudentCourse(studentCourse); // Relacionar con el StudentCourse
            enrollment.setStatusEnrollment(status); // Asignar el estado "Active"

            // Validar si el curso tiene un horario
            if (course.getCourseSchedules() != null && !course.getCourseSchedules().isEmpty()) {
                CourseSchedule selectedSchedule = course.getCourseSchedules().iterator().next(); // Selecciona el primer horario
                enrollment.setCourseSchedule(selectedSchedule); // Asignar el horario del curso
            } else {
                // Lanza una excepción o registra un mensaje
                throw new RuntimeException("Course does not have a schedule assigned");
            }
            // Establecer la relación bidireccional
            studentCourse.getEnrollments().add(enrollment);

            // Guardar el Enrollment
            enrollmentRepository.save(enrollment);

            // Convertir a DTO para la respuesta
            StudentCourseDto studentCourseDto = new StudentCourseDto(studentCourse);

            return new ResponseEntity<>(studentCourseDto, HttpStatus.OK);

           // return new ResponseEntity<>("Course successfully added to student's list", HttpStatus.OK);
        }
        catch (Exception e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }
}
