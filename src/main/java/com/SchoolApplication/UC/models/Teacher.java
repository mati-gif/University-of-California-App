package com.SchoolApplication.UC.models;

import jakarta.persistence.Entity;

@Entity
public class Teacher extends Usuarioo {

    public Teacher(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    // Constructor vacío necesario para JPA
    public Teacher() {
        super(); // Llama al constructor vacío de Usuarioo
    }
}
