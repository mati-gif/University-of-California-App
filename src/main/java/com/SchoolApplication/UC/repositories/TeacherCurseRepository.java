package com.SchoolApplication.UC.repositories;

import com.SchoolApplication.UC.models.TeacherCurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherCurseRepository extends JpaRepository<TeacherCurse, Long> {
}
