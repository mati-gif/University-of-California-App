package com.SchoolApplication.UC.repositories;

import com.SchoolApplication.UC.models.Curse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurseRepository extends JpaRepository<Curse, Long> {
}
