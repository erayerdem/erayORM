package desingpatternwork.demo;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;

@Component
public class SqlRepository<T> {

    public void insertPojo(T t) throws IllegalAccessException, NoSuchFieldException {
        Student student = new Student();
        student.setAge(12);
        student.setName("samet");
        student.setSurname("eray erdem");

        String trim = student.getClass().getName().toString().trim();


        String myquery = "INSERT INTO " + trim.substring(trim.lastIndexOf('.') + 1, trim.length()) + " (";
        System.out.println(myquery);
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

        Field field = student.getClass().getDeclaredField("name");

        field.setAccessible(true);

        Object value = field.get(student);
        Class<?> type = field.getType();
    }

    public void createTable() {


    }
}
