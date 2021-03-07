package com.example.demo.login.domain.model;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class User{
	private int userId;

	@NotBlank
	@Length(min = 6, max = 20)
	private String password;

	@NotBlank
	@Length(min = 6, max=100)
	@Email
	private String userAddress;

	@NotBlank
	private String userName;

	private int loginType;

	private int authority;

	private int invalidFlag;
}