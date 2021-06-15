package com.hardik.ballista.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JacksonStdImpl
public class UserCreationRequestDto {

	@NotBlank(message = "fullname must not be null")
	@Size(max = 50)
	private final String fullName;

	@NotBlank(message = "password must not be null")
	@Size(max = 15)
	private final String password;

	@NotBlank(message = "email-id must not be null")
	@Email(message = "Email-id must be a valid email address")
	@Size(max = 50)
	private final String emailId;

	@NotBlank(message = "contact number must not be null")
	@Size(max = 12)
	private final String contactNumber;

}
