package com.hardik.ballista.mobile.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.hardik.ballista")
public class TwilioConfigurationProperties {

	private Twilio twilio = new Twilio();

	@Data
	public class Twilio {
		private String accountSid;
		private String authToken;
		private String contactNumber;
	}

}