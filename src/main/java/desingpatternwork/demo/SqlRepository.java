package desingpatternwork.demo;

import desingpatternwork.demo.Annatations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
@Slf4j
public class SqlRepository<T> {

    private final Config config;


    public void persistSaveData(T clazz) {


        persistCheckTable(clazz);
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
        System.out.println(myquery);

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
        String myprimarykey = findPrimaryKey(clazz);
        System.out.println(myprimarykey + "benim primay key");
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

            config.getStatement().execute(myquery.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            log.error(e.toString());
        }
        log.info("table created" + myquery);

    }

    private void persistCheckTable(T clazz) {
        int i = 0;
        String tablename = findClassName(clazz);
        try {

            if (config.getDatabasemodel().equals("mysql")) {
                i = config.getStatement().executeUpdate("SHOW TABLES like '%" + tablename + "%'");
                System.out.println("durum" + i);
            } else if ("postgresql".equals(config.getDatabasemodel())){
                try {

                    config.getStatement().executeQuery("select * from" + tablename);
                } catch (Exception e) {
                    i = 0;

                }

            }

            log.info("table check edildi");
            if (i == 0) {

                persistCreateTable(clazz);
            }

            System.out.println(i);
        } catch (NullPointerException e) {


        } catch (SQLException e) {
            log.error(e.toString());
        } catch (Exception e) {
            log.error(e.toString());
        }

    }

    public String findPrimaryKey(T clazz) {
        Class<?> aClass = clazz.getClass();
        PrimaryKey[] annotationsByType = aClass.getAnnotationsByType(PrimaryKey.class);
        Optional<PrimaryKey> primaryKey = Optional.ofNullable(annotationsByType[0]);
        if (primaryKey.isPresent())
            return primaryKey.get().value();
        return "pkyoktur";
    }
}
