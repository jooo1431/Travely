package com.travely.travely;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TravelyApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.properties,"
            + "classpath:aws.properties,"
            + "./../dev.properties,"
            + "./../prod.properties";

    public static void main(String[] args) {
        new SpringApplicationBuilder(TravelyApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}

