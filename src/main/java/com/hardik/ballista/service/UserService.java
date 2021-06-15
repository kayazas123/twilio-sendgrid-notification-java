package com.hardik.ballista.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hardik.ballista.dto.UserCreationRequestDto;
import com.hardik.ballista.entity.User;
import com.hardik.ballista.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;

	public ResponseEntity<?> create(UserCreationRequestDto userCreationRequestDto) {

		if (checkForDuplicateContactNumber(userCreationRequestDto.getContactNumber()))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate contact number");

		if (checkForDuplicateEmail(userCreationRequestDto.getEmailId()))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate email-id");

		final var user = new User();
		user.setContactNumber(userCreationRequestDto.getContactNumber());
		user.setEmailId(userCreationRequestDto.getEmailId());
		user.setFullName(userCreationRequestDto.getFullName());
		user.setPassword(userCreationRequestDto.getPassword());

		userRepository.save(user);

		return ResponseEntity.ok().build();
	}

	private Boolean checkForDuplicateEmail(final String emailId) {
		return userRepository.existsByEmailId(emailId);
	}

	private Boolean checkForDuplicateContactNumber(final String contactNumber) {
		return userRepository.existsByContactNumber(contactNumber);
	}

}
