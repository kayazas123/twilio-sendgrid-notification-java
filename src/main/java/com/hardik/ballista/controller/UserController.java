package com.hardik.ballista.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.ballista.dto.UserCreationRequestDto;
import com.hardik.ballista.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

	private final UserService userService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Creates a user record in the system")
	public ResponseEntity<?> userCreationHandler(
			@Valid @RequestBody(required = true) final UserCreationRequestDto userCreationRequestDto) {
		return userService.create(userCreationRequestDto);
	}

}
