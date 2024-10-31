package com.SchoolApplication.UC.controllers;

import com.SchoolApplication.UC.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
     StudentRepository studentRepository;


    @GetMapping("/hello")
    public String hello() {
        return "hello esto funciona";
    }
}
