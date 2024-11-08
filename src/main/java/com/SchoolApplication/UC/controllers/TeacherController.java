package com.SchoolApplication.UC.controllers;

import com.SchoolApplication.UC.dtos.TeacherCourseDto;
import com.SchoolApplication.UC.dtos.TeacherDto;
import com.SchoolApplication.UC.models.Teacher;
import com.SchoolApplication.UC.models.TeacherCourse;
import com.SchoolApplication.UC.repositories.TeacherCourseRepository;
import com.SchoolApplication.UC.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    TeacherRepository teacherRepository;



    @GetMapping("/")
    public ResponseEntity<?> getAllTeachers() {
        List<Teacher> allTeachers = teacherRepository.findAll();

        if (allTeachers.isEmpty() || allTeachers == null) {
            return new ResponseEntity<>("Teachers not found", HttpStatus.NOT_FOUND);
        }

        List<TeacherDto> teachers = allTeachers.stream()
                .map(TeacherDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if (teacher == null) {
            return new ResponseEntity<>("Teacher not found", HttpStatus.NOT_FOUND);
        }
        TeacherDto teacherDto = new TeacherDto(teacher);
        return new ResponseEntity<>(teacherDto, HttpStatus.OK);
    }
}
