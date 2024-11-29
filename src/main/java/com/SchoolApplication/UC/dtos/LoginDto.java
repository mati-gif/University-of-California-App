package com.SchoolApplication.UC.dtos;

public record LoginDto(String email, String password) {

}


//Propósito General
//El propósito de este record es crear un objeto simple (con propiedades inmutables y genera tambien el constructor y los getters)  y eficiente para transferir los datos de inicio de sesión (login) que el usuario proporciona cuando intenta autenticarse en la aplicación.
// Este LoginDto encapsula el email y la password en una sola estructura que se puede pasar entre diferentes capas de la aplicación, como desde el controlador hasta el servicio de autenticación.
//
//Resumen
//public record LoginDto(String email, String password): Define un record en Java que representa un DTO para capturar los datos de inicio de sesión.
// El record automáticamente genera métodos getter, equals, hashCode, toString y un constructor para los campos email y password.
//Comentario //Dto para recibir datos de login.: Indica que este record se utiliza para recibir y encapsular los datos que el usuario proporciona al intentar iniciar sesión.

//Por qué se hace
//Simplicidad y Eficiencia: Los records en Java están diseñados para ser inmutables y proporcionar una forma concisa de declarar clases de datos, lo que reduce la cantidad de código necesario para crear objetos de solo datos (como DTOs).
//Claridad: Utilizar un DTO para encapsular los datos de inicio de sesión proporciona una clara separación entre los datos que se transfieren y la lógica de negocio, haciendo que el código sea más fácil de entender y mantener.
//Seguridad: Al encapsular los datos de inicio de sesión en un objeto, es más fácil gestionar, validar y asegurar estos datos a medida que se transfieren a través de diferentes partes de la aplicación.