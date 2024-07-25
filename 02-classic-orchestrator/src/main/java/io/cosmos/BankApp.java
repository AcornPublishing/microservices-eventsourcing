package io.cosmos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BankApp {
    //
    public static void main(String[] args) {
        //
        SpringApplication.run(BankApp.class, args);
    }
}
