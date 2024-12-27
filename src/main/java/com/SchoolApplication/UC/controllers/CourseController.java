package com.SchoolApplication.UC.controllers;

import com.SchoolApplication.UC.dtos.*;
import com.SchoolApplication.UC.models.*;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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


             Student student = studentRepository.findByEmail(authentication.getName());
             //Schedule schedule = scheduleRepository.findScheduleById(createCourseDto.scheduleId());



//             if (schedule == null) {
//                 return new ResponseEntity<>("Schedule not found", HttpStatus.NOT_FOUND);
//             }

             int countQuantityCourses = courseRepository.countByNameSubject(
                     createCourseDto.nameSubject().toLowerCase());

             if (countQuantityCourses == 1 ){
                 return new ResponseEntity<>("Course already exists with nameSubject ->" + " "
                         + createCourseDto.nameSubject() + " "
                         + "<- please create another with diferent nameSubject", HttpStatus.BAD_REQUEST);
             }

             Set<String> uniqueSchedules = new HashSet<>();
             for(CourseScheduleDto courseScheduleDto : createCourseDto.schedules()){
                 String uniqueKey = courseScheduleDto.getScheduleId() + "|"
                         + courseScheduleDto.getDayOfWeek() + "|"
                         + courseScheduleDto.getTime() + "|"
                         + courseScheduleDto.getShift();

                 if (!uniqueSchedules.add(uniqueKey)) {
                     return new ResponseEntity<>(
                             "Duplicate CourseSchedule found with scheduleId: "
                                     + courseScheduleDto.getScheduleId()
                                     + ", dayOfWeek: " + courseScheduleDto.getDayOfWeek()
                                     + ", time: " + courseScheduleDto.getTime()
                                     + ", shift: " + courseScheduleDto.getShift(),
                             HttpStatus.BAD_REQUEST
                     );
                 }

                 if (courseScheduleDto.getDayOfWeek() == null || courseScheduleDto.getDayOfWeek().isEmpty()) {
                     return new ResponseEntity<>("Day of week is required", HttpStatus.BAD_REQUEST);
                 }

                 if (courseScheduleDto.getTime() == null || courseScheduleDto.getTime().isEmpty()) {
                     return new ResponseEntity<>("Time is required", HttpStatus.BAD_REQUEST);
                 }
                 if (courseScheduleDto.getScheduleId() < 0 ) {
                     return new ResponseEntity<>("Schedule ID is required", HttpStatus.BAD_REQUEST);
                 }
                 if(courseScheduleDto.getShift() == null ) {
                     return new ResponseEntity<>("Shift is required", HttpStatus.BAD_REQUEST);
                 }

                 Schedule schedule = scheduleRepository.findScheduleById(courseScheduleDto.getScheduleId());

                 if(schedule.getCourseSchedules().size() >= 3 ) {
                     return new ResponseEntity<>("Can not create more than 3 schedule for course with nameSubject : " + createCourseDto.nameSubject(), HttpStatus.NOT_FOUND);
                 }
                 if (schedule == null) {
                     return new ResponseEntity<>("Schedule not found for ID: " + courseScheduleDto.getScheduleId(), HttpStatus.NOT_FOUND);
                 }
                 if (courseScheduleDto.getScheduleId() != schedule.getId()) {
                     return new ResponseEntity<>("Schedule ID does not match ,try with a valid Schedule ID", HttpStatus.BAD_REQUEST);
                 }
                 if(!courseScheduleDto.getShift().equals(schedule.getShift())) {
                     return new ResponseEntity<>("Shift does not match with any shift,try with a valid Shift", HttpStatus.BAD_REQUEST);
                 }
                 // Validar que el día de la semana coincida con alguno de los días en la lista
                 boolean dayOfWeekExists = schedule.getDays().stream()
                         .anyMatch(day -> day.equals(courseScheduleDto.getDayOfWeek()));
                 if(!dayOfWeekExists) {
                     return new ResponseEntity<>("Day of week does not exits in the days list schedule,try with a valid Days of week", HttpStatus.BAD_REQUEST);
                 }
                 // Validar que el horario coincida con alguno de los horarios en la lista
                 boolean timeExists = schedule.getTimes().stream()
                         .anyMatch(time -> time.equals(courseScheduleDto.getTime()));
                 if(!timeExists) {
                     return new ResponseEntity<>("Time does not exits in the times list schedule,try with a valid Time", HttpStatus.BAD_REQUEST);
                 }
             }

             Course course = new Course();
             course.setNameSubject(createCourseDto.nameSubject().toLowerCase());
             course.setYearCourse(createCourseDto.yearCourse());
             course.setMaxCapacity(createCourseDto.maxCapacity());
             courseRepository.save(course);

             // Iterar sobre la lista de horarios
             for (CourseScheduleDto courseScheduleDto : createCourseDto.schedules()) {

                 Schedule schedule = scheduleRepository.findScheduleById(courseScheduleDto.getScheduleId());


                 CourseSchedule courseSchedule = new CourseSchedule();
                 courseSchedule.setDayOfWeek(courseScheduleDto.getDayOfWeek());
                 courseSchedule.setTime(courseScheduleDto.getTime());
                 courseSchedule.setSchedule(schedule);

                 schedule.addCourseSchedule(courseSchedule);
                 course.addCourseSchedule(courseSchedule);

                 courseScheduleRepository.save(courseSchedule);
             }
                 courseRepository.save(course);
//             scheduleRepository.save(schedule);

//             return new ResponseEntity<>("Course created successfully", HttpStatus.CREATED);
             CourseDtoForCourseController courseDtoForCourseController = new CourseDtoForCourseController(course);

             return new ResponseEntity<>(courseDtoForCourseController, HttpStatus.CREATED);

         }catch (Exception e){


             return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
         }



        }


        @GetMapping("/coursesAvailableStudent")
        public ResponseEntity<?> getCoursesAvailableStudent(Authentication authentication) {
            Student student = studentRepository.findByEmail(authentication.getName());
            Set<StudentCourse> studentCourse = student.getStudentCourses();
            System.out.println(studentCourse);

            List<StudentCourseDto> studentCourseDto = studentCourse.stream().map(sc ->
                    new StudentCourseDto(sc)).collect(Collectors.toList());

            System.out.println(studentCourseDto);
        return new ResponseEntity<>(studentCourseDto, HttpStatus.OK);
    }




}
