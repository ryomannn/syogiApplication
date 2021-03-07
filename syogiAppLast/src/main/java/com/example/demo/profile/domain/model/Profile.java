package com.example.demo.profile.domain.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class Profile implements Serializable{

	private int user_id;
	
	@NotBlank
	private String gender;
	@NotBlank
	@Length(min = 6, max=100)
	private String hobby;
	
	@NotBlank
	@Length(min = 1, max=100)
	private String grade;
	
	@NotBlank
	@Length(min = 3, max=100)
	private String history;
	
	@NotBlank
	@Length(min = 2, max=100)
	private String fav_battleType;
	

	private String comment;
}