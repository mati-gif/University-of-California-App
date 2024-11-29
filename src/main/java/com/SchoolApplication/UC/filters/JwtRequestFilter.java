package com.SchoolApplication.UC.filters;

import com.SchoolApplication.UC.serviceSecurity.JwtUtilService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


//esta es la parte en la que el servidor verifica la validez del token JWT en cada solicitud, permitiendo el acceso a los recursos protegidos si el token es válido y tiene los permisos adecuados.
//Uno de los filtros que nos va a permitir la autenticacion de los usuarios a atraves de un Jwt token.
//Esta clase es un filtro que se ejecuta en cada solicitud HTTP que llega al servidor.
@Component//indica que es un componente de Spring.
// Spring administrara como un @Bean lo que esta anotando y lo agregara al contexto de la aplicacion.
//a anotación @Component permite que la clase JwtRequestFilter sea administrada por Spring,
// permitiendo inyecciones de dependencias y facilitando su uso dentro del ecosistema de Spring.
public class JwtRequestFilter extends OncePerRequestFilter {

    //OncePerRequestFilter  asegura que el la clase JwtRequestFilter junto con el método doFilterInternal se ejecute una vez por cada  solicitud HTTP.

    @Autowired//Estams inyectando el UserDetailsService (que es una interfaz) para obtener los datos del usuario
    private UserDetailsService  userDetailsService;

    @Autowired//Estamo inyectando la clase JwtUtilService que genera el token.
    private JwtUtilService jwtUtilService;


    @Override //sobreescribimos el metodo de la interfaz  OncePerRequestFilter.
    //Aca se realiza la logica principal del filtro
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //HttpServletRequest request: es una interfaz que representa la solicitud HTTP enviada por el cliente al servidor.
        //Representa la solicitud HTTP entrante que el filtro está procesando.
        // Permite acceder a la información de la solicitud, como encabezados, parámetros, y el cuerpo de la solicitud.

        //HttpServletResponse response: es una interfaz que representa la respuesta HTTP que el servidor envía de vuelta al cliente.
        //Representa la respuesta HTTP que el filtro está preparando para devolver al cliente.
        // Permite configurar el contenido de la respuesta, los encabezados y el estado HTTP.

        //FilterChain filterChain: es una interfaz que representa la cadena de filtros en la aplicación web.
        // Cada solicitud HTTP puede pasar por una serie de filtros antes de llegar al controlador (o al recurso solicitado).
        // representa la cadena de filtros que se aplican a la solicitud.
        // Este parámetro se usa para pasar la solicitud y la respuesta al siguiente filtro en la cadena.
        // Es esencial para que el flujo de la solicitud continúe a través de otros filtros.


        //Se maneja en un bloque try-catch para evitar que nos de un internal server error y poder hacer el correcto manejo de la excepcion.
        try{

            //Esta línea obtiene el valor del encabezado HTTP llamado "Authorization" de la solicitud (request).
            //Se almacena en la variable authorizationHeader
            final String authorizationHeader = request.getHeader("Authorization");//Extrae el encabezado Authorization de la solicitud HTTP. Este encabezado generalmente contiene el token JWT.
            // request.getHeader("Authorization") de la peticion que estamos recibinedo por paramemtro vamos a obtener el Header que seria el encabezado de autorizacon de la peticion
            //Si el cliente envía un token JWT en el encabezado de la solicitud, el valor de authorizationHeader podría ser algo como "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...".


            //Inicializar variables para el nombre de usuario y el JWT, declaramos dos variables nulas.
            String username = null;
            String jwt = null;


            //Si el encabezado de autorización no es null y si el encabezado comienza con "Bearer ", extrae el token JWT del encabezado. Luego, usa jwtUtilService para extraer el nombre de usuario del token.
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {




                //jwt = authorizationHeader.substring(7);

                //Qué hace:
                //Esta línea extrae el token JWT de la cadena authorizationHeader.
                //Cómo lo hace:
                //Usa el método substring(7) para cortar los primeros 7 caracteres de la cadena authorizationHeader, es decir, elimina la palabra "Bearer " y deja solo el token.
                //Ejemplo:
                //Si authorizationHeader es "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", después de esta línea, la variable jwt contendría "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...".
                jwt = authorizationHeader.substring(7);//El método substring de la clase String se usa para extraer una parte de la cadena. El índice 7 indica que se deben tomar los caracteres a partir de la posición 7 de la cadena hasta el final.
                //Extrae el token JWT del encabezado, eliminando el prefijo "Bearer "




                //username = jwtUtilService.extractUsername(jwt);

                //Qué hace:
                //Esta línea llama al método extractUsername del servicio jwtUtilService para extraer el nombre de usuario del token JWT.
                //Contexto:
                //jwtUtilService es un servicio que contiene lógica para manejar tokens JWT, incluyendo la extracción de información como el nombre de usuario.
                //Propósito:
                //jwtUtilService.extractUsername(jwt) decodifica el token JWT y obtiene el nombre de usuario que está almacenado en el "payload" (carga útil) del token.
                //Resultado:
                //Después de esta línea, la variable username contendrá el nombre de usuario asociado con el token JWT, que luego se puede utilizar para autenticar al usuario en el contexto de la aplicación.
                username = jwtUtilService.extractUsername(jwt);//usa jwtUtilService para extraer el nombre de usuario del token
            }



            //if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //
            //Qué hace:
            //Este es un condicional que evalúa dos condiciones:
            //username != null:
            //Verifica si la variable username no es null, es decir, que se haya extraído correctamente un nombre de usuario del token JWT.
            //SecurityContextHolder.getContext().getAuthentication() == null:
            //Verifica si el contexto de seguridad actual (SecurityContextHolder.getContext()) no tiene ya una autenticación establecida.
            //Propósito:
            //Este condicional asegura que se proceda con la autenticación solo si:
            //Se ha obtenido un nombre de usuario válido del token JWT.
            //No existe ya una autenticación activa en el contexto de seguridad.
            //Explicación detallada del condicional:
            //Si el nombre de usuario (username) no es null y si no hay una autenticación activa en el contexto de seguridad (SecurityContextHolder), entonces se ejecuta el bloque de código dentro del if


            //otra forma de explicarlo:
            //username != null:
            //Verifica si la variable username no es null. Esto significa que el nombre de usuario ha sido correctamente extraído del token JWT previamente.

            //SecurityContextHolder.getContext().getAuthentication() == null:
            //Verifica si el contexto de seguridad actual (SecurityContextHolder.getContext()) no tiene ya una autenticación establecida. Si esta condición es true, significa que no hay un usuario autenticado en el contexto actual.

            //Verifica si el nombre de usuario fue extraído y si no hay una autenticación ya establecida en el contexto de seguridad.
            //Si se ha extraído un nombre de usuario y el contexto de seguridad no tiene autenticación (es decir, el usuario no está autenticado aún),
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){







                //UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                //
                //Qué hace:
                //Esta línea llama al método loadUserByUsername del servicio userDetailsService para cargar los detalles del usuario a partir del nombre de usuario extraído.
                //Contexto:
                //userDetailsService es un servicio que implementa la interfaz UserDetailsService de Spring Security. Este servicio se utiliza para obtener los detalles del usuario (como roles, credenciales, etc.) desde una fuente de datos, como una base de datos.
                //Propósito:
                //Este método devuelve un objeto UserDetails que contiene toda la información necesaria sobre el usuario para realizar la autenticación.
                //Resultado:
                //La variable userDetails contendrá los detalles del usuario, que luego serán utilizados para crear un objeto de autenticación.

                //otra forma de explicarlo:

                //serDetailsService.loadUserByUsername(username):
                //
                //Carga los detalles del usuario correspondiente al nombre de usuario extraído (username).
                //Este método busca al usuario en una fuente de datos (por ejemplo, una base de datos) y devuelve un objeto UserDetails.

                //Qué es UserDetails:
                //
                //UserDetails es una interfaz de Spring Security que proporciona la información necesaria sobre el usuario, como su nombre de usuario, contraseña, y roles (autoridades).
                //Resultado:
                //
                //El objeto userDetails ahora contiene toda la información sobre el usuario que será utilizada para autenticarlo.


                //Generamos un objeto de tipo UserDetails y para asignarle datos vamos a usar userDetailsService para cargar los detalles del usuario con el nombre extraido del token (que vendria a ser el email)
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);//Genera un userDetail en el contextholder
                //Carga los detalles del usuario desde el servicio userDetailsService usando el nombre de usuario extraído.

// Este metodo carga desde la base de datos el usuario que coincide con el username extraído del token.
// El resultado es un objeto UserDetails que contiene información sobre el usuario, como su nombre de usuario, contraseña, y roles.




                //Otra forma de explicarlo:
                //Si el token JWT no ha expirado (es decir, si el método isTokenExpired(jwt) devuelve false), entonces el código dentro de este bloque if se ejecutará, permitiendo que el proceso de autenticación continúe.



                //if (!jwtUtilService.isTokenExpired(jwt)) {
                //
                //Qué hace:
                //Este es otro condicional que verifica si el token JWT aún es válido.
                //Cómo lo hace:
                //Llama al método isTokenExpired del servicio jwtUtilService, que devuelve true si el token ha expirado, y false si aún es válido.
                //Explicación detallada del condicional:
                //Si el token JWT no ha expirado (es decir, si !jwtUtilService.isTokenExpired(jwt) devuelve true), entonces el código dentro de este bloque if se ejecuta, permitiendo que el proceso de autenticación continúe.


                //Verifica si el token JWT ha expirado usando el servicio jwtUtilService.
                if(!jwtUtilService.isTokenExpired(jwt)){//Luego, verifica si el token no ha expirado. y usamos el metodo isTokenExpired que definimos en la clase JwtUtilService






                    //otra forma de explicarlo:
                    //new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()):
                    //
                    //Crea un nuevo objeto UsernamePasswordAuthenticationToken, que es una implementación de la interfaz Authentication en Spring Security.
                    //Parámetros:
                    //userDetails: Contiene la información del usuario que se está autenticando.
                    //null: Se refiere a la contraseña del usuario, que no es necesaria aquí porque la autenticación ya está validada por el token JWT.
                    //userDetails.getAuthorities(): Obtiene los roles o permisos del usuario, que se utilizan para determinar sus privilegios dentro de la aplicación.
                    //Propósito:
                    //
                    //Este objeto authentication representa la autenticación del usuario dentro de Spring Security. Contiene toda la información relevante sobre el usuario y sus roles.






                    //Qué hace:
                    //Esta línea crea un nuevo objeto UsernamePasswordAuthenticationToken, que es una implementación de la interfaz Authentication en Spring Security.
                    //Cómo lo hace:
                    //Se pasa el objeto userDetails al constructor junto con null (que sería la contraseña en un caso típico, pero no es necesaria aquí) y los roles o permisos (authorities) del usuario.
                    //Propósito:
                    //Este objeto authentication representa la autenticación del usuario dentro de Spring Security y contiene toda la información relevante, como el usuario y sus roles.
                    //Resultado:
                    //Se crea un token de autenticación que puede ser usado por el contexto de seguridad para representar al usuario autenticado.

                    //Si el token es válido, crea un objeto UsernamePasswordAuthenticationToken llamado authentication  que va a ser la autenticacion como tal con los detalles del usuario,extraido del userDetail.
                    //Es decir Crea un objeto UsernamePasswordAuthenticationToken con los detalles del usuario y sus autoridades(roles).
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    //  a las credenciales las vamos a estar pasando como nulll porque nosotros estamos trabajando con la autenticacion a traves del token.







                    //  //new WebAuthenticationDetailsSource().buildDetails(request):
                    //
                    //Construye detalles adicionales de autenticación usando el objeto request.
                    //Estos detalles adicionales pueden incluir información como la dirección IP del usuario y el tipo de navegador desde el cual se está realizando la solicitud.
                    //authentication.setDetails(...):
                    //
                    //Establece estos detalles adicionales en el objeto de autenticación authentication.
                    //Propósito:
                    //
                    //Estos detalles adicionales pueden ser útiles para auditorías o para aplicar restricciones de seguridad basadas en la sesión o la fuente de la solicitud.




                    //Esta línea añade detalles adicionales de la autenticación al token authentication, como la dirección IP del usuario y el tipo de navegador.
                    //Cómo lo hace:
                    //Utiliza WebAuthenticationDetailsSource para construir estos detalles a partir de la solicitud HTTP (request) y los establece en el objeto authentication.
                    //Propósito:
                    //Estos detalles adicionales pueden ser útiles para auditorías o para aplicar restricciones de seguridad adicionales basadas en la sesión o la fuente de la solicitud.
                    //Resultado:
                    //El token de autenticación ahora incluye detalles contextuales sobre la solicitud del usuario.

                    //Establece los detalles de la solicitud en el objeto de autenticación.
                    //establece los detalles de autenticación y configura el contexto de seguridad con esta autenticación.
                    // A autenticaton le seteamos los detalles con el constructor WebAuthenticationDetailsSource()
                    //le vamos a pasar el metodo buildDetails() con los que vamos a crear y establecer los detalles a la autenticacion  basado en la peticion proporcionada.
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // se utiliza para agregar detalles adicionales sobre la solicitud HTTP al objeto de autenticación (UsernamePasswordAuthenticationToken).


                    //WebAuthenticationDetailsSource: es una clase proporcionada por Spring Security.
                    //Esta clase se encarga de crear instancias de WebAuthenticationDetails,
                    // que son objetos que contienen detalles adicionales sobre la solicitud web, como la dirección IP del cliente y el nombre del host remoto.
                    //Al crear una nueva instancia de WebAuthenticationDetailsSource, estás preparando el objeto que generará estos detalles adicionales.


                    //buildDetails(HttpServletRequest request) es un método de la clase WebAuthenticationDetailsSource.
                    //Este método toma como parámetro la solicitud HTTP (HttpServletRequest) actual y crea un objeto WebAuthenticationDetails que contiene detalles sobre esa solicitud.
                    //El WebAuthenticationDetails generado incluirá:
                    //Remote Address: La dirección IP desde la cual se hizo la solicitud.
                    //Session ID: El ID de la sesión HTTP, si existe.
                    //Estos detalles pueden ser útiles para realizar un seguimiento adicional de la autenticación o para implementar medidas de seguridad adicionales (como detectar accesos desde ubicaciones sospechosas).




                    //setDetails(Object details) es un método de la clase AbstractAuthenticationToken, que es la clase base de UsernamePasswordAuthenticationToken.
                    //Este método asigna los detalles generados por buildDetails(request) al objeto de autenticación (authentication).
                    //Al hacer esto, estás asociando los detalles adicionales de la solicitud (como la dirección IP y el ID de la sesión) con el proceso de autenticación actual.
                    //Esto puede ser útil para mantener un registro más completo de las circunstancias en las que se realizó la autenticación y puede ser utilizado más adelante, por ejemplo, en registros de auditoría o en validaciones adicionales de seguridad.








                    //SecurityContextHolder.getContext().setAuthentication(authentication):
                    //
                    //Guarda el objeto authentication en el contexto de seguridad de Spring.
                    //Qué hace:
                    //Esto establece que el usuario ahora está autenticado en el contexto de seguridad, lo que significa que en adelante, el usuario será considerado autenticado para cualquier otra operación dentro de la sesión actual.
                    //Resultado:
                    //
                    //El contexto de seguridad de la aplicación ahora reconoce al usuario como autenticado, y sus permisos y roles serán respetados en futuras solicitudes.




                    //Qué hace:
                    //Esta línea establece el token de autenticación recién creado en el contexto de seguridad de Spring.
                    //Cómo lo hace:
                    //Usa SecurityContextHolder.getContext().setAuthentication(authentication) para almacenar el objeto authentication en el contexto de seguridad.
                    //Propósito:
                    //Esto significa que a partir de este punto, el usuario está autenticado dentro de la aplicación y se le otorgará acceso a los recursos protegidos según sus roles y permisos.
                    //Resultado:
                    //El contexto de seguridad ahora tiene conocimiento del usuario autenticado, y las solicitudes subsiguientes dentro de la misma sesión podrán acceder a los recursos protegidos sin necesidad de reautenticación.


                    //Establece la autenticación en el contexto de seguridad de Spring, indicando que el usuario está autenticado.
                    //SecurityContextHolder vamos a establecer la autenticacion del usuario actual y se usa para gestionar la autenticacion y autorizacion de los usuarios.
                    SecurityContextHolder.getContext().setAuthentication(authentication);


                }
            }
        }catch (Exception e){
//            System.out.println(e.getMessage());
            // En lugar de solo imprimir el error, devuelves un 401 Unauthorized
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "El token JWT no es válido o ha expirado.");

            // esto quiere decir que se va a ejecutar siempre
        } finally { //vamos a aplicar el filterChain que recibimos como parametro para que continue con la siguiente cadena de filtros pasandole la peticion y la respuesta
            filterChain.doFilter(request, response);
        }
    }


    //Verificación del usuario y contexto: Se verifica que el nombre de usuario sea válido y que no haya una autenticación activa en el contexto.
    //Carga de detalles del usuario: Se cargan los detalles del usuario desde el userDetailsService.
    //Verificación de expiración del token: Se verifica que el token JWT no haya expirado.
    //Creación de un token de autenticación: Se crea un objeto UsernamePasswordAuthenticationToken con los detalles del usuario y sus roles.
    //Adición de detalles de autenticación: Se agregan detalles adicionales como la IP o el navegador.
    //Establecimiento en el contexto de seguridad: Finalmente, se guarda el token de autenticación en el contexto de seguridad, autenticando al usuario dentro de la aplicación.
}
