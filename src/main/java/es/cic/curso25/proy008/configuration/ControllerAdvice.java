package es.cic.curso25.proy008.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import es.cic.curso25.proy008.controller.ConductorController;

@RestControllerAdvice
public class ControllerAdvice {

        private final static Logger LOGGER = LoggerFactory.getLogger(ConductorController.class);

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String controloError(SecurityException ex) {
        LOGGER.error("Error: ", ex.getMessage());
        return "Acceso denegado: " + ex.getMessage();
        // El usuario solo recibe este mensaje (en vez de el log completo) por las dos líneas añadidas en application.properties
    }


    
}
