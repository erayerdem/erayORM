package desingpatternwork.demo;


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

    @After("execution(* *.*.*.persist*(..))")
    public void closeConnection() {
        System.out.println("bağlantı koptu");
        if (config.getStatement() != null) {
            try {
                config.getStatement().close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (config.getConnection() != null) {
            try {
                config.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Before("execution(*  *.*.*.persist*(..))")
    public void beConnection() {
        System.out.println("bağlandı");
        try {

            Class.forName("com.mysql.jdbc.Driver");
            config.setConnection(DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword()));
            config.setStatement(config.getConnection().createStatement());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}