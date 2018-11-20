package desingpatternwork.demo;

import java.util.ArrayList;

public class Test<E> {

    Test(Class clazz) throws IllegalAccessException, InstantiationException {

        Object o = clazz.newInstance();

    }


    public static void main(String[] args) throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        Test test = new Test(ArrayList.class);
         new ArrayList<>();

    }
}
