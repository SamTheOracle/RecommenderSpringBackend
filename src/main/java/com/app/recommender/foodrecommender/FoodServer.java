package com.app.recommender.foodrecommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableDiscoveryClient
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = IFoodRepository.class)
public class FoodServer {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "foodrec-server");

        SpringApplication.run(FoodServer.class, args);

    }
}
