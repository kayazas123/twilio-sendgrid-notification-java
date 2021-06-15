package com.hardik.ballista.dto;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JacksonStdImpl
public class UserCreationRequestDto {

	private final String fullName;
	private final String password;
	private final String emailId;
	private final String contactNumber;

}