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
        Student student = studentRepository.findByEmail(username);
        if (student != null) {
            if (isAdmin(student.getEmail())) {
                return buildUserDetails(student.getEmail(), student.getPassword(), "ADMIN");
            }
            return buildUserDetails(student.getEmail(), student.getPassword(), "STUDENT");
        }

        Teacher teacher = teacherRepository.findByEmail(username);
        if (teacher != null) {
            if (isAdmin(teacher.getEmail())) {
                return buildUserDetails(teacher.getEmail(), teacher.getPassword(), "ADMIN");
            }
            return buildUserDetails(teacher.getEmail(), teacher.getPassword(), "TEACHER");
        }

        throw new UsernameNotFoundException("User not found with email: " + username);

    }
}
