package com.example.demo.posts.domain.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.posts.domain.model.Post;
import com.example.demo.posts.domain.repository.PostDao;

@Repository
public class PostDaoJdbcInpl implements PostDao{

	@Autowired
	JdbcTemplate jdbc;

	//投稿情報登録
	@Override
	public int insertOne(Post post) {

		String insertSQL = "insert into syogi.posts(user_id, title, contents) values(?,?,?)";

		int insertNum = jdbc.update(insertSQL, post.getUserId(), post.getTitle(), post.getContents());

		return insertNum;
	}

	//自分の投稿履歴取得
	public List<Map<String, Object>> mySelectMany(int userId){

		String selectSQL = "select * from syogi.posts where user_id = ?";

		List<Map<String, Object>> list = jdbc.queryForList(selectSQL, userId);

		return list;
	}


	//他の人の投稿情報取得
	@Override
	public List<Map<String, Object>> otherSelectMany(int userId){

		String selectSQL = "select * syogi.posts where user_id = ?";

		List<Map<String, Object>> list = jdbc.queryForList(selectSQL, userId);

		return list;
	}

	//投稿数取得
	@Override
	public int Count() {

		String countSQL = "select count(*) from syogi.posts";

		int count = jdbc.queryForObject(countSQL,Integer.class);

		return count;
	}



}