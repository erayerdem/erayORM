package desingpatternwork.demo;


import desingpatternwork.demo.Annatations.PrimaryKey;
import lombok.Data;

@Data
@PrimaryKey("age")
public class Student {

    private int age;
    private String name;
    private String surname;

    public static void main(String[] args) {

    }

}
