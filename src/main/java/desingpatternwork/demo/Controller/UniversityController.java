package desingpatternwork.demo.Controller;

import desingpatternwork.demo.Annatations.KeyValue;
import desingpatternwork.demo.Repositories.SqlRepository;
import desingpatternwork.demo.entities.University;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RequestMapping("universities")
@RestController
@AllArgsConstructor
@Slf4j
public class UniversityController {

    private final SqlRepository<University> studentSqlRepository;

    @PostMapping
    public void responseEntity(@RequestBody University university) throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException {

        studentSqlRepository.persistSave(university);


    }

    @GetMapping
    public List<University> getAllEntity() throws NoSuchFieldException, IllegalAccessException, SQLException, InstantiationException {
        List<University> universities = studentSqlRepository.persistFindAll();
        return universities;

    }

    @GetMapping("/{id}")
    public University getEntity(@PathVariable Long id) throws IllegalAccessException, SQLException, NoSuchFieldException, InstantiationException {
        University university = studentSqlRepository.persistGetEntity(id);
        return university;

    }

    @DeleteMapping("/{id}")
    public void deleteEntity(@PathVariable Long id) {
        try {
            studentSqlRepository.persistDeleteEntity(id);
        } catch (SQLException e) {
            log.error("boyle bir kayit bulunamadi...");
        }
    }

    @PutMapping("/{id}")
    public void updateEntity(@RequestBody University university) throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        studentSqlRepository.persistUpdateEntity(university);

    }

    @GetMapping("custom")
    public List<University> dda(@RequestParam(name = "key", required = true) String key, @RequestParam(name = "value", required = true) String value) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {

        try {
            Integer.parseInt(value);
        } catch (Exception e) {
            value = "'" + value + "'";

        }
        KeyValue keyValue = new KeyValue(key, value);
        List<University> universities = studentSqlRepository.persistFindAllByQuery(keyValue);
        return universities;
    }
}
