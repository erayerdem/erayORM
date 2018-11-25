package desingpatternwork.demo.Controller;

import desingpatternwork.demo.Annatations.KeyValue;
import desingpatternwork.demo.Repositories.SqlRepository;
import desingpatternwork.demo.entities.IdCard;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
@Slf4j
@AllArgsConstructor
@Controller
@RestController
@RequestMapping("idcards")
public class IdCardController {



    private final SqlRepository<IdCard> idCardSqlRepository;

    @PostMapping
    public void responseEntity(@RequestBody IdCard idcard) throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException {

        idCardSqlRepository.persistSave(idcard);


    }

    @GetMapping
    public List<IdCard> getAllEntity() throws NoSuchFieldException, IllegalAccessException, SQLException, InstantiationException {
        List<IdCard> idcards = idCardSqlRepository.persistFindAll();
        return idcards;

    }

    @GetMapping("/{id}")
    public IdCard getEntity(@PathVariable Long id) throws IllegalAccessException, SQLException, NoSuchFieldException, InstantiationException {
        IdCard idcard = idCardSqlRepository.persistGetEntity(id);
        return idcard;

    }

    @DeleteMapping("/{id}")
    public void deleteEntity(@PathVariable Long id) {
        try {
            idCardSqlRepository.persistDeleteEntity(id);
        } catch (SQLException e) {
            log.error("boyle bir kayit bulunamadi...");
        }
    }

    @PutMapping("/{id}")
    public void updateEntity(@RequestBody IdCard idcard) throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        idCardSqlRepository.persistUpdateEntity(idcard);

    }

    @GetMapping("custom")
    public List<IdCard> dda(@RequestParam(name = "key", required = true) String key, @RequestParam(name = "value", required = true) String value) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {

        try {
            Integer.parseInt(value);
        }
        catch (Exception e){
            value="'"+value+"'";

        }
        KeyValue keyValue = new KeyValue(key, value);
        List<IdCard> idcards = idCardSqlRepository.persistFindAllByQuery(keyValue);
        return idcards;
    }
}
