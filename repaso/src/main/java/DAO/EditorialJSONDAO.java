package DAO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Editorial;

public class EditorialJSONDAO implements EditorialDAO {
    
    private final File archivo;
    private final ObjectMapper mapper;

    public EditorialJSONDAO(String ruta){
        this.mapper = new ObjectMapper();
        this.archivo =  new File(ruta);
        
    }

    @Override
    public List<Editorial> getAll() throws Exception {
        List<Editorial> editorialesList = new ArrayList<>();
        Editorial[] editoriales;
        try {
            /* for (Editorial editorial : editoriales) {
                editorial = mapper.readValue(this.archivo, Editorial.class);
                editoriales.add(editorial);
            } */
          editoriales = mapper.readValue(this.archivo, Editorial[].class);
          for (Editorial editorial : editoriales) {
            editorialesList.add(editorial);
          }
          editorialesList = mapper.readValue(this.archivo, new TypeReference<List<Editorial>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editorialesList;
    }

    @Override
    public void updateAll(List<Editorial> editoriales) throws Exception {

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(this.archivo, editoriales);
            
        } catch (Exception e) {
        }
    }
}
