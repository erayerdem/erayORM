package desingpatternwork.demo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Test<E> {
    Test(Class clazz) throws IllegalAccessException, InstantiationException {

        Object o = clazz.newInstance();
        clazz.
    }


    public static void main(String[] args) throws NoSuchFieldException {
        Test test=new Test(ArrayList.class);

    }
}
