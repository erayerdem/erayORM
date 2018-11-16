package desingpatternwork.demo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Date;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
@Slf4j
public class SqlRepository<T> {
    private final Config config;



    public void persistInjectInsertPojo(T clazz) {


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

        StringBuilder myquery=new StringBuilder("CREATE TABLE "+className+"(");
        Field[] declaredFields = clazz.getClass().getDeclaredFields();
        Stream.of(declaredFields)
                .forEach(declaring -> {
                    myquery.append(declaring.getName()+" ");
                    if(!(declaring.getType().toString()).contains(".")){
                        myquery.append(declaring.getType()+", ");
                    }
                else {  int start=declaring.getType().toString().trim().lastIndexOf(".")+1;
                        String substring ;
                     substring=   declaring.getType().toString().trim().substring(start);
                        System.out.println("substring"+substring);
                        if(substring.equals("String"))
                            myquery.append("varchar(255),");
                    }
                });
        myquery.replace(myquery.lastIndexOf(","),myquery.capacity(),(");"));
        try {
            System.out.println("burasi calisiyor");
            config.getStatement().execute(myquery.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e){
            log.error(e.toString());
        }
        log.info("table created"+myquery);

    }

    private void persistCheckTable(T clazz) {

        String tablename = findClassName(clazz);
        log.info("table check edildi");
        try {
            System.out.println(tablename);
            int i = config.getStatement().executeUpdate("SHOW TABLES LIKE '%" + tablename + "%'");
            System.out.println(i);
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
}
