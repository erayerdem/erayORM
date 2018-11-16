package desingpatternwork.demo;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {

        Student student=new Student();
        SqlRepository<Student> studentSqlRepository = new SqlRepository<>(null);
        studentSqlRepository.persistCreateTable(student);

    }
}
