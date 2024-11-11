package doa_bookstore;

import doa_bookstore.startup.StartupInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(StartupInitializer.class, args);
    }
}
