package com.SchoolApplication.UC.repositories;

import com.SchoolApplication.UC.models.StudentCurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCurseRepository extends JpaRepository<StudentCurse, Long> {
}
