package com.example.demo.login.domain.repository;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

public interface UserDao{

	//user情報を登録
	public int insertOne(User user) throws DataAccessException;

	//user情報をアドレスで取得
	public Map<String, Object> selectOne(String address) throws DataAccessException;

	//user情報をIDで取得
	public Map<String, Object> selectOne(int userId) throws DataAccessException;

}