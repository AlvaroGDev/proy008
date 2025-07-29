package es.cic.curso25.proy008.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String apellido;
    private String tfno;
    private String email;
    private String genero;

    @JsonIgnore
    @OneToOne(mappedBy = "conductor", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
    private Viaje viaje;
    /*
     * El ignore básicamente le dice que a la hora de crear un viaje junto con el
     * conductor, no lo haga
     * en principio, el orden debería ser, un conductor se puede crear por si solo,
     * pero un viaje DEBE llamar a un conductor y crearlo si no existe uno
     * Es importante tener cuidado con el modo cascada en el remove. Si lo tenemos
     * en viaje (Que depende de conductor) al borrar un viaje
     * borrariamos el conductor también. Igual lo queremos o igual no, eso depende
     * de lo que queramos hacer
     */

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    // El mapped by hace dos cosas, la primera es decirnos quien va a tener la foreign key 
    // Cuando creo la mascota, creo el conductor
    private List<Mascota> mascotas = new ArrayList<>(); 

    public Conductor() {

    }

    public Conductor(Long id, String nombre, String apellido, String tfno, String email, String genero, Viaje viaje) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tfno = tfno;
        this.email = email;
        this.genero = genero;
        this.viaje = viaje;
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

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

}
