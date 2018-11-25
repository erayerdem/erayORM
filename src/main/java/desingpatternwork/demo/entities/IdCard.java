package desingpatternwork.demo.entities;

import desingpatternwork.demo.Annatations.PrimaryKey;
import lombok.Data;

@Data
@PrimaryKey(value = "idcarddef", increment = true)
public class IdCard {

    public int idcarddef;
    private int idNumber;
    private String birthPlace;
    private String birthDate;
    private String motherName;
    private String fatherName;


}
