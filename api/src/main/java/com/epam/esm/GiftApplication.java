package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@EnableResourceServer
@EnableAuthorizationServer
@SpringBootApplication
@EntityScan(basePackages = {"com.epam.esm.model.entity"})
public class GiftApplication {
    public static void main(String[] args) {
        String testString = null;
        testString.contains("123");

        SpringApplication.run(GiftApplication.class, args);
    }
}
