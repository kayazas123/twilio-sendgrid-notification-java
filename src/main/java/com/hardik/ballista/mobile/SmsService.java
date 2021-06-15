package com.hardik.ballista.mobile;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.hardik.ballista.mobile.properties.TwilioConfigurationProperties;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Message.Status;
import com.twilio.type.PhoneNumber;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(TwilioConfigurationProperties.class)
public class SmsService {

	private final TwilioConfigurationProperties twilioConfigurationProperties;

	public Status send(final String userMobileNumber, final String messageBody) {
		final var properties = twilioConfigurationProperties.getTwilio();
		Twilio.init(properties.getAccountSid(), properties.getAuthToken());
		final var userPhone = new PhoneNumber("+" + userMobileNumber);
		final var heartcarePhone = new PhoneNumber(properties.getContactNumber());

		final var message = Message.creator(userPhone, heartcarePhone, messageBody).create();
		return message.getStatus();
	}

}