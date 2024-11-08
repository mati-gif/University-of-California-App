package com.SchoolApplication.UC.controllers;

import com.SchoolApplication.UC.dtos.AttendanceDto;
import com.SchoolApplication.UC.models.Attendance;
import com.SchoolApplication.UC.repositories.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    AttendanceRepository attendanceRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAttendance() {
        List<Attendance> attendances = attendanceRepository.findAll();

        if (attendances.isEmpty() || attendances == null) {
            return new ResponseEntity("Attendances not found", HttpStatus.NOT_FOUND);
        }

        List<AttendanceDto> attendanceDtos = attendances
                .stream()
                .map(AttendanceDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity(attendanceDtos, HttpStatus.OK);
    }
}
