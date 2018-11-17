package desingpatternwork.demo;

import java.lang.reflect.Field;
import java.util.Date;

public class Test {
    public  void  dasda() throws NoSuchFieldException, IllegalAccessException {
        Student clazz = new Student();
        clazz.setSurname("erdem");
        clazz.setName("samet");
        clazz.setAge(12);
        String tablename = "dsadas";
        String myquery = "INSERT INTO  " + tablename + "  VALUES" + " (";
        Class<Student> studentClass =Student.class;
        Field age = studentClass.getField("age");

        age.set(clazz,112);

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

    }
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Test test = new Test();
        test.dasda();
    }
}
