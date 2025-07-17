package com.cts.OnlineDeliverySystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineDeliverySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineDeliverySystemApplication.class, args);
	}

}
