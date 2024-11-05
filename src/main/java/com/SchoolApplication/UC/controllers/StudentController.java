package com.SchoolApplication.UC.controllers;

import com.SchoolApplication.UC.dtos.StudentDto;
import com.SchoolApplication.UC.models.Student;
import com.SchoolApplication.UC.repositories.StudentRepository;
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
}
