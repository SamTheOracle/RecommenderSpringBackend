package com.app.recommender.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class GateWay {

    public static void main(String[] args){
        System.setProperty("spring.config.name", "gateway-server");
        SpringApplication.run(GateWay.class);
    }
}
