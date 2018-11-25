package desingpatternwork.demo.entities;

import desingpatternwork.demo.Annatations.PrimaryKey;

@PrimaryKey(value = "universityId", increment = true)
public class University {


    public int universityId;
    private int unicode;
    private String universityName;
    private String city;
    private String country;
    private  String departmentName;
    private  int schoolnumber;



}
