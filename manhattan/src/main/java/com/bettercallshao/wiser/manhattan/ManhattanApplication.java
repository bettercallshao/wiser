package com.bettercallshao.wiser.manhattan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ManhattanApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManhattanApplication.class, args);
    }

}
