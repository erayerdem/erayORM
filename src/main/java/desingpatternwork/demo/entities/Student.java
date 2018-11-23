package desingpatternwork.demo.entities;

import desingpatternwork.demo.Annatations.PrimaryKey;
import lombok.Data;

@Data
@PrimaryKey(value = "studentid",increment = true)
public class Student {


    public int studentid;
    private  String name;
    private  String surname;
    private  int İdNumber ;
    private  int unicode;
    private  int schoolnumber;



}
