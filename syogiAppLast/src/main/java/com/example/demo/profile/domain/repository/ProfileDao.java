package com.example.demo.profile.domain.repository;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.example.demo.profile.domain.model.Profile;

public interface ProfileDao{

	//profile情報登録
	public int insertOne(Profile profile) throws DataAccessException;

	//profile情報取得
	public Map<String, Object> selectOne(int userId) throws DataAccessException;

	//profileのアップデート
	public int updataOne(Profile profile) throws DataAccessException;

	//友達登録処理
	public int followOne(int followId,int followerId) throws DataAccessException;

	//友達情報取得
	public List<Map<String, Object>> selectMany(int userId) throws DataAccessException;
}