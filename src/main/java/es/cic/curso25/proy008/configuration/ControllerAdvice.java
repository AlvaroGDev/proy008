package es.cic.curso25.proy008.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String controloError(SecurityException ex) {
        return "Acceso denegado: " + ex.getMessage();
        // El usuario solo recibe este mensaje (en vez de el log completo) por las dos líneas añadidas en application.properties
    }

    
}
