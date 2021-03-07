package com.example.demo.login.domain.jdbc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository
public class UserDaoJdbcImpl implements UserDao{

	@Autowired
	JdbcTemplate jdbc;


	//user情報を登録
	@Override
	public int insertOne(User user) {

		String insertSQL = "insert into syogi.users(name, address, password) values(?,?,?)";

		System.out.println(user + "UserDaoJdbcImpl ");

		int insertNumber = jdbc.update(insertSQL, user.getUserName(), user.getUserAddress(),user.getPassword());

		System.out.println( "UserDaoJdbcImpl終わり ");

		return insertNumber;

	}

	//user情報をアドレスで取得
	@Override
	public Map<String, Object> selectOne(String address) {

		String selectSQL = "select * from syogi.users where address = ?";

		Map<String, Object> map = jdbc.queryForMap(selectSQL, address);

		return map;
	}

	//user名をIDで取得
	@Override
	public Map<String, Object> selectOne(int userId){

		String selectManySQL = "select * from syogi.users where id = ? ";

		Map<String, Object> map = jdbc.queryForMap(selectManySQL,userId);

		return map;
	}
}

