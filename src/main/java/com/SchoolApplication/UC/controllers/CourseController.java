package com.SchoolApplication.UC.controllers;

import com.SchoolApplication.UC.dtos.CourseDto;
import com.SchoolApplication.UC.models.Course;
import com.SchoolApplication.UC.repositories.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @RequestMapping("/hello")
    public String hello() {
        return "hello esto funciona!!!!!!!!!!!!!!";
    }

    @Transactional
    @GetMapping("/")
    public ResponseEntity<?> getAllCourses() {

        List<Course> allCourse = courseRepository.findAll();
        System.out.println(allCourse + " " + "aca estan los cursos");


        allCourse.forEach(course -> {
            System.out.println("Curso ID: " + course.getId());
            System.out.println("Nombre de la materia: " + course.getNameSubject());
            System.out.println("Año del curso: " + course.getYearCourse());
            System.out.println("Capacidad máxima: " + course.getMaxCapacity());

            if (course.getCourseSchedules().isEmpty()) {
                System.out.println(" - No tiene horarios asignados.");
            } else {
                course.getCourseSchedules().forEach(courseSchedule -> {
                    System.out.println(" - Día de la semana: " + courseSchedule.getDayOfWeek());
                    System.out.println(" - Hora: " + courseSchedule.getTime());
                    if (courseSchedule.getSchedule() != null) {
                        System.out.println(" - Schedule ID: " + courseSchedule.getSchedule().getId());
                        System.out.println(" - Turno: " + courseSchedule.getSchedule().getShift());
                    } else {
                        System.out.println(" - Schedule no encontrado.");
                    }
                });
            }
        });




        if(allCourse == null || allCourse.isEmpty()) {
            return new ResponseEntity<>("Courses not found", HttpStatus.NOT_FOUND);

        }


        System.out.println(allCourse + " " + "aca tambien deberian estar los cursos");
        List<CourseDto> allCoursesDto = allCourse.stream()
                .map(CourseDto::new)
                .collect(Collectors.toList());

        System.out.println("Datos de los cursos DTO generados: " + allCoursesDto);


        if (allCoursesDto == null || allCoursesDto.isEmpty()) {

            return new ResponseEntity<>("CoursesDTO not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(allCoursesDto, HttpStatus.OK);
    }

}
