package com.app.recommender.user;


import com.app.recommender.user.Persistence.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
public class RegistrationServer {


    public static void main(String[] args) {
        System.setProperty("spring.config.name", "registration-server");

        SpringApplication.run(RegistrationServer.class, args);

    }
}