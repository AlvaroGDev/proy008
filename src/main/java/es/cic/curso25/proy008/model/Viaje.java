package es.cic.curso25.proy008.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String destino;
    private long distanciaKm;
    private String estado;
    private String fecha;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.EAGER) 
    private Conductor conductor;
/*Esto en la práctica lo que hace es:
Para generar un viaje, necesitamos un conductor (ya que tiene como clave foránea un idConductor)
Entonces, cuando intentamos generar un viaje, en caso de que no exista conductor (para un test, por ejemplo)
primero genera e inserta un conductor (para generar un id) y luego genera el viaje y lo inserta
referenciando al id de conductor que ha creado previamente
*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public long getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(long distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

     @Override
     public String toString() {
        return "Viaje [id=" + id + ", destino=" + destino + ", distancia en Km =" + distanciaKm + ", fecha =" + fecha + ", estado = " + estado + "]";
    }



}
