package es.cic.curso25.proy008.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coche {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String marca;
    private String tipoCombustible;
    private int numPuertas;
    private int numPlazas;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getTipoCombustible() {
        return tipoCombustible;
    }
    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }
    public int getNumPuertas() {
        return numPuertas;
    }
    public void setNumPuertas(int numPuertas) {
        this.numPuertas = numPuertas;
    }
    public int getNumPlazas() {
        return numPlazas;
    }
    public void setNumPlazas(int numPlazas) {
        this.numPlazas = numPlazas;
    }
}
