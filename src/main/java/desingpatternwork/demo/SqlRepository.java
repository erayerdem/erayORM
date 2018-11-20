package desingpatternwork.demo;

import desingpatternwork.demo.Annatations.PkAndName;
import desingpatternwork.demo.Annatations.PrimaryKey;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SqlRepository<T> {
     void persistSaveData(T t) throws SQLException, IllegalAccessException, NoSuchFieldException;

     String findClassName(T t);

     void persistCreateTable(T t);

     Optional<PrimaryKey> findPrimaryKey(T t);

    PkAndName persistgetLastValue(T clazz) throws SQLException;

     ResponseEntity<List<T>> persistGetByPrimaryKey();
}

