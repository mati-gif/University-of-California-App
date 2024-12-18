package com.SchoolApplication.UC.controllers;

import com.SchoolApplication.UC.dtos.CourseDto;
import com.SchoolApplication.UC.dtos.CourseDtoForCourseController;
import com.SchoolApplication.UC.dtos.CourseScheduleDto;
import com.SchoolApplication.UC.dtos.CreateCourseDto;
import com.SchoolApplication.UC.models.Course;
import com.SchoolApplication.UC.models.CourseSchedule;
import com.SchoolApplication.UC.models.Schedule;
import com.SchoolApplication.UC.models.Student;
import com.SchoolApplication.UC.repositories.CourseRepository;
import com.SchoolApplication.UC.repositories.CourseScheduleRepository;
import com.SchoolApplication.UC.repositories.ScheduleRepository;
import com.SchoolApplication.UC.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;


    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseScheduleRepository courseScheduleRepository;


    @Autowired
    ScheduleRepository scheduleRepository;

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

        List<CourseDtoForCourseController> allCoursesDtoForCourseController = allCourse.stream()
                .map(CourseDtoForCourseController::new)
                .collect(Collectors.toList());

        System.out.println("Datos de los cursos DTO generados: " + allCoursesDtoForCourseController);


        if (allCoursesDtoForCourseController == null || allCoursesDtoForCourseController.isEmpty()) {

            return new ResponseEntity<>("CoursesDTO not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(allCoursesDtoForCourseController, HttpStatus.OK);
    }

    @Transactional
     @PostMapping("/create")
        public ResponseEntity<?> createCourse(Authentication authentication,
                                              @RequestBody CreateCourseDto createCourseDto) {


         try{

             if(createCourseDto.nameSubject().isEmpty() || createCourseDto.nameSubject() == null) {
                 return new ResponseEntity<>("Course name is required", HttpStatus.BAD_REQUEST);

             }
             if (createCourseDto.yearCourse().isEmpty() || createCourseDto.yearCourse() == null) {
                 return new ResponseEntity<>("Course year is required", HttpStatus.BAD_REQUEST);
             }

             if (createCourseDto.maxCapacity() <= 0 || createCourseDto.maxCapacity() == null) {
                 return new ResponseEntity<>("Course max capacity is required and must be greater than 0", HttpStatus.BAD_REQUEST);
             }

             // Validar el horario y el dia de la semana
             if (createCourseDto.dayOfWeek() == null || createCourseDto.dayOfWeek().isEmpty()) {
                 return new ResponseEntity<>("Day of week is required", HttpStatus.BAD_REQUEST);
             }

             if (createCourseDto.time() == null || createCourseDto.time().isEmpty()) {
                 return new ResponseEntity<>("Time is required", HttpStatus.BAD_REQUEST);
             }
             if (createCourseDto.scheduleId() == null) {
                 return new ResponseEntity<>("Schedule ID is required", HttpStatus.BAD_REQUEST);
             }
             Student student = studentRepository.findByEmail(authentication.getName());
             Schedule schedule = scheduleRepository.findScheduleById(createCourseDto.scheduleId());



             if (schedule == null) {
                 return new ResponseEntity<>("Schedule not found", HttpStatus.NOT_FOUND);
             }

             int countQuantityCourses = courseRepository.countByNameSubject(
                     createCourseDto.nameSubject().toLowerCase());

             if (countQuantityCourses == 1 ){
                 return new ResponseEntity<>("Course already exists with nameSubject ->" + " "
                         + createCourseDto.nameSubject() + " "
                         + "<- please create another with diferent nameSubject", HttpStatus.BAD_REQUEST);
             }

             Course course = new Course();
             course.setNameSubject(createCourseDto.nameSubject().toLowerCase());
             course.setYearCourse(createCourseDto.yearCourse());
             course.setMaxCapacity(createCourseDto.maxCapacity());
             courseRepository.save(course);


             CourseSchedule courseSchedule = new CourseSchedule();
             courseSchedule.setDayOfWeek(createCourseDto.dayOfWeek());
             courseSchedule.setTime(createCourseDto.time());
//             courseSchedule.setCourse(course);
             courseSchedule.setSchedule(schedule);
             schedule.addCourseSchedule(courseSchedule);
             course.addCourseSchedule(courseSchedule);

             courseScheduleRepository.save(courseSchedule);
             courseRepository.save(course);
//             scheduleRepository.save(schedule);

//             return new ResponseEntity<>("Course created successfully", HttpStatus.CREATED);
             CourseDtoForCourseController courseDtoForCourseController = new CourseDtoForCourseController(course);

             return new ResponseEntity<>(courseDtoForCourseController, HttpStatus.CREATED);

         }catch (Exception e){


             return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
         }



        }



}
