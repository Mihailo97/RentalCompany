package rs.raf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"rs.raf.rental"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}