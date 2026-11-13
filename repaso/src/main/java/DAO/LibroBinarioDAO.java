package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Libro;

public class LibroBinarioDAO implements LibroDAO {

    private final File archivo;

    public LibroBinarioDAO(String ruta) {
        this.archivo = new File(ruta);
    }

    @Override
    public List<Libro> getAll() throws Exception {
        List<Libro> libros = new ArrayList<>();
        try (
            ObjectInputStream oisReader = new ObjectInputStream(new FileInputStream(this.archivo));
        ){
            while(true){
                Object o = oisReader.readObject();
                Libro libro = (Libro) o;
                libros.add(libro);
            }
            
        } catch (EOFException e) { 
        // A la que termina de leer el fichero de objetos salta esta excepcion, porque
        // aunque un fichero de texto normal se sabe cuando acaba porque lee una linea nula (null) en objetos nunca da null.
            System.out.println("Datos leidos correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return libros;
    }


    @Override
    public void updateAll(List<Libro> libros) throws Exception {
        File texto = new File("repaso\\src\\main\\java\\com\\example\\librosTexto.txt");
        String p = ";";
        try (
            ObjectOutputStream oosWriter= new ObjectOutputStream(new FileOutputStream(this.archivo));
            BufferedWriter bw = new BufferedWriter(new FileWriter(texto));
        ) {
            for (Libro libro : libros) {
                oosWriter.writeObject(libro);
                bw.write(libro.getId() + p + libro.getTitulo() + p + libro.getGenero() + libro.getFechaPublicacion() + p + libro.getCifEditorial());
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
