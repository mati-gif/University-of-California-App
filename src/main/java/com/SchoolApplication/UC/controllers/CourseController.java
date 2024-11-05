package com.SchoolApplication.UC.controllers;

import com.SchoolApplication.UC.dtos.CourseDto;
import com.SchoolApplication.UC.models.Course;
import com.SchoolApplication.UC.repositories.CourseRepository;
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

    @GetMapping("/")
    public ResponseEntity<?> getAllCourses() {

        List<Course> allCurs = courseRepository.findAll();
        if(allCurs == null || allCurs.isEmpty()) {
            return new ResponseEntity<>("Courses not found", HttpStatus.NOT_FOUND);

        }


        System.out.println(allCurs);
        List<CourseDto> allCoursesDto = allCurs.stream()
                .map(CourseDto::new)
                .collect(Collectors.toList());

        if (allCoursesDto == null || allCoursesDto.isEmpty()) {

            return new ResponseEntity<>("CoursesDTO not found", HttpStatus.NOT_FOUND);
        }
        System.out.println(allCoursesDto);
        return new ResponseEntity<>(allCoursesDto, HttpStatus.OK);
    }

}
