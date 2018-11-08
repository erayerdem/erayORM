package desingpatternwork.demo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class UniversityController {

    private  final  SqlRepository<University> sqlRepository;
}
