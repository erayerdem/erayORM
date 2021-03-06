package desingpatternwork.demo.aspects;


import desingpatternwork.demo.configuration.Config;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Aspect
@AllArgsConstructor
public class ConnectionCloseAspect {

    private final Config config;

    @After("execution(* *.*.*.*.persist*(..))")
    public void closeConnection() {

        if (config.getStatement() != null) {
            try {
                config.getStatement().close();
                config.getConnection().close();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (config.getConnection() != null) {
            try {
                System.out.println(config+"occonfig");
                config.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Before("execution(*  *.*.*.*.persist*(..))")
    public void beConnection() {
        System.out.println("my aspect is working");

        try {
            if ("mysql".equals(config.getDatabasemodel())) {
                Class.forName("com.mysql.jdbc.Driver");
                config.setConnection(DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword()));
                config.setStatement(config.getConnection().createStatement());
            } else if ("postgresql".equals(config.getDatabasemodel())) {
                Class.forName("org.postgresql.Driver");
                config.setConnection(DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword()));
                config.setStatement(config.getConnection().createStatement());

            }

        } catch (SQLException e) {
            System.out.println("bağlantı sorunlu");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("bağlantı sorunlu");
        }
    }
}