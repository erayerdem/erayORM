package desingpatternwork.demo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class StudentController {

    private  final  SqlRepository<Student> sqlRepository;






}
