package com.SchoolApplication.UC.utils;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsServicesImplementMethod {


    // Método auxiliar para determinar si un usuario es admin
    public static boolean isAdmin(String email) {
        // Aquí se puede hacer la verificación de si el correo es de un admin, o usar una propiedad
        return email.toLowerCase().contains("admin");
    }

    // Método auxiliar para construir el UserDetails
    public static UserDetails buildUserDetails(String email, String password, String role) {
        return User
                .withUsername(email)
                .password(password)
                .roles(role)
                .build();
    }
}
