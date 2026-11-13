package DAO;

import java.util.List;

import model.Editorial;

public interface EditorialDAO {
    List<Editorial> getAll() throws Exception;
    void updateAll(List<Editorial> editoriales) throws Exception;
}