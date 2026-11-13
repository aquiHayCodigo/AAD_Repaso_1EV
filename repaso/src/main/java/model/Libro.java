package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Libro implements Serializable{

    private long id;
    private String titulo;
    private String genero;
    private LocalDate fechaPublicacion;
    private String cifEditorial;

    public Libro(long id, String titulo, String genero, LocalDate fecha, String cifEditorial){
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.fechaPublicacion = fecha;
        this.cifEditorial = cifEditorial;
    }

    public  Libro(){
        
    }
        
    @Override
    public String toString() {
        String desc = "LIBRO --> ID:" + getId() + ", Titulo: " + getTitulo() + ", Genero: " + getGenero() + ", Publicacion: "
                + getFechaPublicacion() + ", cifEditorial: " + getCifEditorial();
        // desc += "\n, Clase: " + getClass() + ", hashCode: " + hashCode() + ", String: " + super.toString();
        return desc;
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public String getCifEditorial() {
        return cifEditorial;
    }

}
