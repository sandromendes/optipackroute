package com.codejukebox.optipackroute.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.codejukebox.optipackroute")
public class OptipackrouteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OptipackrouteApiApplication.class, args);
	}

}
