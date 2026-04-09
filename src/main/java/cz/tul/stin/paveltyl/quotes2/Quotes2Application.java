package cz.tul.stin.paveltyl.quotes2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Quotes2Application {

    public static void main(String[] args) {
        SpringApplication.run(Quotes2Application.class, args);
    }

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }
}
