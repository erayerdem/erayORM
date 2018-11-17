package desingpatternwork.demo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("students")
@AllArgsConstructor
public class StudentController {

    private final SqlRepository<Student> sqlRepository;
    private final SqlRepository<MyPojo> myPojoSqlRepository;

    @PostMapping
    public void responseEntity(@RequestBody Student student) {

        sqlRepository.persistSaveData(student);


    }



}
