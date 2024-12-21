package com.SchoolApplication.UC.dtos;

import com.SchoolApplication.UC.models.Shift;

import java.util.List;

public record CreateNewSchedule(Long scheduleId, List<String> days , List<String> times , Shift shift)   {
}
