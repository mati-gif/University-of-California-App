package com.SchoolApplication.UC.repositories;

import com.SchoolApplication.UC.models.CurseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurseScheduleRepository extends JpaRepository<CurseSchedule, Long> {
}
