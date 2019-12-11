package com.example.changestream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class ChangestreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChangestreamApplication.class, args);
    }

}
