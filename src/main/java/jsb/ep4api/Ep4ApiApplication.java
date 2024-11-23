package jsb.ep4api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Ep4ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ep4ApiApplication.class, args);
    }

}
