package desingpatternwork.demo;


import desingpatternwork.demo.Annatations.PrimaryKey;
import lombok.Data;

@Data
@PrimaryKey(value = "age",increment = true)
public class Student<T> {

    public int age;
    private String name;
    private String surname;


}
