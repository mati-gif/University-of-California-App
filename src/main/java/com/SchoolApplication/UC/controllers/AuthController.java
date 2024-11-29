package com.SchoolApplication.UC.controllers;

import com.SchoolApplication.UC.dtos.LoginDto;
import com.SchoolApplication.UC.dtos.RegisterDto;
import com.SchoolApplication.UC.dtos.StudentDto;
import com.SchoolApplication.UC.models.Student;
import com.SchoolApplication.UC.repositories.StudentRepository;
import com.SchoolApplication.UC.serviceSecurity.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuthenticationManager authenticationManager;//para manejar la autenticacion cuando se haga el login.

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){//El método login está anotado con @RequestBody, lo que significa que espera un cuerpo de la solicitud en formato JSON que será mapeado a un objeto de tipo LoginDto.
        try{


            //Aquí, estás usando authenticationManager para autenticar al usuario.
            // Este método verifica las credenciales del usuario:
            //UsernamePasswordAuthenticationToken: Este objeto encapsula las credenciales del usuario, en este caso,
            // el email y la contraseña.
            //authenticationManager.authenticate(...): Este método intenta autenticar al usuario usando las credenciales proporcionadas.
            // Internamente, authenticationManager delega la autenticación a un AuthenticationProvider que utiliza un UserDetailsService para cargar el usuario y un PasswordEncoder para verificar si la contraseña es correcta.




            //Estamos accediendo al metodo authenticate que lo encontramos en la interfaz de authenticationManager.
            //este metodo usa por detras un authentication provider que genera un userDetails usando el userDetailsService ,
            // tambien va a usar un password encoder,para comparar si el password que nosotros le pasamos es igual al password del cliente que nosotors tenemos en nuestra base de datos.
            //Si no es asi va a entrar por el catch .
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.email(),loginDto.password()));








            //userDetailsService carga el UserDetails (que contiene la información del usuario) desde la base de datos utilizando el email del usuario


            //luego creamos el userDetails en memoria para despues generar el token con las credenciales de usuario
            // ya que el metodo generateToekn recibe un userDetails
            final UserDetails userDetails =  userDetailsService.loadUserByUsername(loginDto.email());



            //jwtUtilService.generateToken(userDetails): Este método genera un token JWT basado en los detalles del usuario autenticado. Este token será usado por el cliente para autenticar futuras solicitudes.
            final String jwt = jwtUtilService.generateToken(userDetails);



            //Si esto sale todo bien se va a loguear el cliente y se va a mandar el token.
            return ResponseEntity.ok(jwt);
        } catch (Exception e){
            return new ResponseEntity<>("Email o Password invalid" , HttpStatus.BAD_REQUEST);
        }


    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){

        if(registerDto.firstName().isBlank() ){
            return new ResponseEntity<>("the name field must not be empty",HttpStatus.FORBIDDEN);
        }
        if (registerDto.lastName().isBlank()){
            return new ResponseEntity<>("the last name field must not be empty",HttpStatus.FORBIDDEN);
        }
        if (registerDto.email().isBlank()){
            return new ResponseEntity<>("the email field must not be empty",HttpStatus.FORBIDDEN);
        }
        if(registerDto.password().isBlank()){
            return new ResponseEntity<>("the password field must not be empty",HttpStatus.FORBIDDEN);
        }

        // Verificar si el correo electrónico ya está registrado
        if (studentRepository.findByEmail(registerDto.email()) != null) {
            return new ResponseEntity<>("Email is already in use", HttpStatus.CONFLICT);
        }

        Student student = new Student(
                registerDto.firstName(),
                registerDto.lastName(),
                registerDto.email(),
                passwordEncoder.encode(registerDto.password()));

        studentRepository.save(student);

        return new ResponseEntity<>("Client created",HttpStatus.CREATED);
    }


    @GetMapping("/current")//metodo para obtener el cliente autenticado (es decir que ya esta logueado)
//este metodo recibe como parametro un Authentication que es el cliente autenticado, es decir el que se acaba de loguear.
    public ResponseEntity<?> getClient(Authentication authentication){
//el parámetro Authentication authentication se utiliza para obtener información sobre
// el usuario que ha iniciado sesión.
// Este objeto es proporcionado automáticamente por Spring Security si el usuario está autenticado.


        //vamos a acceder al metodo findByEmail() que esta en clientRepository  para buscar ese cliente por su email (en la base de datos)
        // y del authentication (que es el usuario logueado es decir autenticado) vamos a obtener el nombre , ese nombre es el email.
        // Una vez que lo obtenemos vamos a guardarlo en una viariable client de tipo Client
        Student student = studentRepository.findByEmail(authentication.getName());
        //despues va a retornar un ResponseEntity con el client y el status OK
        return new ResponseEntity<>( new StudentDto(student),HttpStatus.OK);



        // se asegura que solo los usuarios autenticados puedan acceder a este endpoint.
        // Si alguien no está autenticado y trata de acceder a /api/auth/current,
        // recibiría una respuesta con un error de autenticación (por ejemplo, 401 Unauthorized).

    }
}
