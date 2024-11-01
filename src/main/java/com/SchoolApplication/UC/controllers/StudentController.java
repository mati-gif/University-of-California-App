package com.SchoolApplication.UC.controllers;

import com.SchoolApplication.UC.dtos.StudentDto;
import com.SchoolApplication.UC.models.Curse;
import com.SchoolApplication.UC.models.Student;
import com.SchoolApplication.UC.models.StudentCurse;
import com.SchoolApplication.UC.repositories.CurseRepository;
import com.SchoolApplication.UC.repositories.StudentCurseRepository;
import com.SchoolApplication.UC.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
     StudentRepository studentRepository;




    @GetMapping("/hello")
    public String hello() {
        return "hello esto funciona";
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllStudents() {

        List<Student>allStudents = studentRepository.findAll();

        List<StudentDto> allStudentsDto = allStudents.stream()
                .map(StudentDto::new) // Convierte cada Student en un StudentDto
                .collect(Collectors.toList());

        return new ResponseEntity<>(allStudentsDto, HttpStatus.OK);
    }
}
