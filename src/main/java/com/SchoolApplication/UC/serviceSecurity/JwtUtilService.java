package com.SchoolApplication.UC.serviceSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtilService {

    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();//vamos a firmar el token con este clave secreta
    //Jwts es una clase que es parte de la libreria java jwt
    //.SIG es un metodo para expecificar que quiere firmar el token jwt
    //.HS256 algoritomo de firma que se va a usar para firmar el token (es decir para crear el toke)
    //.key() este metodo indica que estas a punto de firmar un token con esta clave.
    //.build() construye el obejto  y devuelve el token ya firmado y con la clave proporcionada.

    private static final long EXPIRATION_TOKEN = 1000 * 60 * 60;//Generamos el tiempo de expiración del token con una hora de variacion .

    //metodo que extrae todos los claims => lo que esta dentro del payload (osea la informacion del usuario logueado)
    public Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();

        //parser(): es un metodo estatico de la clase Jwts
        //Jwts.parser(): Crea un nuevo objeto JwtParser para analizar y validar el token.
        //verifyWith(SECRET_KEY): Especifica que el token debe ser verificado usando la clave secreta.
        // Es decir se verifica el token jwt usando una clave secreta.
        //.build() con este metodo se va a finalizar la configuracion de verificacoin
        // y se va a construir un objeto jwtparser listo para analizar token.
        //parseSignedClaims(token): Este metodo de la clase Jwtparser analiza el token jwt firmado y
        // devuelve un objeto Signed jwt que contiene las claims firmadas.
        // Analiza y valida el token firmado, devolviendo un objeto que contiene los claims.

        //getPayload(): Obtiene el payload, que es la parte del JWT que contiene los claims.

        //En resumen: Este metodo va a verificar un token usando una clave secreta y luego va a extraer
        // y devolver las claims del token verificado.
        //Su proposito: es extraer la información contenida en el payload del token JWT, una vez que se ha validado que el token es auténtico y no ha sido manipulado.
        //Esto  garantiza que el token JWT es seguro y no ha sido manipulado, permitiendo a la aplicación extraer y usar la información contenida en él para autorizar correctamente a los usuarios.

    }



    //Este metodo va a extraer algun claims en particular.
    //<T> : Con esto declaramos que el tipo de retorno de esta función es de tipo T.Y que no se que me va a devolver , puede ser cualquier claim
    //recibe como parametro el token y una funcion que recibe un claim y devuelve un T (que la voy a sacar  de la interfaz de claims)
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {// Extrae un claim específico del token usando una función que se aplica sobre los claims.
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);

        //claimsTFunction.apply(claims): Aplica la función proporcionada para extraer un claim específico de los claims.
        //claimsTFunction es el nombre de la funcion .
        //apply(claims) aca le estamos pasando un claim en particular.
    }


    //metodo que extrae el nombre de usuario.
    //Extrae el nombre de usuario (el "subject" del JWT) del token.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    //Usa extractClaim para obtener el claim correspondiente al "subject", que normalmente es el nombre de usuario.

//Claims::getSubject: Esto es una referencia a un método llamado getSubject en la clase Claims.

    //Claims es una clase proporcionada por la biblioteca Java JWT (JSON Web Tokens). Representa los claims contenidas en un token JWT.
    //getSubject es un método en la clase Claims que devuelve el sujeto (subject) del token. En el contexto de JWT, el sujeto suele ser el nombre de usuario o la identificación del usuario.

//El operador :: se utiliza para crear una referencia a un método o un constructor en Java.
//En este caso, Claims::getSubject es una referencia al método getSubject en la clase Claims.
// Esto significa que estamos pasando una referencia al método como argumento a la función extractClaim.





    //metodo que extrae la fecha de expiracion del token.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    //Usa extractClaim para obtener el claim de expiración del token.



    //metodo que nos devuelve un booleno para saber si el token esta expirado o no.
    // Verifica si el token ha expirado
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    //extractExpiration(token): Obtiene la fecha de expiración del token.
    //before(new Date()): Compara la fecha de expiración con la fecha actual para ver si ya ha pasado.

//Si la fecha de expiración es anterior a la fecha actual, el método devuelve true, lo que significa que el token está expirado.
// Si no, devuelve false, lo que indica que el token aún es válido.




    //metodo que crea el token, recibe como parametro el Map<String, Object> claims y el Username.

    public String createToken(Map<String, Object> claims , String username) {



        //Aca estamo generando creado todo lo que queremos que vaya dentro del payload.
        return Jwts
                .builder()       //dentro de la clase Jwts usamos el metodo builder para construir un nuevo objeto JwtBuilder.
                .claims(claims)      //Este metodo le pasamos los claims que recibe por parametro.
                .subject(username)   //Este metodo le pasamos el username que recibe por parametro y que es el que queremos que sea nuestro claim subject dentro del payload
                .setIssuedAt(new Date(System.currentTimeMillis()))   //Aca seria la fecha de emison del token que es lo que queremos que este tambien dentro del payload como "ISA"
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN)) //Aca seria la fecha de expiracion del token que es lo que queremos que este tambien dentro del payload com "EXP"
                .signWith(SECRET_KEY)    //usamos la secret key que creamos arriba para firmar este token que estamos generando.
                .compact();  //Con este metodo vamos a creat el token Jwt completo y lo vamos a devolver como un String
    }




    //En realidad, el método generateToken es un envoltorio (wrapper) alrededor de createToken.
    //Su función principal es preparar los datos necesarios antes de llamar a createToken.
    //En este caso, agrega el rol del usuario al mapa de reclamaciones (claims) y luego llama a createToken con esos datos.
    //metodo para generar el token
    //recibe como parametro un UserDetails es la instancia de la clase User que contiene la informacion del usuario que se va a autenticar.
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();//Estructura para asociar una clave a un valor.(Es un objeto que almacena pares clave-valor).
        //String va a ser el "rol" del usuario
        //Object : rol que vamos a obtener del UserDetails.

        //para obteber el rol: Accedo al metodo getAuthorities() del UserDetails para obtener las autoridades,devuelve una colección (generalmente una lista) de autoridades (o roles) asociadas al usuario.
        //con el iterator() combertimos esta coleccion de autoridades  en un iterador para poder recorrer los elementos de la coleccion uno por uno
        //.next() nos da el siguiente elemento de la iteracion , como se espera que el usuario tenga solo un rol se va a obtener el primer elemento de la coleccion
        //getAuthority() va a obtener el nombre o autoridad que en este caso seria el rol,devuelve el nombre del rol o autoridad como una cadena.
        //Por ejemplo, si el usuario tiene el rol “ADMIN”, este método devolverá la cadena “ADMIN”.
        String rol = userDetails.getAuthorities().iterator().next().getAuthority(); //Resumidamente: Obtiene el primer rol del usuario. Osea extrae el nombre de la primera autoridad (rol) asociada al usuario en el objeto userDetails. Ese nombre de autoridad se almacena en la variable rol.

        //luego se agrega el rol obtenido al Map<String, Object> con la clave rol.
        claims.put("rol", rol);//Basicamente estamos asignando el nombre del rol del usuario al campo "rol"

        //va a retornar el metodo createToken y le va a pasar por parametro los claims y el nombre de usuario obtenido del UserDetails
        //para generar un token (que es un String)
        return createToken(claims, userDetails.getUsername());
    }
}
