package es.cic.curso25.proy008.controller;

public class SecurityException extends RuntimeException {
    
    public SecurityException(){

        super("Has tratado de modificar mediante creación");
    }

    public SecurityException(String message){
        super(message);
    }

    public SecurityException(String message, Throwable throwable){
        super(message,throwable);
    }
}
