package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import model.Editorial;

public class EditorialCSVDAO implements EditorialDAO{
    
    private final File archivo;

    public EditorialCSVDAO(String ruta){
        this.archivo = new File(ruta);
    }

    @Override
    public List<Editorial> getAll() throws Exception {
        comprobarExistencia();

        List<Editorial> editoriales = new ArrayList<>();
        try (
            BufferedReader br = new BufferedReader(new FileReader(this.archivo));
        ) {
            String linea;
            while((linea=br.readLine())!=null){
                String[] elemtLista = linea.split(";");
                String cif = elemtLista[0];
                String nombre = elemtLista[1];
                String pais = elemtLista[2];
                Editorial editorial = fromCSV(cif, nombre, pais);
                editoriales.add(editorial);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return editoriales;
    }

    public Editorial fromCSV(String cif, String nombre, String pais){
        Editorial editorial = new Editorial(cif, nombre, pais);
        return editorial;
    }

    public void comprobarExistencia() {
        try {
            if (this.archivo.exists()) {
                if (this.archivo.isFile()) {
                    System.out.println("Actualmente estamos trabajando con el archivo: " + this.archivo);
                } else if (this.archivo.isDirectory()) {
                    System.out.println("Actualmente estamos trabajando con un directorio");
                }
            } else {
                this.archivo.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAll(List<Editorial> editoriales) throws Exception {
        try (
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.archivo, false));
        ){
            for (Editorial editorial : editoriales) {
                String p = ";";
                bw.write(editorial.getCif() + p + editorial.getNombre() + p + editorial.getPais());
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
