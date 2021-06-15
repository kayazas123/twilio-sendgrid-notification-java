package com.hardik.ballista.mail;

import java.io.IOException;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.hardik.ballista.mail.properties.SendGridConfigurationProperties;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.ClickTrackingSetting;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.MailSettings;
import com.sendgrid.helpers.mail.objects.OpenTrackingSetting;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.sendgrid.helpers.mail.objects.SpamCheckSetting;
import com.sendgrid.helpers.mail.objects.TrackingSettings;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@EnableConfigurationProperties(SendGridConfigurationProperties.class)
public class EmailService {

	private static final String FROM_NAME = "HeartCare Support";
	private static final String CONTENT_TYPE = "text/html";
	private static final String SPAM_POST_URL = "https://spamcatcher.sendgrid.com";
	private static final String REQUEST_ENDPOINT = "mail/send";

	private final SendGridConfigurationProperties sendGridConfigurationProperties;

	public Integer sendMail(final String subject, final String body, final String userEmailId) throws JSONException {
		final var properties = sendGridConfigurationProperties.getSendgrid();
		final var mail = new Mail();
		final var personalization = new Personalization();

		final var heartcareEmail = new Email();
		heartcareEmail.setName(FROM_NAME);
		heartcareEmail.setEmail(properties.getEmailId());

		final var userEmail = new Email();
		userEmail.setEmail(userEmailId);
		personalization.addTo(userEmail);

		mail.setFrom(heartcareEmail);
		mail.setSubject(subject);
		mail.addContent(getContent(body));
		mail.addPersonalization(personalization);
		mail.setMailSettings(getMailSetting());
		mail.setTrackingSettings(getTrackingSetting());

		final var sendGrid = new SendGrid(properties.getKey());
		final var request = new Request();

		request.setMethod(Method.POST);
		request.setEndpoint(REQUEST_ENDPOINT);

		Integer mailResponseStatus;
		try {
			request.setBody(mail.build());
			mailResponseStatus = sendGrid.api(request).getStatusCode();
		} catch (IOException e) {
			log.error("Unable To Send Email to " + userEmailId + " " + e);
			mailResponseStatus = 500;
		}

		return mailResponseStatus;
	}

	private Content getContent(final String body) {
		final var content = new Content();
		content.setType(CONTENT_TYPE);
		content.setValue(body);
		return content;
	}

	private SpamCheckSetting getSpamCheckSetting() {
		final var spamCheckSetting = new SpamCheckSetting();
		spamCheckSetting.setEnable(true);
		spamCheckSetting.setSpamThreshold(1);
		spamCheckSetting.setPostToUrl(SPAM_POST_URL);
		return spamCheckSetting;
	}

	private TrackingSettings getTrackingSetting() {
		final var trackingSettings = new TrackingSettings();
		final var clickTrackingSetting = new ClickTrackingSetting();
		clickTrackingSetting.setEnable(true);
		clickTrackingSetting.setEnableText(true);
		trackingSettings.setClickTrackingSetting(clickTrackingSetting);
		final var openTrackingSetting = new OpenTrackingSetting();
		openTrackingSetting.setEnable(true);
		trackingSettings.setOpenTrackingSetting(openTrackingSetting);
		return trackingSettings;
	}

	private MailSettings getMailSetting() {
		final var mailSettings = new MailSettings();
		mailSettings.setSpamCheckSetting(getSpamCheckSetting());
		return mailSettings;
	}

}