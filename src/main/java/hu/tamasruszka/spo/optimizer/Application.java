package hu.tamasruszka.spo.optimizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "hu.tamasruszka.spo.optimizer.configuration")
public class Application {

    /**
     * Entry point for the spring boot application.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
