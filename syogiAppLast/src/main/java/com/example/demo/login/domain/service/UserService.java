package com.example.demo.login.domain.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Service
public class UserService{

	@Autowired
	UserDao dao;

	@Autowired
	PasswordEncoder passwordEncoder;

	//user情報を登録
	public boolean insertOne(User user){

		String password = passwordEncoder.encode(user.getPassword());

		user.setPassword(password);

		int insertNumber = dao.insertOne(user);

		System.out.println(insertNumber);

		if(insertNumber > 0) {
			System.out.println("user情報insert成功");
			return true;
		}
		else {
			System.out.println("user情報insert失敗");
			return false;
		}

	}

	//user情報をアドレスで取得
	public User selectOne(String address) {

		User  resultUser  = new User();

		Map<String, Object> map = dao.selectOne(address);

		resultUser.setUserId((int)map.get("id"));
		resultUser.setUserAddress((String)map.get("address"));
		resultUser.setUserName((String)map.get("name"));

		System.out.println(resultUser);

		return resultUser;
	}

	//user情報をIDで取得
	public User selectOne(int userId){

		User resultUser = new User();

		Map<String, Object> map = dao.selectOne(userId);

		resultUser.setUserId((int)map.get("id"));
		resultUser.setUserAddress((String)map.get("address"));
		resultUser.setUserName((String)map.get("name"));

		return resultUser;

	}

}