package ru.liga.intership.badcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.liga.intership.badcode.service.PersonService;

@SpringBootApplication
public class BadcodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BadcodeApplication.class, args);
        PersonService personService = new PersonService();
        personService.getBMI();
    }
}
