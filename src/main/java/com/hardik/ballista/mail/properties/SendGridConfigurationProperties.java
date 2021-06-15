package com.hardik.ballista.mail.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.hardik.ballista")
public class SendGridConfigurationProperties {

	private Sendgrid sendgrid = new Sendgrid();

	@Data
	public class Sendgrid {
		private String emailId;
		private String key;
	}

}