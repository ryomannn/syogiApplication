package com.example.demo.profile.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.controller.UserController;
import com.example.demo.login.domain.model.UserId;
import com.example.demo.profile.domain.model.Profile;
import com.example.demo.profile.domain.repository.ProfileDao;

@Service
public class ProfileService{

	@Autowired
	ProfileDao dao;

	@Autowired
	UserController userController;

	//profile情報登録
	public boolean insertOne(Profile profile) {
		int insertNumber = dao.insertOne(profile);

		if(insertNumber > 0) {
			System.out.println("Profileのinsertが成功しました");
			return true;
		}
		else {
			System.out.println("Profileのinsertが失敗しました");
			return false;
		}
	}

	//profile情報取得
	public Profile selectOne(int userId) {

		Profile profile = new Profile();

		Map<String, Object> map = dao.selectOne(userId);

		profile.setUser_id((int)map.get("user_id"));
		profile.setGender((String)map.get("gender"));
		profile.setHobby((String)map.get("hobby"));
		profile.setGrade((String)map.get("grade"));
		profile.setHistory((String)map.get("history"));
		profile.setFav_battleType((String)map.get("fav_battleType"));
		profile.setComment((String)map.get("comment"));

		return profile;
	}

	//profileのアップデート
	public boolean updateOne(Profile profile){
		int updateNumber = dao.updataOne(profile);

		if(updateNumber > 0) {
			System.out.println("Profileのupdateが成功しました");
			return true;
		}
		else {
			System.out.println("Profileのupdateが失敗しました");
			return false;
		}
	}

	//友達登録処理結果
	public boolean followtOne(int followId, int followerId) {

		int insertNumber = dao.followOne(followId, followerId);

		if(insertNumber > 0) {
			System.out.println("insert成功　friend");
			return true;
		}
		else {
			System.out.println("insert失敗 friend");
			return false;
		}
	}

	//友達情報取得
	public List<UserId>selectMany(){



		int userId = userController.GetUserId();

		List<Map<String, Object>> list = dao.selectMany(userId);

		List<UserId> friendList = new ArrayList<UserId>();

		for(Map<String, Object> map : list) {

			UserId friendId = new UserId();

			friendId.setUserId((int)map.get("follower_id"));

			friendList.add(friendId);
		}

		return friendList;

	}


}