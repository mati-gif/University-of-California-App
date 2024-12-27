package com.SchoolApplication.UC.configurations;
import com.SchoolApplication.UC.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;


//Configura la seguridad de la aplicación, incluyendo cómo se manejan las solicitudes.
//Añade el filtro: Usa addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class) para asegurarse de que el filtro JWT se ejecute antes de que cualquier autenticación basada en nombres de usuario y contraseñas (UsernamePasswordAuthenticationFilter) tenga lugar.
//SessionManagement: Configura la política de sesiones como STATELESS, indicando que el servidor no mantiene estado de la sesión entre solicitudes. Esto es típico en aplicaciones que usan JWT, donde la autenticación se maneja completamente a través de los tokens en cada solicitud.



//La configuaracion de CORS se va a implementar aca



//Vamos a hacer otra cadena de filtros.
//Proposito: es definir cómo se autentican y autorizan las solicitudes HTTP en la aplicación,
// configurando un filtro de seguridad basado en JWT,
// deshabilitando mecanismos de autenticación tradicionales,
// y manejando la política de sesión para asegurar la aplicación.
@Configuration//@Configuration: Indica que esta clase es una configuración de Spring.
// Spring utilizará esta clase para configurar componentes y comportamientos específicos relacionados con la seguridad de la aplicación.
public class WebConfig {




    @Autowired//Inyectamos el JwtRequestFilter, Un filtro personalizado que valida los JWT en cada solicitud.
    //@Autowired: Inyecta automáticamente una instancia de JwtRequestFilter. Este filtro intercepta las solicitudes HTTP para extraer y validar el token JWT.
    private JwtRequestFilter jwtRequestFilter;



    @Autowired//Inyectamos el CorsConfigurationSource,Configuración para manejar las solicitudes CORS.
    //corsConfigurationSource: Este objeto configura la política de CORS (Cross-Origin Resource Sharing),
    // que define cómo los recursos de tu servidor pueden ser solicitados desde dominios diferentes al propio.
    private CorsConfigurationSource corsConfigurationSource;



    @Bean
//Con esta anotacino decimos que sea una de las primeras cosas que se ejecuten cuanod arranca la aplicacion y lo ponemos dentro del contexto de Spring.




    //Este metodo va a devolver un SecurityFilterChain y recibe por parametro un
    //HttpSecurity : se va a encargar de crear las configuaraciones por nosotros(es decir Es un objeto que permite configurar la seguridad HTTP)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {//Aquí se configura cómo se aplican las políticas de seguridad en la aplicación.
        // SecurityFilterChain: Define la cadena de filtros de seguridad
        // que Spring Security aplicará a las solicitudes entrantes.
        // Este método configura la seguridad HTTP para tu aplicación.

//Recibe como parametro un objeto HttpSecurity.
//Este objeto es clave en la configuración de la seguridad en aplicaciones Spring, ya que proporciona
// un conjunto de métodos y opciones para personalizar cómo se protegen las rutas y los recursos en la aplicación.
//
//Detalles sobre el parámetro HttpSecurity
//HttpSecurity es una clase proporcionada por Spring Security, y su función principal es permitir la configuración detallada de la seguridad HTTP.
// Con él, puedes definir cómo se gestionan las solicitudes HTTP entrantes, especificar qué rutas están protegidas, cómo se manejan las sesiones, y qué mecanismos de autenticación y autorización se utilizan.









        httpSecurity

                //Propósito General
                //Estas configuraciones desactivan mecanismos de seguridad tradicionales de Spring Security que no son necesarios cuando se usa JWT para manejar la autenticación y autorización en una API RESTful.
                // JWT es un token que se maneja completamente en el lado del cliente y se pasa en cada solicitud HTTP para autenticar al usuario.
                // Por lo tanto, los mecanismos tradicionales de autenticación y protección basados en formularios o sesiones son reemplazados por este enfoque más moderno.





                //httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource))
                //Qué hace:
                //Esta línea habilita y configura CORS (Cross-Origin Resource Sharing) en tu aplicación. Utiliza una configuración de CORS personalizada definida por corsConfigurationSource.
                //
                //Por qué lo hace:
                //CORS es una política de seguridad del navegador que restringe cómo los recursos en una página web pueden ser solicitados desde un dominio diferente al que sirvió la página.
                // En aplicaciones modernas, especialmente en aquellas que tienen un frontend separado del backend (por ejemplo, un frontend React o Angular y un backend Spring), es común que se necesiten solicitudes entre dominios.
                //
                //Por ejemplo, si tu frontend está alojado en https://frontend.com y tu backend en https://api.backend.com, sin una configuración CORS adecuada, el navegador bloquearía las solicitudes del frontend al backend.
                //Configurar CORS permite que el backend acepte solicitudes desde el dominio del frontend, especificando qué métodos HTTP están permitidos, qué encabezados pueden enviarse, y qué orígenes tienen acceso.




                //Aca establecemos la configuracon del CORS que hicimos antes.
                // Esto le dice a Spring Security que deseas habilitar la configuración de CORS para las solicitudes HTTP.
                .cors(cors -> cors.configurationSource(corsConfigurationSource))





                //httpSecurity.csrf(AbstractHttpConfigurer::disable)
                //Qué hace:
                //Esta línea desactiva la protección CSRF (Cross-Site Request Forgery) en la aplicación.
                //
                //Por qué lo hace:
                //CSRF es un tipo de ataque donde un usuario autenticado en un sitio es engañado para enviar solicitudes no deseadas a otro sitio en el que está autenticado.
                // Spring Security habilita la protección CSRF por defecto para proteger contra este tipo de ataque en aplicaciones que manejan sesiones basadas en cookies.
                //
                //Sin embargo, en una aplicación que utiliza JWT para la autenticación, esta protección CSRF no es necesaria, ya que:
                //
                //La autenticación no depende de las cookies o la sesión del navegador.
                //El token JWT se envía explícitamente en cada solicitud (normalmente en el encabezado Authorization), y el servidor valida este token para cada solicitud.
                //Por esta razón, desactivar CSRF simplifica la configuración de seguridad sin comprometer la seguridad en el contexto de una aplicación JWT.




                //.csrf : Cross site request forgery. Es una vulnearibildad que tienen las aplicaciones web.
                //¿Como funciona? => El usuario diligencia el formulario en el  navegador y envia una peticion al servidor.
                //El cors inteercepta esa comunicacion que esta tratando de hacer el el usuario ,con el formulario, al navegador y eso genera la vulnerabilidad por eso hay que desabilitarlo

                // Lo vamos a desabilitar porque es una configuracion que se hace para impedir ataques de formularios.
                //ataques de formularios: es cuando alguien hace un formulario similar al que tenemos en nuestra aplicacion y
                // nos "pega" a nuestra aplicacion
                //AbstractHttpConfigurer::disable : genera un token unico para cada uno de los formularios que tenemos en nuestra aplicacion
                //Entonces cuando nos llega una peticion se va a fijar  si de ese formulario que nos esta llegando la peticion
                // coincide el token con el que genero nuestra api
                //pero como trabajamos con un fornt-end externo a nuestra aplicacion lo tenemos que desabilitar para que no se fije por si nos llega un formulario malicioso

                //De dónde proviene:
                //CSRF Protection: Spring Security incluye, por defecto, protección contra ataques CSRF,
                // que son ataques en los que un usuario autenticado es engañado para ejecutar acciones en un sitio web malicioso.
                //AbstractHttpConfigurer::disable: AbstractHttpConfigurer es una clase de configuración base en Spring Security que permite personalizar la configuración de seguridad HTTP.
                // Aquí, ::disable es una referencia a un método que deshabilita la configuración específica.
                //Por qué se hace: En una API RESTful que utiliza JWT para la autenticación,
                // la protección contra CSRF generalmente no es necesaria, ya que la autenticación no depende de cookies de sesión del lado del servidor,
                // sino de tokens enviados en el encabezado de la solicitud.
                .csrf(AbstractHttpConfigurer::disable)

                //¿Qué es csrf?
                //csrf (Cross-Site Request Forgery): Es un tipo de ataque donde un usuario malintencionado engaña a alguien
                // para que realice una acción no deseada en un sitio web en el que ya ha iniciado sesión.
                // Por ejemplo, si estás conectado a tu banco y haces clic en un enlace malicioso,
                // ese enlace podría intentar transferir dinero sin tu permiso.
                //
                //¿Por qué se deshabilita?:
                //
                //Cuando usamos tokens: En tu aplicación, usas JWT (JSON Web Tokens) para autenticar a los usuarios.
                // JWT es una forma moderna y segura de autenticación que ya protege contra este tipo de ataques,
                // así que no necesitas la protección extra que csrf ofrece.





















                //httpSecurity.httpBasic(AbstractHttpConfigurer::disable)
                //Qué hace:
                //Esta línea desactiva la autenticación HTTP Basic.
                //
                //Por qué lo hace:
                //HTTP Basic es un método simple de autenticación que envía las credenciales (nombre de usuario y contraseña)
                // en cada solicitud en un encabezado Authorization codificado en Base64.
                // Aunque es sencillo de implementar, no es ideal para aplicaciones modernas que buscan mejores prácticas de seguridad, como:
                //
                //No es seguro por defecto: Las credenciales se envían en cada solicitud,
                // lo que puede ser peligroso si no se usa HTTPS.
                //No es flexible: No permite la complejidad de autenticación que las aplicaciones modernas necesitan, como el uso de tokens JWT.
                //En una configuración JWT, la autenticación HTTP Basic se desactiva porque se prefiere un enfoque
                // donde el cliente (por ejemplo, una SPA o aplicación móvil) se autentica una vez,
                // recibe un token JWT, y luego usa ese token en las solicitudes posteriores.



                //Seria la configuracion basica que nos provee spring, no es seguro porque las credenciales viajan sin cifrar.
                //Deshabilita la autenticación básica HTTP.
                // Esto evita que las solicitudes utilicen un encabezado Authorization con usuario y contraseña
                // en texto claro.

                //De dónde proviene:
                //HTTP Basic Authentication: Este es un método de autenticación en el que el cliente envía un encabezado Authorization
                // con un nombre de usuario y una contraseña codificados en base64. Es una técnica simple y no muy segura.
                //AbstractHttpConfigurer::disable: Nuevamente, deshabilita esta característica de seguridad.
                //Por qué se hace: En aplicaciones modernas, especialmente aquellas que usan JWT,
                // la autenticación básica no se utiliza, ya que se prefiere un enfoque más seguro y escalable (como JWT).
                .httpBasic(AbstractHttpConfigurer::disable)//Estamos desactivando la condifugracion basica que hace por defecto spring security.

                // httpBasic: Es una forma muy básica de pedirle a los usuarios que se identifiquen (es decir, que digan quiénes son).
                // Lo hace mostrando una ventana emergente en el navegador donde el usuario tiene que poner su nombre de usuario y contraseña.
                //
                //¿Por qué se deshabilita?:
                //
                //No es seguro: Los datos de usuario se envían de manera que cualquiera podría interceptarlos y verlos.

















                //httpSecurity.formLogin(AbstractHttpConfigurer::disable)
                //Qué hace:
                //Esta línea desactiva la funcionalidad de formulario de inicio de sesión proporcionada por Spring Security.
                //
                //Por qué lo hace:
                //Spring Security incluye por defecto un formulario de inicio de sesión HTML para la autenticación basada en formularios.
                // Sin embargo, en aplicaciones que utilizan JWT, la autenticación suele ser manejada por una API REST que recibe las credenciales del usuario, valida la autenticación, y devuelve un token JWT.
                //
                //Desactivar el formulario de inicio de sesión es necesario porque:
                //
                //Incompatible con JWT: El flujo típico de JWT no involucra la redirección a una página de inicio de sesión HTML. En su lugar, la autenticación se realiza a través de solicitudes API (por ejemplo, POST /api/auth/login), donde se envían las credenciales en el cuerpo de la solicitud.
                //Mejor control: Desactivar esta funcionalidad te da control completo sobre cómo se maneja la autenticación y cómo se envían las respuestas a las solicitudes de inicio de sesión, permitiendo una experiencia de usuario más coherente en aplicaciones modernas.



                //deshabilita la autenticacion basada en formularios HTML, porque se está usando JWT en su lugar.

                //De dónde proviene:
                //Form-Based Authentication: Este es un método tradicional de autenticación en aplicaciones web, donde un usuario ingresa su nombre de usuario y contraseña en un formulario HTML. Spring Security, por defecto, ofrece esta funcionalidad.
                //AbstractHttpConfigurer::disable: Deshabilita esta funcionalidad.
                //Por qué se hace: En una API RESTful, los usuarios no interactúan con formularios HTML para autenticarse.
                // En su lugar, envían tokens JWT en los encabezados de sus solicitudes, por lo que la autenticación basada en formularios no es necesaria.
                .formLogin(AbstractHttpConfigurer::disable)






                //Resumen de las 4 configuraciones:
                //CORS: Se configura para permitir solicitudes desde dominios externos (como un frontend separado).
                //CSRF: Se desactiva porque la protección CSRF es innecesaria cuando se usa JWT para la autenticación.
                //HTTP Basic: Se desactiva porque JWT es una alternativa más segura y flexible.
                //Formulario de inicio de sesión: Se desactiva porque las aplicaciones que usan JWT típicamente manejan la autenticación a través de API en lugar de formularios de inicio de sesión HTML.










                //Qué hace esta línea de código?
                //Esta línea de código está configurando las cabeceras HTTP que se envían en las respuestas del servidor, específicamente la cabecera X-Frame-Options.
                // En este caso, la configuración está deshabilitando (disable) la protección que esta cabecera ofrece.
                //
                //¿Qué es X-Frame-Options?
                //X-Frame-Options es una cabecera de seguridad que fue introducida para prevenir ataques del tipo Clickjacking.
                // Clickjacking es una técnica maliciosa que consiste en engañar a un usuario para que haga clic en algo diferente de lo que cree que está haciendo, por ejemplo, cargando una página legítima dentro de un iframe invisible o transparente sobre una página maliciosa.
                // Esto podría llevar al usuario a realizar acciones no deseadas, como enviar un formulario o hacer clic en un enlace engañoso.
                //
                //El valor de la cabecera X-Frame-Options puede ser:
                //
                //DENY: Evita que la página sea cargada en cualquier iframe, independientemente de dónde se origine la solicitud.
                //SAMEORIGIN: Permite que la página sea cargada en un iframe solo si el iframe pertenece al mismo origen que la página (es decir, el mismo dominio).
                //ALLOW-FROM uri: Permite que la página sea cargada en un iframe solo desde un origen específico.
                //¿Por qué se deshabilita la protección de X-Frame-Options?
                //En este caso, se está utilizando la configuración para deshabilitar (disable) la cabecera X-Frame-Options, lo que significa que la aplicación permite que sus páginas sean cargadas en un iframe desde cualquier origen.
                //
                //Razones comunes para deshabilitar X-Frame-Options:
                //Consola H2: En el contexto de aplicaciones de Spring Boot, una razón común para deshabilitar esta cabecera es permitir que la consola H2 (una base de datos en memoria que Spring Boot utiliza para pruebas y desarrollo) se cargue dentro de un iframe.
                // La consola H2 utiliza un iframe para mostrar su interfaz de usuario, y si X-Frame-Options está habilitado, el navegador bloqueará la carga de la consola.
                //
                //Integración con aplicaciones de terceros: A veces, deshabilitar esta cabecera es necesario cuando la aplicación necesita ser integrada o embebida en aplicaciones de terceros que la cargan dentro de un iframe.
                //
                //Riesgos asociados:
                //Deshabilitar X-Frame-Options puede hacer que tu aplicación sea vulnerable a ataques de Clickjacking, especialmente si se utiliza en un entorno de producción. Por lo tanto, es crucial asegurarse de que esto se haga solo cuando sea absolutamente necesario y en un entorno controlado, como un entorno de desarrollo o pruebas.
                //
                //Resumen:
                //Configuración: Se está configurando la cabecera HTTP X-Frame-Options para que no sea enviada en las respuestas HTTP.
                //Funcionalidad: Permite que las páginas de la aplicación sean cargadas dentro de iframes desde cualquier origen.
                //Motivación: Comúnmente utilizado para permitir la carga de la consola H2 en un iframe o para soportar integraciones donde se necesita embebido.
                //Riesgo: Deshabilitar esta protección puede exponer la aplicación a ataques de Clickjacking, por lo que debe hacerse con cuidado y, preferiblemente, solo en entornos donde se entiende y se acepta el riesgo.






                //El propósito de esta configuración es deshabilitar el encabezado de seguridad X-Frame-Options,
                // que normalmente previene que la aplicación sea embebida en un iframe.
                // Esto puede ser necesario en situaciones donde la aplicación debe permitir la carga
                // de ciertas páginas dentro de iframes,
                // como en el caso de la consola H2 o cuando se integra la aplicación dentro de un sistema
                // más grande que utiliza iframes.

                //Configuracion en los headers para desabilitar las frameOptions. osea Configuración de Encabezados
                //Lo desabilitamos porque estamos usando el h2-console para embeberlo en nuestra aplicacion y
                // poder acceder a la consola para poder manejar la base de datos.
                //Si no lo tuvieramos no podriamos acceder a la consola de la base de datos.

                //.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(...))
                //Qué hace: Configura los encabezados HTTP de la respuesta. En este caso, está específicamente configurando las opciones de seguridad relacionadas con los marcos (frames).
                //De dónde proviene:
                //headers: Es un método en la clase HttpSecurity de Spring Security que permite configurar varios
                // aspectos relacionados con los encabezados de seguridad HTTP.
                //httpSecurityHeadersConfigurer: Es un objeto de tipo HeadersConfigurer,
                // el cual proporciona métodos para configurar diferentes encabezados de seguridad.
                //frameOptions: Es un método dentro de HeadersConfigurer que se utiliza para configurar el encabezado
                // X-Frame-Options.
                //frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                //
                //Qué hace: Deshabilita la configuración de seguridad relacionada con X-Frame-Options.
                //De dónde proviene:
                //X-Frame-Options: Es un encabezado de seguridad HTTP que indica si un navegador debe permitir que una página se cargue en un iframe o no. Es una medida de seguridad para proteger contra ataques de clickjacking.
                //FrameOptionsConfig::disable: FrameOptionsConfig es una clase anidada dentro de HeadersConfigurer que permite configurar las opciones de X-Frame-Options. El método disable deshabilita esta protección.
                //Por qué se hace: Deshabilitar X-Frame-Options puede ser necesario si, por ejemplo, la aplicación debe permitir que ciertas páginas se carguen en un iframe. Esto es común cuando se usa la consola H2 (una base de datos embebida en Java) que generalmente se carga en un iframe y que de otra manera sería bloqueada por este encabezado.
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable))//Deshabilita la protección contra clickjacking que impide que la página sea cargada dentro de un <iframe>

                //Estoy permitiendo embeber la consola h2 que tambien va a estar en otro sitio web

                //Estas lienas de codigo son una configuracion de spring security para que se pueda embeber
                //la consola h2 que va a estar en otro sitio web.
                //Si esto no esta desativado yo no podria embeber la consola h2 ya que estaria activado
                //lo que se llama frameOptions que es una configuracion de seguridad que se usa para evitar que una
                //pagina web sea embebida dentro de un iframe en otro sitio web






                //Configuración de las Reglas de Autorización:
                //Defino cómo deben ser autorizadas las solicitudes HTTP,es decir que rutas y que roles pueden acceder a la aplicacion
                .authorizeHttpRequests(authorize ->
                                authorize// aca estamos autorizando a que ruta le puede pegar y con que rol cuando se hace una peticion.
                                        //permitAll() es para que todos los usuarios puedan acceder a la ruta.


                                        // Rutas exclusivas para STUDENT
                                        .requestMatchers(
                                                "/api/current/teacher",
                                                "/api/current",
                                                "/api/course/", "/api/course/**",
                                                "/api/schedule/", "/api/schedule/**",
                                                "/api/teacher/**", "/api/teacher/",
                                                "/api/attendance/", "/api/attendance/**",
                                                "/api/student/", "/api/student/**",
                                                "/api/course/create", "/api/schedule/createNewSchedule"
                                                //, "/api/schedule/deleteAllSchedule/{id}"
                                               // "/api/student/confirmCourse/{id}"
                                                //,"/api/course/coursesAvailableStudent"
                                        ).hasAnyRole("STUDENT", "ADMIN", "TEACHER")

                                        .requestMatchers( "/api/course/coursesAvailableStudent", "/api/student/confirmCourse/{id}").hasRole("STUDENT")
                                        .requestMatchers(HttpMethod.DELETE, "/api/schedule/deleteAllSchedule/{id}").hasRole("ADMIN")
                                        // Rutas exclusivas para ADMIN
                                        .requestMatchers(
                                                "/api/student/", "/api/student/**",
                                                "/api/teacher/**", "/api/teacher/",
                                                "/api/schedule/", "/api/schedule/**",
                                                "/api/attendance/", "/api/attendance/**",
                                                "/api/course/", "/api/course/**",
                                                "/api/course/create", "/api/schedule/createNewSchedule"
                                                //,"/api/course/create/coursesAvailableStudent"

                                        ).hasRole("ADMIN")


                                        // Rutas exclusivas para TEACHER
                                        .requestMatchers(
                                                "/api/current/teacher",
                                                "/api/course/", "/api/course/**",
                                                "/api/schedule/", "/api/schedule/**",
                                                "/api/student/", "/api/student/**"
//                                                "/api/attendance/", "/api/attendance/**"
                                        ).hasRole("TEACHER")


                                        // Rutas públicas
                                        .requestMatchers(
                                                "/api/auth/login",
                                                "/api/auth/register",
                                                "/api/auth/register/teacher",
                                                "/h2-console/**"
                                        ).permitAll()

                                        .anyRequest().authenticated()//: Cualquier solicitud que se haga para acceder a alguna otra ruta
                        // que no esta expecificada arriba tambienn el usuario tiene que estar autentucado.
                        // Requiere que cualquier otra solicitud esté autenticada.
                )

                //Estamo agregando jwtRequestFilter antes del  filtro UsernamePasswordAuthenticationFilter.
                //. Esto asegura que todas las solicitudes pasen por el filtro JWT antes de llegar a cualquier otro filtro de autenticación, validando así el JWT en cada solicitud.
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)




                //Propósito General
                //El propósito de esta configuración es establecer que la aplicación sea "sin estado" (stateless).
                // Esto significa que el servidor no mantendrá ninguna información de sesión entre solicitudes.
                // En lugar de depender de sesiones en el servidor para recordar el estado del usuario (como en las aplicaciones tradicionales),
                // cada solicitud debe contener toda la información necesaria para autenticarse, generalmente en forma de un token JWT.
                //No se mantiene estado del usuario: Cada solicitud HTTP debe ser independiente. El servidor no guarda ninguna información sobre el usuario entre solicitudes.

                //Qué hace: Configura la política de manejo de sesiones en Spring Security.
                //De dónde proviene:
                //sessionManagement: Es un método en la clase HttpSecurity que permite configurar cómo se manejarán las sesiones en la aplicación.
                //session: Es un objeto de tipo SessionManagementConfigurer, que se utiliza para configurar diferentes aspectos del manejo de sesiones,
                // como la creación de sesiones y la política de sesión.
                //sessionCreationPolicy: Es un método de SessionManagementConfigurer que permite definir cómo se manejarán las sesiones en la aplicación.


                //sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //Qué hace: Establece la política de creación de sesiones en STATELESS, lo que significa que no se crearán sesiones en el servidor para las solicitudes.
                //De dónde proviene:
                //SessionCreationPolicy: Es una enumeración (enum) en Spring Security que define las políticas de manejo de sesiones. Las opciones más comunes incluyen:
                //ALWAYS: Siempre crea una nueva sesión si no existe una.
                //IF_REQUIRED: Solo crea una sesión si es necesario (por defecto).
                //NEVER: Nunca crea una sesión, pero utiliza una sesión existente si la hay.
                //STATELESS: No crea ni utiliza sesiones, lo que significa que cada solicitud se maneja de forma completamente independiente.
                //Por qué se hace: Configurar SessionCreationPolicy.STATELESS es esencial en aplicaciones que usan JWT para la autenticación, ya que en estas aplicaciones, no se mantiene una sesión en el servidor.
                // Cada solicitud es independiente y se valida utilizando el token JWT enviado por el cliente.

                //Política de Sesión:
                //Aca deshabilitamos el manejo de las sesiones  y utilizamos autenticacion a traves de token ,
                // que son las que tienen que estar en cada peticion que nos mandan
                //como no vamos a trabajar con la sesion que nos genera spring security le ponemos el STATELESS
                //Configura la política de manejo de sesiones para ser "sin estado" (stateless).
                // En una API basada en JWT, no se mantiene ninguna sesión en el servidor;
                // el cliente debe enviar el JWT en cada solicitud.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)//le estamos poniendo STATELESS para no trabajar con la sesion//
                        // una sesión es una forma de mantener información sobre un usuario entre diferentes solicitudes HTTP.
                        // HTTP es un protocolo sin estado, lo que significa que cada solicitud es independiente y
                        // no mantiene información sobre la solicitud anterior.
                );//Esto indica que la política de creación de sesiones es stateless.
        // Lo cual significa que el servidor no mantendrá ninguna información sobre la sesión del usuario.
        // En lugar de eso, cada solicitud debe incluir un JWT válido para ser autenticada.




        //construimos la configuracion de la cadena de seguridad
        return httpSecurity.build();

    }

    @Bean
    //Estoy declarando un metodo para despues encriptar contraseñas
    //este metodo nos va a servir para despues poder encriptar la contraseña del usuario.
    //PasswordEncoder: Define cómo se codifican las contraseñas en la aplicación.
    // Aquí se utiliza BCryptPasswordEncoder, un algoritmo robusto para codificar contraseñas que incluye un salt y múltiples rondas de hashing, lo que lo hace seguro contra ataques de fuerza bruta.
    public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder();}

    @Bean
    //lo vamos a usar par autenticar a los usuarios una vez que se hayan logueado. para eso tenemos que crear un nuevo controlador
    //Proporciona un AuthenticationManager, que es responsable de manejar la autenticación en la aplicación.
    // Esto es necesario cuando se configura autenticación personalizada, como la basada en JWT.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//AuthenticationManager es una interfaz proporcionada por Spring Security que se encarga de manejar la autenticación.
    // AuthenticationConfiguration que es una clase de Spring Security que proporciona
    // la configuración relacionada con la autenticación.

    //authenticationConfiguration.getAuthenticationManager(): Llama al método getAuthenticationManager()
    // en el objeto authenticationConfiguration.
    // Este método proporciona el AuthenticationManager configurado en la instancia de AuthenticationConfiguration.

    //Cuál es el propósito del método?
    //
    //El método authenticationManager se usa para obtener una instancia de AuthenticationManager a partir de una configuración de autenticación proporcionada por AuthenticationConfiguration.
    // En una aplicación de Spring Security, el AuthenticationManager es esencial para manejar la autenticación de los usuarios.
    // Este método simplifica el acceso a esa instancia configurada al proporcionarla directamente a través de la llamada a getAuthenticationManager().







    //Propósito del Código
    //el propósito de esta configuración es asegurar la aplicación mediante:

    //1. Autenticación con JWT: Validar las solicitudes HTTP usando un token JWT, lo que permite asegurar que solo usuarios autenticados pueden acceder a ciertos recursos.

    //2.Deshabilitar Mecanismos de Seguridad Innecesarios: Como CSRF, HTTP básico y autenticación de formularios, ya que no son necesarios o relevantes en una API REST protegida por JWT.

    //3.Configurar CORS: Para controlar qué orígenes pueden hacer solicitudes a la API, protegiendo la aplicación contra ataques de origen cruzado.

    //4.Manejo de Sesiones Sin Estado: Configurar la API para que no maneje sesiones en el servidor, lo que es típico en APIs RESTful donde la autenticación se maneja con tokens en lugar de sesiones de usuario.

    //5.Seguridad de Contraseñas: Asegurar que las contraseñas se manejen de manera segura usando el algoritmo BCrypt.

    //En resumen, esta configuración prepara la aplicación para manejar la autenticación y autorización de manera segura, utilizando JWTs y configurando la cadena de filtros de Spring Security para proteger los endpoints de la API.

}
