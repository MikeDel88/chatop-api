package com.project.chatop;

import com.project.chatop.config.PropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PropertiesConfig.class)
public class ChaTopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChaTopApiApplication.class, args);
    }

}
