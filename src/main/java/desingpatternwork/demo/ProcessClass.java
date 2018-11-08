package desingpatternwork.demo;

import java.lang.reflect.Field;

public class ProcessClass {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
    String c="INSERT INTO TABLE_NAME VALUES (value1,value2,value3,...valueN);\n";
        Student student = new Student();
        student.setAge(12);
        student.setName("samet");
        student.setSurname("eray erdem");

        String trim=student.getClass().getName().toString().trim();


        String myquery="INSERT INTO "+trim.substring(trim.lastIndexOf('.')+1,trim.length())+" (";
        System.out.println(myquery);
        Field[] declaredFields = student.getClass().getDeclaredFields();
        for (Field field:declaredFields) {

        int start=field.toString().trim().lastIndexOf(".")+1;
         int end=field.toString().trim().length();
            Field declaredField = student.getClass().
                    getDeclaredField(field.toString().trim().substring(start,end));
        declaredField.setAccessible(true);
            Object o = declaredField.get(student);
            myquery+=o;
            myquery+=",";
        }

        System.out.println(myquery);

        Field field = student.getClass().getDeclaredField("name");

        field.setAccessible(true);

        Object value = field.get(student);
        Class<?> type = field.getType();

    }
}
