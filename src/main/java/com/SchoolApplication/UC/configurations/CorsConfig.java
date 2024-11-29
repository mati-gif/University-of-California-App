package com.SchoolApplication.UC.configurations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


//CORS es una política de seguridad del navegador que restringe cómo los recursos en una página web pueden ser solicitados desde un dominio diferente al que sirvió la página.
// En aplicaciones modernas, especialmente en aquellas que tienen un frontend separado del backend (por ejemplo, un frontend React o Angular y un backend Spring), es común que se necesiten solicitudes entre dominios.
//
//Por ejemplo, si tu frontend está alojado en https://frontend.com y tu backend en https://api.backend.com, sin una configuración CORS adecuada, el navegador bloquearía las solicitudes del frontend al backend.
// Configurar CORS permite que el backend acepte solicitudes desde el dominio del frontend, especificando qué métodos HTTP están permitidos, qué encabezados pueden enviarse, y qué orígenes tienen acceso.

@Configuration////@Configuration: Indica que esta clase es una configuración de Spring.
// Spring utilizará esta clase para configurar componentes y comportamientos específicos relacionados con la seguridad de la aplicación.
public class CorsConfig {

    @Bean
// Con esta anotacion la ponemos en el contexto de la aplicacion y que sea una de las primeras cosas que se ejecuten
    public CorsConfigurationSource corsConfigurationSource() {

        //Creamos una instancia de CorsConfiguration llamada configuration para poder hacerle modficaciones
        CorsConfiguration configuration = new CorsConfiguration();

        //Le seteamos las rutas que nos pueden "pegar" a la aplicacion.
        //"http://localhost:5173" es la que por defecto usa react que es la que vamos a tener en el front y es la que se va a comunicar con esta api para traer todos los clientes ,las cuentas ,prestamos
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:5173"));

        //Aca setemaos los metodos que nos pueden hacer con las peticiones.
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));

        //Aca seteamos la lista de todos los headers(encabezados) que nos pueden llegar a mandar.
        configuration.setAllowedHeaders(List.of("*"));

        //Creamos una fuenta de configuracion de cors para las rutas y la vamos a llamar source
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        //Aca establecemos que nos pueden "pegar" a cualquier ruta.
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
