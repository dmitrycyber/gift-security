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
//        testMethod("qwe");
        SpringApplication.run(GiftApplication.class, args);
    }

//    private static void testMethod(String a) {
//        boolean qwe = a.contains("qwe");
//        System.out.println(qwe);
//    }
//
//    private static void testMethod2(String a) {
//        boolean qwe = a.contains("qwe");
//        System.out.println(qwe);
//    }
//
//    private static void testMethod3(String a) {
//        boolean qwe = a.contains("qwe");
//        System.out.println(qwe);
//    }
//
//    private static void testMethod4(String a) {
//        boolean qwe = a.contains("qwe");
//        System.out.println(qwe);
//    }
}
