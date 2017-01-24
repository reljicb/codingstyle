package org.reljicb.codingstyle.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration()
public class CodingstyleApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CodingstyleApplication.class, args);
    }
}
