package com.SchoolApplication.UC.repositories;

import com.SchoolApplication.UC.models.Usuarioo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariooRepository extends JpaRepository<Usuarioo, Long> {
}
