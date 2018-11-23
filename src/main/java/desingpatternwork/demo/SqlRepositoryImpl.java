package desingpatternwork.demo;

import desingpatternwork.demo.Annatations.KeyValue;
import desingpatternwork.demo.Annatations.PkAndName;
import desingpatternwork.demo.Annatations.PrimaryKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@Slf4j
public class SqlRepositoryImpl<T> implements SqlRepository<T> {

    T cachedata;
    @Autowired
    private Config config;

    SqlRepositoryImpl() {


    }

    public void persistSave(T clazz) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        PkAndName pkAndName = null;
        cachedata = (T) clazz.getClass().newInstance();
        if (!config.checked) {
            config.checked = true;
            persistCreateTable(clazz);
        }
        Optional<PrimaryKey> primaryKey = findPrimaryKey(clazz);
        String value = primaryKey.get().value();
        Field declaredField1 = clazz.getClass().getDeclaredField(value);
        declaredField1.setAccessible(true);
        Integer o1 = (Integer) declaredField1.get(clazz);
        ;
        if (o1 == 0)
            pkAndName = persistgetLastValue(clazz);

        if (pkAndName != null) {
            Class<T> aClass = (Class<T>) cachedata.getClass();

            Field age = aClass.getField(pkAndName.getName());
            age.setAccessible(true);
            age.set(clazz, pkAndName.getValue());


        }
        String o12;
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
                o12 = (String) o;
                o12 = o12.trim();
                myquery += "'";
                myquery += o12;
                myquery += "'";
            } else {
                myquery += o;
            }
            myquery += ",";
        }

        myquery = myquery.substring(0, myquery.length() - 1);
        myquery += ")";
        System.out.println(myquery + "sokukquery");
        try {
            config.getStatement().executeUpdate(myquery);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        cachedata = clazz;
    }

    public String findClassName(T clazz) {
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
                String sonquery = "insert  into " + myprimarykey + "   values" + "(1);";

                config.getStatement().executeUpdate(sonquery);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            log.error(e.toString());
        }
        log.info("table created" + myquery);

    }

    @Override
    public Optional<PrimaryKey> findPrimaryKey(T clazz) {
        Class<?> aClass = clazz.getClass();
        PrimaryKey[] annotationsByType = aClass.getAnnotationsByType(PrimaryKey.class);
        try {

            Optional<PrimaryKey> primaryKey = Optional.ofNullable(annotationsByType[0]);
            return primaryKey;
        } catch (IndexOutOfBoundsException e) {


        }
        return Optional.ofNullable(null);
    }


    @Override
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

        return null;
    }


    @Override
    public List<T> persistFindAll() throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        String substring;

        System.out.println(cachedata + "cache data değerim");
        List<T> ts = new ArrayList<>();
        String className = findClassName(cachedata);
        String myquery = "select * from   " + className;
        System.out.println(myquery);
        ResultSet resultSet = config.getStatement().executeQuery(myquery);
        List<String> fieldNameList = getFieldNameList(cachedata);
        ts = persistRunQuery(myquery);
        return ts;
    }

    @Override
    public List<String> getFieldNameList(T clazz) {
        List<String> strings = new ArrayList<>();
        Field[] declaredFields = clazz.getClass().getDeclaredFields();
        Stream.of(declaredFields).forEach(field -> {
                    strings.add(field.getName());
                }

        );

        return strings;
    }

    @Override
    public List<T> persistFindAllByQuery(KeyValue keyValue) throws IllegalAccessException, SQLException, InstantiationException, NoSuchFieldException {
        String query = "select * from  ";
        String className = findClassName(cachedata);
        query = query + className + " where  " + keyValue.getKey() + "=" + keyValue.getValue() + ";";
        List<T> ts = persistRunQuery(query);
        System.out.println(ts.size() + "size");
        return ts;

    }

    @Override
    public List<T> persistRunQuery(String query) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        System.out.println(query);
        List<T> ts = new ArrayList<>();
        String substring;
        ResultSet resultSet = config.getStatement().executeQuery(query);
        List<String> fieldNameList = getFieldNameList(cachedata);
        while (resultSet.next()) {
            System.out.println("worked");
            for (int i = 0; i < fieldNameList.size(); i++) {
                Field declaredField = cachedata.getClass().getDeclaredField(fieldNameList.get(i));
                declaredField.setAccessible(true);
                int start = declaredField.getType().toString().trim().lastIndexOf(".") + 1;
                substring = declaredField.getType().toString().trim().substring(start);
                if ("String".equals(substring)) {
                    declaredField.set(cachedata, resultSet.getString(fieldNameList.get(i)));
                } else {
                    declaredField.set(cachedata, resultSet.getInt(fieldNameList.get(i)));
                }

            }

            ts.add(cachedata);
            cachedata = (T) cachedata.getClass().newInstance();
        }

        return ts;
    }


    @Override
    public T persistGetEntity(Long id) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException {

        List<String> fieldNameList = getFieldNameList(cachedata);
        String className = findClassName(cachedata);
        String query = "select * from  " + className;
        PrimaryKey primaryKey1 = null;
        Optional<PrimaryKey> primaryKey = findPrimaryKey(cachedata);
        if (primaryKey.isPresent())
            primaryKey1 = primaryKey.get();
        query = "select * from " + className + "  ";
        query = query + "  where " + primaryKey1.value() + "=" + id + "";
        System.out.println(query + "its get 1");

        List<T> ts = persistRunQuery(query);
        if (ts.isEmpty())
            return null;
        return ts.get(0);
        //  return (T) cachedata;
    }

    @Override
    public void persistDeleteEntity(Long id) throws SQLException {

        String className = findClassName(cachedata);
        Optional<PrimaryKey> primaryKey = findPrimaryKey(cachedata);
        PrimaryKey primaryKey1 = null;
        if (primaryKey.isPresent())
            primaryKey1 = primaryKey.get();
        String query = "delete from " + className + "  where " + primaryKey1.value() + "=" + id;
        System.out.println(query);


        boolean execute = config.getStatement().execute(query);


    }

    @Override
    public void persistUpdateEntity(T entity) throws NoSuchFieldException, IllegalAccessException, SQLException, InstantiationException {
        Optional<PrimaryKey> primaryKey = findPrimaryKey(entity);
        Field declaredField = cachedata.getClass().getDeclaredField(primaryKey.get().value());
        declaredField.setAccessible(true);
        Integer integer = (Integer) declaredField.get(entity);
        Long s1 = Long.valueOf(integer);
        persistDeleteEntity(s1);
        persistSave(entity);
    }


}
