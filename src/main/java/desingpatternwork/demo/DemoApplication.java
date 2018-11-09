package desingpatternwork.demo;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@AllArgsConstructor
@EnableAspectJAutoProxy
public class DemoApplication {
    private final SqlRepository sqlRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
            sqlRepository.persistInsertPojo();

            }
        };
    }
}
