package ru.ranepa.hrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HrmApplication {
  public static void main(String[] args) {
    SpringApplication.run(HrmApplication.class, args);
  }
}
//http://localhost:8080/swagger-ui.html address API
// http://localhost:8080/h2-console address DB
// jdbc:h2:mem:hrm url
