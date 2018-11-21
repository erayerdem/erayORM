package desingpatternwork.demo;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Test<E> {


    public static void main(String[] args) {
        Student student = new Student();
        Field[] declaredFields = student.getClass().getDeclaredFields();
        Stream.of(declaredFields).forEach(field -> System.out.println(field.getType()));


    }

    public String getlist() {

        return "12";

    }
}
