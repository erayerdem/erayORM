package desingpatternwork.demo.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Statement;

@ConfigurationProperties(

        prefix = "databaseconnection"
)
@Component
@Data
public class Config {
    boolean checked = false;
    private String databasemodel;
    private String url;
    private String username;
    private String password;
    private Connection connection;
    private Statement statement;

}
