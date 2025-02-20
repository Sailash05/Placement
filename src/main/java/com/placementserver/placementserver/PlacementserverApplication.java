package com.placementserver.placementserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

@SpringBootApplication
public class PlacementserverApplication {

	public static void main(String[] args) {

		SpringApplication.run(PlacementserverApplication.class, args);
	}

}
