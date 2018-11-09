package desingpatternwork.demo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Date;

@Component
@AllArgsConstructor
public class SqlRepository{
        private  final  Config config;
    public void persistInsertPojo() throws IllegalAccessException, NoSuchFieldException {

        Student student = new Student();
        student.setAge(12);
        student.setName("samet");
        student.setSurname("eray erdem");

        String trim = student.getClass().getName().trim();


        String myquery = "INSERT INTO  "  + trim.substring(trim.lastIndexOf('.') + 1) + "  VALUES"+" (";
        Field[] declaredFields = student.getClass().getDeclaredFields();
        for (Field field : declaredFields) {

            int start = field.toString().trim().lastIndexOf(".") + 1;
            int end = field.toString().trim().length();
            Field declaredField = student.getClass().
                    getDeclaredField(field.toString().trim().substring(start, end));
            declaredField.setAccessible(true);
            Object o = declaredField.get(student);
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
        }
            catch (Exception e ){
            e.printStackTrace();
            }
    }

    public void persistCreateTable() {


    }
}
