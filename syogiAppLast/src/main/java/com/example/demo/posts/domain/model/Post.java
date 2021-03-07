package com.example.demo.posts.domain.model;

import lombok.Data;

@Data
public class Post{

	private int userId;

	private String title;

	private String contents;

}