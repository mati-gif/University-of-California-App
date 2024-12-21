package com.SchoolApplication.UC.controllers;


import com.SchoolApplication.UC.dtos.CreateNewSchedule;
import com.SchoolApplication.UC.dtos.ScheduleDto;
import com.SchoolApplication.UC.models.Schedule;
import com.SchoolApplication.UC.models.Student;
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
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    StudentRepository studentRepository;


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

    @Transactional
    @PutMapping("/createNewSchedule")
    public ResponseEntity<?> createNewSchedule(Authentication authentication
            , @RequestBody CreateNewSchedule createNewSchedule) {
        Student student = studentRepository.findByEmail(authentication.getName());

        Schedule schedule = scheduleRepository.findScheduleById(createNewSchedule.scheduleId());


        // Agregar los nuevos días si no existen
        for (String day : createNewSchedule.days()) {
            if(schedule.getDays().size() >= 3 && schedule.getTimes().size() >= 3){
                return new ResponseEntity<>("The maximum number of days is 3.", HttpStatus.BAD_REQUEST);
            }
            if (!schedule.getDays().contains(day)) {
                schedule.getDays().add(day);
            } else {
                return new ResponseEntity<>("Day '" + day + "' already exists.", HttpStatus.BAD_REQUEST);
            }
        }

        // Agregar los nuevos horarios si no existen
        for (String time : createNewSchedule.times()) {
            if(schedule.getTimes().size() >= 3 && schedule.getDays().size() >= 3){
                return new ResponseEntity<>("The maximum number of times is 3.", HttpStatus.BAD_REQUEST);
            }
            if (!schedule.getTimes().contains(time)) {
                schedule.getTimes().add(time);
            } else {
                return new ResponseEntity<>("Time '" + time + "' already exists.", HttpStatus.BAD_REQUEST);
            }
        }

// Guardar el schedule actualizado en la base de datos
        scheduleRepository.save(schedule);

    return new ResponseEntity<>("Days and  times add successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllSchedule/{id}")
    public ResponseEntity<?> deleteSchedule(Authentication authentication
            , @PathVariable Long id) {
        Student student = studentRepository.findByEmail(authentication.getName());

        if(scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
            return new ResponseEntity<>("Schedule deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Schedule not found", HttpStatus.NOT_FOUND);
        }

    }

}
