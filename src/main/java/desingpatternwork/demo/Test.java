package desingpatternwork.demo;

import desingpatternwork.demo.entities.Student;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Test<E> {


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Student student = new Student();

        Field declaredFields = student.getClass().getField("studentid");
        Object o = declaredFields.get(student);
        System.out.println(o);
    }


}
