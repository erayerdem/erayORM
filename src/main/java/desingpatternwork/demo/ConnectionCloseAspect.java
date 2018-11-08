package desingpatternwork.demo;


import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Controller;

@Controller
@Aspect
@AllArgsConstructor
public class ConnectionCloseAspect {
    private  final  Config config;
    @After("execution(*  *.*.*.persist*(..))")
    public  void closeConnection(){

        try {
            config.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Before("execution(*  *.*.*.persist*(..))")
    public  void beConnection(){

        System.out.println("bağlandım bro");

    }



}
