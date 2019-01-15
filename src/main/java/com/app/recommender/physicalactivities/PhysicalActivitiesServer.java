package com.app.recommender.physicalactivities;

import com.app.recommender.user.RegistrationServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication

public class PhysicalActivitiesServer {

    public static void main(String[] args){
        System.setProperty("spring.config.name", "physicalactivities-server");

        SpringApplication.run(RegistrationServer.class, args);
    }
}
