package desingpatternwork.demo;

import desingpatternwork.demo.Annatations.PkAndName;
import desingpatternwork.demo.Annatations.PrimaryKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@Slf4j
public class SqlRepository<T> {
    @Autowired
    private Config config;
    T t;
    SqlRepository() {


    }

    SqlRepository(Class aClass) throws IllegalAccessException, InstantiationException {
        t= (T) aClass.newInstance();

    }


    public void persistSaveData(T clazz) throws SQLException, IllegalAccessException, NoSuchFieldException {

        if (!config.checked) {
            config.checked = true;
            persistCreateTable(clazz);
        }
        PkAndName pkAndName = persistgetLastValue(clazz);
        if (pkAndName != null) {
            Class<Student> studentClass = Student.class;
            Field age = studentClass.getField(pkAndName.getName());

            age.set(clazz, pkAndName.getValue());


        }

        String tablename = findClassName(clazz);
        String myquery = "INSERT INTO  " + tablename + "  VALUES" + " (";
        Field[] declaredFields = clazz.getClass().getDeclaredFields();
        for (Field field : declaredFields) {

            int start = field.toString().trim().lastIndexOf(".") + 1;
            int end = field.toString().trim().length();
            Field declaredField = null;
            try {
                declaredField = clazz.getClass().
                        getDeclaredField(field.toString().trim().substring(start, end));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            declaredField.setAccessible(true);
            Object o = null;
            try {
                o = declaredField.get(clazz);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (o instanceof String || o instanceof Date) {
                myquery += " '";
                myquery += o;
                myquery += " '";
            } else {
                myquery += o;
            }
            myquery += ",";
        }

        myquery = myquery.substring(0, myquery.length() - 1);
        myquery += ")";

        try {
            config.getStatement().executeUpdate(myquery);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String findClassName(T clazz) {
        String trim = clazz.getClass().getName().trim();
        return trim.substring(trim.lastIndexOf('.') + 1);

    }

    public void persistCreateTable(T clazz) {
        String className = findClassName(clazz);
        String myprimarykey = "pkyoktur";
        boolean autoincrement = false;
        Optional<PrimaryKey> primaryKey = findPrimaryKey(clazz);
        if (primaryKey.isPresent()) {
            myprimarykey = primaryKey.get().value();
            autoincrement = primaryKey.get().increment();
        }

        StringBuilder myquery = new StringBuilder("CREATE TABLE " + className + "(");
        Field[] declaredFields = clazz.getClass().getDeclaredFields();
        Stream.of(declaredFields)
                .forEach(declaring -> {
                    myquery.append(declaring.getName() + " ");
                    if (!(declaring.getType().toString()).contains(".")) {
                        myquery.append(declaring.getType() + ", ");

                    } else {
                        int start = declaring.getType().toString().trim().lastIndexOf(".") + 1;
                        String substring;
                        substring = declaring.getType().toString().trim().substring(start);

                        if ("String".equals(substring))
                            myquery.append("varchar(255),");
                    }
                });
        if (!myprimarykey.equals("pkyoktur"))
            myquery.append("PRIMARY KEY (" + myprimarykey + "))");
        else
            myquery.replace(myquery.lastIndexOf(","), myquery.capacity(), (");"));
        try {
            System.out.println(myquery);
            config.getStatement().execute(myquery.toString());
            if (autoincrement) {
                String mysecond = "CREATE  TABLE  " + myprimarykey + "(" + myprimarykey + "   int )";
                System.out.println(mysecond);
                config.getStatement().execute(mysecond);
                String sonquery = "insert  into " + myprimarykey + "   values" + "(0);";

                config.getStatement().executeUpdate(sonquery);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            log.error(e.toString());
        }
        log.info("table created" + myquery);

    }


    public Optional<PrimaryKey> findPrimaryKey(T clazz) {
        Class<?> aClass = clazz.getClass();
        PrimaryKey[] annotationsByType = aClass.getAnnotationsByType(PrimaryKey.class);
        Optional<PrimaryKey> primaryKey = Optional.ofNullable(annotationsByType[0]);
        return primaryKey;
    }

    public PkAndName persistgetLastValue(T clazz) throws SQLException {
        PkAndName pkAndName = new PkAndName();

        boolean increment = false;
        String tablename = "yok";
        int sonvalue = -1;
        Optional<PrimaryKey> primaryKey = findPrimaryKey(clazz);
        if (primaryKey.isPresent()) {
            increment = primaryKey.get().increment();
            tablename = primaryKey.get().value();
            pkAndName.setName(tablename);

        }
        if (increment) {
            String queryasd = "select  " + tablename + "  from " + tablename;
            System.out.println(queryasd);
            ResultSet resultSet = config.getStatement().executeQuery(queryasd);

            while (resultSet.next()) {
                sonvalue = resultSet.getInt(tablename);
            }
            pkAndName.setValue(sonvalue);
            String query = "update  " + tablename + "   set  " + tablename + "=" + (sonvalue + 1) + " where " + tablename + " =" + sonvalue;
            System.out.println(query);
            config.getStatement().execute(query);
            return pkAndName;
        }
        //config.getStatement().executeUpdate(sonquery);r
        return null;
    }

    public ResponseEntity<List<T>> persistGetByPrimaryKey() {


        try {
            ResultSet resultSet = config.getStatement().executeQuery("select * from   ");
            while (resultSet.next()) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}
