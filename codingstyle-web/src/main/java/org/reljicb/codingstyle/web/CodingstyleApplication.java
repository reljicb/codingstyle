package org.reljicb.codingstyle.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration()
public class CodingstyleApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CodingstyleApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // initialization phase
        com.spinn3r.log5j.LogManager.enableExplicitShutdown();
    }

    @PreDestroy
    public void shutdown() {
        // in the very end of the shutdown routine
        com.spinn3r.log5j.LogManager.shutdown();
    }
}
