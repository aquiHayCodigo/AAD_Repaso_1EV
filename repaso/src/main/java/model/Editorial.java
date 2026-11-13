package model;

import java.io.Serializable;

public class Editorial implements Serializable{
    
    private String cif;
    private String nombre;
    private String pais;

    public Editorial(String cif, String nombre, String pais) {
        this.cif = cif;
        this.nombre = nombre;
        this.pais = pais;
    }

    public Editorial(){
        
    }

    public String getCif() {return cif;}
    public String getNombre() {return nombre;}
    public String getPais() {return pais;}

    @Override
    public String toString() {
        return "Editorial: CIF [" + cif + "] NOMBRE [" + nombre + "] PAIS [" + pais + "]";
    }

}
