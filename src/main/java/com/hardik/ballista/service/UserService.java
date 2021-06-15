package com.hardik.ballista.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hardik.ballista.dto.UserCreationRequestDto;
import com.hardik.ballista.entity.User;
import com.hardik.ballista.mail.EmailService;
import com.hardik.ballista.mobile.SmsService;
import com.hardik.ballista.repository.UserRepository;
import com.twilio.rest.api.v2010.account.call.FeedbackSummary.Status;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final EmailService emailService;
	private final SmsService smsService;

	public ResponseEntity<?> create(UserCreationRequestDto userCreationRequestDto) {

		if (checkForDuplicateContactNumber(userCreationRequestDto.getContactNumber()))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate contact number");

		if (checkForDuplicateEmail(userCreationRequestDto.getEmailId()))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate email-id");

		final var user = new User();
		final var response = new JSONObject();
		user.setContactNumber(userCreationRequestDto.getContactNumber());
		user.setEmailId(userCreationRequestDto.getEmailId());
		user.setFullName(userCreationRequestDto.getFullName());
		user.setPassword(userCreationRequestDto.getPassword());

		final var savedUser = userRepository.save(user);
		final var mediumContext = new ArrayList<String>();

		if (savedUser != null) {
			final var emailStatus = emailService.sendMail("Account Created Successfully",
					"Hello " + savedUser.getFullName() + ", your account was created successfully in the database",
					savedUser.getEmailId());

			final var smsStatus = smsService.send(savedUser.getContactNumber(),
					"Hello " + savedUser.getFullName() + ", your account was created successfully");

			if (!emailStatus.equals(500))
				mediumContext.add(savedUser.getEmailId());
			if (!smsStatus.equals(Status.FAILED))
				mediumContext.add(savedUser.getContactNumber());

			if (mediumContext.size() != 0)
				response.put("message",
						"Sent account creation notification to " + StringUtils.join(mediumContext, ", "));
			else
				response.put("message", "Could not send confirmation messages to your email and contact number");

			response.put("timestamp",
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString());

			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	private Boolean checkForDuplicateEmail(final String emailId) {
		return userRepository.existsByEmailId(emailId);
	}

	private Boolean checkForDuplicateContactNumber(final String contactNumber) {
		return userRepository.existsByContactNumber(contactNumber);
	}

}
