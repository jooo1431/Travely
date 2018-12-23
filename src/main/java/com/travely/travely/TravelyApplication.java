package com.travely.travely;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravelyApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.properties,"
            + "classpath:aws.properties,"
            + "./../dev.properties,"
            + "./../prod.properties";

    public static void main(String[] args) {
        SpringApplication.run(TravelyApplication.class, args);
    }

}

