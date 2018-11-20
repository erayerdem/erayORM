package desingpatternwork.demo;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("students")
@AllArgsConstructor
public class StudentController {

    private final SqlRepositoryIMPL<Student> sqlRepositoryIMPL;


    @PostMapping
    public void responseEntity(@RequestBody Student student) throws SQLException, NoSuchFieldException, IllegalAccessException {

        sqlRepositoryIMPL.persistSaveData(student);


    }

    @GetMapping
    public ResponseEntity<Student> getAllStudents() {

        return new ResponseEntity<Student>(null);

    }


}
