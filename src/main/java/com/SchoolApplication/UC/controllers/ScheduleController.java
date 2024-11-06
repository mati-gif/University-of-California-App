package com.SchoolApplication.UC.controllers;


import com.SchoolApplication.UC.dtos.ScheduleDto;
import com.SchoolApplication.UC.models.Schedule;
import com.SchoolApplication.UC.repositories.ScheduleRepository;
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
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    ScheduleRepository scheduleRepository;


    @Transactional
    @GetMapping("/")
    public ResponseEntity<?> getAllSchedule(){

        List<Schedule>allSchedule = scheduleRepository.findAll();

        if(allSchedule.isEmpty() || allSchedule == null) {
            return new ResponseEntity<>("Schedule not found", HttpStatus.NOT_FOUND);
        }

        List<ScheduleDto> allScheduleDto = allSchedule.stream()
                .map(ScheduleDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(allScheduleDto, HttpStatus.OK);
    }

}
