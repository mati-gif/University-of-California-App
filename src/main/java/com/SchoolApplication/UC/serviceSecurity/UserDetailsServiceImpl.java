package com.SchoolApplication.UC.serviceSecurity;


import com.SchoolApplication.UC.models.Student;
import com.SchoolApplication.UC.models.Teacher;
import com.SchoolApplication.UC.repositories.StudentRepository;
import com.SchoolApplication.UC.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.SchoolApplication.UC.utils.UserDetailsServicesImplementMethod.buildUserDetails;
import static com.SchoolApplication.UC.utils.UserDetailsServicesImplementMethod.isAdmin;

@Service//Servicio parar crear una instancia de User en nuestra aplicacion. Marca la clase como un servicio de Spring. Esto significa que Spring va a gestionar la instancia de esta clase y la inyectará donde sea necesario.
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 1. Buscar si es un estudiante
//        Student student = studentRepository.findByEmail(username);
//
//        if (student == null) {
//            throw new UsernameNotFoundException("User not found with email: " + username);
//        }
//
//        if (student.getEmail().contains("admin")) {
//            return User
//                    .withUsername(username)
//                    .password(student.getPassword())
//                    .roles("ADMIN")
//                    .build();
//        }
//
//        return
//                User
//                        .withUsername(username)
//                        .password(student.getPassword())
//                        .roles("STUDENT")
//                        .build();

        //opcion 2:
//        Student student = studentRepository.findByEmail(username);
//        if (student != null) {
//            // Verificar si el email contiene "admin"
//            if (student.getEmail().contains("admin")) {
//                return User
//                        .withUsername(username)
//                        .password(student.getPassword())
//                        .roles("ADMIN")
//                        .build();
//            }
//
//            // Si no contiene "admin", asignar el rol de STUDENT
//            return User
//                    .withUsername(username)
//                    .password(student.getPassword())
//                    .roles("STUDENT")
//                    .build();
//        }
//
//        // 2. Si no es estudiante, buscar si es un profesor
//        Teacher teacher = teacherRepository.findByEmail(username);
//
//        if (teacher != null) {
//            // Verificar si el email contiene "admin"
//            if (teacher.getEmail().contains("admin")) {
//                return User
//                        .withUsername(username)
//                        .password(teacher.getPassword())
//                        .roles("ADMIN")
//                        .build();
//            }
//
////            // Verificar si el email contiene "teacher"
////            if (teacher.getEmail().contains("teacher")) {
//                return User
//                        .withUsername(username)
//                        .password(teacher.getPassword())
//                        .roles("TEACHER")
//                        .build();
////            }
//
////            // Si no contiene ninguna palabra clave, asignar el rol de STUDENT
////            return User
////                    .withUsername(username)
////                    .password(teacher.getPassword())
////                    .roles("STUDENT")
////                    .build();
//        }
//
//        // 3. Si no es estudiante ni profesor, lanzar una excepción
//        throw new UsernameNotFoundException("User not found with email: " + username);



//opcion 3:
//        Student student = studentRepository.findByEmail(username);
//        if (student != null) {
//            if (isAdmin(student.getEmail())) {
//                return buildUserDetails(student.getEmail(), student.getPassword(), "ADMIN");
//            }
//            return buildUserDetails(student.getEmail(), student.getPassword(), "STUDENT");
//        }
//
//        Teacher teacher = teacherRepository.findByEmail(username);
//        if (teacher != null) {
//            if (isAdmin(teacher.getEmail())) {
//                return buildUserDetails(teacher.getEmail(), teacher.getPassword(), "ADMIN");
//            }
//            return buildUserDetails(teacher.getEmail(), teacher.getPassword(), "TEACHER");
//        }
//
//        throw new UsernameNotFoundException("User not found with email: " + username);


        //opcion 4 :

        // 1. Buscar si es un estudiante
        Student student = studentRepository.findByEmail(username);
        if (student != null) {
            System.out.println("Entro por el primer if " + student);
            if (student.getEmail().contains("admin")) {
                System.out.println("entro por el segundo if" + student.getEmail());

                System.out.println("el rol del usuario es : " + User.withUsername(username).password(student.getPassword()).roles("ADMIN").build());

                return User
                        .withUsername(username)
                        .password(student.getPassword())
                        .roles("ADMIN")
                        .build();

            }
            System.out.println("el rol del usuario es : " + User.withUsername(username).password(student.getPassword()).roles("STUDENT").build());

            return User
                    .withUsername(username)
                    .password(student.getPassword())
                    .roles("STUDENT")
                    .build();

        }
        System.out.println("el rol del usuario es : " + User.withUsername(username).password(student.getPassword()).roles("STUDENT").build());

        // 2. Buscar si es un profesor
        Teacher teacher = teacherRepository.findByEmail(username);
        if (teacher != null) {
            if (teacher.getEmail().contains("teacher")) {
                return User
                        .withUsername(username)
                        .password(teacher.getPassword())
                        .roles("TEACHER")
                        .build();
            }

            // Puedes manejar otros roles aquí si es necesario para profesores sin "teacher" en el email.
        }

        throw new UsernameNotFoundException("User not found with email: " + username);


    }

}
