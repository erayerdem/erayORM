package desingpatternwork.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(

        prefix = "databaseconnection"
)
@Component
@Data
public class Config {

    private  String name;
}
