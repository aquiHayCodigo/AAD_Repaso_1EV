package com.example;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.EditorialCSVDAO;
import DAO.EditorialJSONDAO;
import DAO.LibroBinarioDAO;
import DAO.LibroJSONDAO;
import model.Editorial;
import model.Libro;

public class LibroService {

    static Scanner sc = new Scanner(System.in);
    static EditorialCSVDAO csv = new EditorialCSVDAO("repaso\\src\\main\\java\\com\\example\\editoriales.csv");
    static File archivoBinario = transformarTextoEnBinario();
    static LibroBinarioDAO libroBinario = new LibroBinarioDAO(archivoBinario.toString());
    static EditorialJSONDAO editorialesJson = new EditorialJSONDAO("repaso\\src\\main\\java\\dataJson\\editoriales.json");

    public static void main(String[] args) {
        boolean menu = true;
        while (menu) {
            System.out.println("- - - - - - - - - - - - - - - - -");
            System.out.println("Bienvenide a la tienda!");
            System.out.println("1) Mostrar todas las editoriales.");
            System.out.println("2) Actualizar editoriales.");
            System.out.println("3) Leer libros (Binario).");
            System.out.println("4) Añadir libro (Binario).");
            System.out.println("5) Pasar editoriales a JSON.");
            System.out.println("6) Pasar libros a JSON.");
            System.out.print("Escribe qué quieres hacer: ");
            int opc = Integer.parseInt(sc.nextLine());
            System.out.println("- - - - - - - - - - - - - - - - -");

            switch (opc) {
                case 1:
                    try {
                        System.out.println("Vamos a trabajar con CSV");
                        List<Editorial> lista = csv.getAll();
                        for (Editorial editorial : lista) {
                            System.out.println(editorial.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    try {
                        List<Editorial> listaActualizada = csv.getAll();
                        Editorial e1 = new Editorial("111222234", "Ximena", "Peru");
                        listaActualizada.add(e1);
                        csv.updateAll(listaActualizada);
                        System.out.println("La lista ha sido actualizada");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    System.out.println("Leyendo lista de libros: ");
                    try {
                        List<Libro> libros = libroBinario.getAll();
                        for (Libro libro : libros) {
                            System.out.println(libro.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    comprobarValidez();
                    break;

                case 5: 
                    pasarEditorialCsvABinario();
                    System.out.println("Pasando libros a JSON");
                    pasarEditorialBinarioJson();
                    try {
                    List<Editorial> editoriales = editorialesJson.getAll(); 
                    for (Editorial editorial : editoriales) {
                        System.out.println(editorial.toString());
                    }                       
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                break;

                case 6:
                    try {
                        // Leyendo libros de binario
                        List<Libro> libros = pasarLibroBinarioAObjeto();
                        System.out.println("Se ha pasado estos libros a json");
                        for (Libro libro : libros) {
                            System.out.println(libro.toString());
                        }
                        leerLibroBinarioJson();
                        // Leyendo de json
                        LibroJSONDAO libroJson = new LibroJSONDAO("repaso\\src\\main\\java\\dataJson\\libros.json");
                        libros = libroJson.getAll();
                        for (Libro libro : libros) {
                            System.out.println(libro.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                break;

                default:
                    break;
            }

        }

    }

    public static File transformarTextoEnBinario() {
        // --> 1º Leo fichero TEXTO, convertir líneas en objetos Libro
        File texto = new File("repaso\\src\\main\\java\\com\\example\\librosTexto.txt");
        List<Libro> libros = new ArrayList<>();
        comprobarExistencia(texto);
        try (
                BufferedReader br = new BufferedReader(new FileReader(texto));
            ) {
            String linea;
            while ((linea = br.readLine()) != null) {
                /*
                 * String[] libro = {
                 * linea.split(";")[0],
                 * linea.split(";")[1],
                 * linea.split(";")[2],
                 * linea.split(";")[3],
                 * linea.split(";")[4],
                 * };
                 */
                Libro libro = new Libro(
                        Long.parseLong(linea.split(";")[0]),
                        linea.split(";")[1],
                        linea.split(";")[2],
                        LocalDate.parse(linea.split(";")[3]),
                        linea.split(";")[4]);
                libros.add(libro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --> Paso la lista de libros a BINARIO
        File binario = new File("repaso\\src\\main\\java\\com\\example\\librosBinario.txt");
        comprobarExistencia(binario);
        try (
                ObjectOutputStream writerOos = new ObjectOutputStream(new FileOutputStream(binario));) {
            for (Libro libro : libros) {
                writerOos.writeObject(libro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return binario;
    }

    public static void comprobarExistencia(File archivo) {
        try {
            if (archivo.exists()) {
                if (archivo.isFile()) {
                    System.out.println("Actualmente estamos trabajando con el archivo: " + archivo);
                } else if (archivo.isDirectory()) {
                    System.out.println("Actualmente estamos trabajando con un directorio");
                }
            } else {

                // Path ruta = Paths.get(texto.toString());
                // Files.createFile(ruta);

                archivo.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void comprobarValidez() {
        System.out.println("Esta es la lista actual de libros: ");
        try {
            List<Libro> libros = libroBinario.getAll();
            for (Libro libro : libros) {
                System.out.println(libro.toString());
            }
            boolean encontrado = false;
            String editorial = " ";

            System.out.print("Introduce el número CIF de editorial: ");
            editorial = sc.nextLine();
            editorial.trim();
            // COMPROBANDO SI EXISTE EDITORIAL
            List<Editorial> lista = csv.getAll();
            for (Editorial editorialCheck : lista) {
                if (editorial.equals(editorialCheck.getCif())) {
                    encontrado = true;
                } else {
                    encontrado = false;
                }
            }

            if (encontrado == true) {
                System.out.println("Editorial ok, se ha registrado con el nombre: " + editorial);
            } else {
                System.out.println("No encuentro esa editorial, vuelve a intentarlo");
            }

            String nombreL = " ";
            LocalDate date = LocalDate.of(2000, 01, 01);
            boolean encontrado2 = false;

            System.out.print("Introduce un nombre de libro: ");
            nombreL = sc.nextLine();
            nombreL.trim();
            System.out.print("Escribe el año: ");
            int year = Integer.parseInt(sc.nextLine());
            System.out.print("Escribe el mes: ");
            int month = Integer.parseInt(sc.nextLine());
            System.out.print("Escribe el día: ");
            int day = Integer.parseInt(sc.nextLine());
            date = LocalDate.of(year, month, day);
            for (Libro libroCheck : libros) {
                if (nombreL.equals(libroCheck.getTitulo()) && date.equals(libroCheck.getFechaPublicacion())) {
                    encontrado2 = true;
                } else {
                    encontrado2 = false;
                }
            }

            if (encontrado2 == false) {
                System.out.println("Sin coincidencias, se ha registrado con nombre y fecha: " + nombreL + date);
            } else {
                System.out.println("Ya tenemos un libro con ese nombre y esa fecha en la editorial " + editorial);
            }

            System.out.print("Introduce el género: ");
            String gen = sc.nextLine();

            Libro l1 = new Libro(1148758, nombreL, gen, date, editorial);
            libros.add(l1);
            // ACTUALIZANDO LISTA DE LIBROS EN BINARIO !!!
            libroBinario.updateAll(libros);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Libro> pasarLibroBinarioAObjeto(){
        File LibroBinarioFile = new File("repaso\\src\\main\\java\\com\\example\\librosBinario.txt");
        List<Libro> libros = new ArrayList<>();
        try (
            ObjectInputStream oisReader = new ObjectInputStream( new FileInputStream(LibroBinarioFile));
        ){
            while(true){
                Object o = oisReader.readObject();
                Libro book = (Libro)o;
                libros.add(book);
            }
            
        } catch(EOFException e){
            System.out.println("Datos leidos correctamente");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return libros;
    }

    public static void leerLibroBinarioJson(){
        List<Libro> libros = pasarLibroBinarioAObjeto();
        File librosJsoneados = new File("repaso\\src\\main\\java\\dataJson\\libros.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(librosJsoneados, libros);
            String mappers = mapper.writeValueAsString(libros);
            System.out.println(mappers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Editorial> pasarEditorialesaObjeto(){
        List<Editorial> listaEditoriales= new ArrayList<>();
        File file = new File("repaso\\src\\main\\java\\com\\example\\editoriales.csv");
        try (
            BufferedReader br = new BufferedReader( new FileReader(file));
        ){
            String linea;
            while ((linea=br.readLine())!=null) {
                Editorial editorial = new Editorial(
                    linea.split(";")[0],
                    linea.split(";")[1],
                    linea.split(";")[2]
                );
                listaEditoriales.add(editorial);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaEditoriales;
    }

    public static void pasarEditorialCsvABinario(){
        List<Editorial> editoriales = pasarEditorialesaObjeto();
        File editorialesBinarioFile = new File("repaso\\src\\main\\java\\com\\example\\editorialesBinario.txt");
        try (
            ObjectOutputStream oosWriter = new ObjectOutputStream(new FileOutputStream(editorialesBinarioFile));
        ) {
            for (Editorial editorial : editoriales) {
                oosWriter.writeObject(editorial);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // HACEEEER
    public static void pasarEditorialCsvABinarioV2(){
        
    }

    public static List<Editorial> pasarEditorialBinarioAObjeto(){
        File fileBinarioEditoriales = new File("repaso\\src\\main\\java\\com\\example\\editorialesBinario.txt");
        List<Editorial> listaEditoriales = new ArrayList<>();
        try (
            ObjectInputStream oisReader = new ObjectInputStream(new FileInputStream(fileBinarioEditoriales));
        ){
            while(true){
                Object o = oisReader.readObject();
                Editorial editorial = (Editorial) o;
                listaEditoriales.add(editorial);
            }
        } catch(EOFException e){
            System.out.println("Datos de editoriales leidos correctamente");
        }
            catch (Exception e) {
            e.printStackTrace();
        }

        return listaEditoriales;
    }

    // HACEEEER
    public static List<Editorial> pasarEditorialBinarioAObjetoV2(){
        List<Editorial> editoriales = new ArrayList<>();

        return editoriales;
    }

    public static void pasarEditorialBinarioJson(){
        List<Editorial> listaEditoriales = pasarEditorialBinarioAObjeto();
        File EditorialesJsoneados= new File("repaso\\src\\main\\java\\dataJson\\editoriales.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(EditorialesJsoneados, listaEditoriales);
        } catch(EOFException e){

         }
         catch (Exception e) {
            e.printStackTrace();
        }
    }

    //HACEEER
    public static void pasarEditorialBinarioJsonV2(){

    }
}