package desingpatternwork.demo;


import desingpatternwork.demo.Annatations.PrimaryKey;
import lombok.Data;

@Data
@PrimaryKey(value = "id", increment = true)
public class Student<T> {
    public  int id;
    private int age;
    private String name;
    private String surname;



}
