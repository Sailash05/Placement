package com.placementserver.placementserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlacementserverApplication {

	public static void main(String[] args) {

		SpringApplication.run(PlacementserverApplication.class, args);
	}

}
