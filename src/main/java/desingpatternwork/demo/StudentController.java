package desingpatternwork.demo;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("students")
@AllArgsConstructor
public class StudentController {

    private final SqlRepository<Student> sqlRepository;


    @PostMapping
    public void responseEntity(@RequestBody Student student) throws SQLException, NoSuchFieldException, IllegalAccessException {

        sqlRepository.persistSaveData(student);


    }

    @GetMapping
    public ResponseEntity<Student> getAllStudents(){

    return  new ResponseEntity<Student>(null);

    }


}
