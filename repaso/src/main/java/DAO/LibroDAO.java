package DAO;

import java.util.List;

import model.Libro;

public interface LibroDAO {
    List<Libro> getAll() throws Exception;
    void updateAll(List<Libro> libros) throws Exception;
}