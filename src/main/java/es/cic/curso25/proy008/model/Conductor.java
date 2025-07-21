package es.cic.curso25.proy008.model;

public class Conductor {

    private Long id;
    private String nombre;
    private String apellido;
    private String tfno;
    private String email;
    private enum Genero {
        HOMBRE,
        MUJER,
        OTRO
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getTfno() {
        return tfno;
    }
    public void setTfno(String tfno) {
        this.tfno = tfno;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    };

    
}
