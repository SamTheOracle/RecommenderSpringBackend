package com.app.recommender.greetings;

import com.app.recommender.records.RecordServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GreetingsServer {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "greeting-server");
        SpringApplication.run(GreetingsServer.class, args);
    }

}
