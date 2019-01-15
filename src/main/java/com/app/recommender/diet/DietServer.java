package com.app.recommender.diet;


import com.app.recommender.diet.Persistence.DietRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = DietRepository.class)
public class DietServer {
    public static void main(String[] args){
        System.setProperty("spring.config.name", "diet-server");
        SpringApplication.run(DietServer.class);
    }
}
