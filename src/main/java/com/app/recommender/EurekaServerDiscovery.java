package com.app.recommender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//@EnableZuulProxy
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerDiscovery {

	@Autowired
	private DiscoveryClient discoveryClient;

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "eureka-server");
		SpringApplication.run(EurekaServerDiscovery.class, args);
	}


}
