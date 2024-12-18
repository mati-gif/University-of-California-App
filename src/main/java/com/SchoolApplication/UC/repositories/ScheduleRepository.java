package com.SchoolApplication.UC.repositories;

import com.SchoolApplication.UC.models.Schedule;
import com.SchoolApplication.UC.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Schedule findScheduleById(Long id);
}
