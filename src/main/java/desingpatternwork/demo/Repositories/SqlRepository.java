package desingpatternwork.demo.Repositories;

import desingpatternwork.demo.Annatations.KeyValue;
import desingpatternwork.demo.Annatations.PkAndName;
import desingpatternwork.demo.Annatations.PrimaryKey;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface SqlRepository<T> {
    void persistSave(T t) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException;

    String findClassName(T t);

    void persistCreateTable(T t) throws SQLException;

    Optional<PrimaryKey> findPrimaryKey(T t);

    PkAndName persistgetLastValue(T clazz) throws SQLException;



    List<T> persistFindAll() throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException;

    List<String> getFieldNameList(T t);

    List<T>  persistFindAllByQuery(KeyValue keyValue) throws IllegalAccessException, SQLException, InstantiationException, NoSuchFieldException;

    <T> T persistGetEntity(Long id) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException;

     List<T> persistRunQuery(String query) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException;
    void persistDeleteEntity(Long id) throws SQLException;

    void persistUpdateEntity(T T) throws NoSuchFieldException, IllegalAccessException, SQLException, InstantiationException;

    boolean isTableCreated(String name);

}

