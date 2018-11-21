package desingpatternwork.demo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("students")
@AllArgsConstructor
@Slf4j
public class StudentController {

    private final SqlRepository<Student> studentSqlRepository;


    @PostMapping
    public void responseEntity(@RequestBody Student student) throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException {

        studentSqlRepository.persistSave(student);


    }

    @GetMapping
    public List<Student> getAllEntity() throws NoSuchFieldException, IllegalAccessException, SQLException, InstantiationException {
        List<Student> students = studentSqlRepository.persistFindAll();
        return students;

    }

    @GetMapping("/{id}")
    public Student getEntity(@PathVariable Long id) throws IllegalAccessException, SQLException, NoSuchFieldException, InstantiationException {
        Student student = studentSqlRepository.persistGetEntity(id);
        return student;

    }

    @DeleteMapping("/{id}")
    public void deleteEntity(@PathVariable Long id)  {
        try {
            studentSqlRepository.persistDeleteEntity(id);
        } catch (SQLException e) {
            log.error("boyle bir kayit bulunamadi...");
        }
    }
}
