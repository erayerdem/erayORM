package desingpatternwork.demo;

import desingpatternwork.demo.Annatations.PkAndName;
import desingpatternwork.demo.Annatations.PrimaryKey;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public interface SqlRepository<T> {
    void persistSave(T t) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException;

    String findClassName(T t);

    void persistCreateTable(T t);

    Optional<PrimaryKey> findPrimaryKey(T t);

    PkAndName persistgetLastValue(T clazz) throws SQLException;

    ResponseEntity<List<T>> persistGetByPrimaryKey();

    List<T> persistFindAll() throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException;

    List<String> getFieldNameList(T t);

    void persistRemove(int id);

    <T> T persistGetEntity(Long id) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException;


    void persistDeleteEntity(Long id) throws SQLException;

    void persistUpdateEntity(T T);
}

