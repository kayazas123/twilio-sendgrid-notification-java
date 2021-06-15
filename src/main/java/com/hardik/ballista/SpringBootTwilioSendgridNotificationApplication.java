package com.hardik.ballista;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringBootTwilioSendgridNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTwilioSendgridNotificationApplication.class, args);
	}

}
