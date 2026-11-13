package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import model.Libro;

public class LibroJSONDAO implements LibroDAO {
    
    private final File archivo;
    private final ObjectMapper mapper;

    public LibroJSONDAO(String ruta) throws Exception {
        
        this.mapper = new ObjectMapper();
        // Necesario para serializar LocalDate
        this.mapper.registerModule(new JavaTimeModule()); 
        this.archivo = new File(ruta);

    }

    @Override
    public List<Libro> getAll() throws Exception {
        List<Libro> libros = new ArrayList<>();
        try {
            
            Libro libro = mapper.readValue(this.archivo, Libro.class);
            libros.add(libro);

            System.out.println("- - - - - - - A - - - - - - - - ");
            /* libros = mapper.readValue(this.archivo, new TypeReference<List<Libro>>(){});
            for (Libro libro : libros) {
                System.out.println(libro.toString());
            } */
           /* Map<Integer, Libro> mapaLibros = mapper.readValue(this.archivo, new TypeReference<Map<Integer,Libro>>(){});
           for (java.util.Map.Entry<Integer, Libro> libro : mapaLibros.entrySet()) {
           } */
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return libros;
    }

    @Override
    public void updateAll(List<Libro> libros) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAll'");
    }

}


