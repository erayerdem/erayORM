package desingpatternwork.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(

        prefix = "databaseconnection"
)
@Component
@Data
public class Config implements AutoCloseable{

    private  String name;

    @Override
    public void close() throws Exception {
        System.out.println("kapattım");
    }

    public void persistexx(){

        System.out.println("dsadasd");

    }
}
