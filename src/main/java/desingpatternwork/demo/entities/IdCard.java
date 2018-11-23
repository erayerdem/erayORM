package desingpatternwork.demo.entities;

import desingpatternwork.demo.Annatations.PrimaryKey;
import lombok.Data;

@Data
@PrimaryKey(value = "idCardid",increment = true)
public class IdCard {

    public  int idCardId;
    private  int İdNumber;
    private  String birthplace;
    private String birthdate;
    private  String mumname;
    private  String fathername;



}
