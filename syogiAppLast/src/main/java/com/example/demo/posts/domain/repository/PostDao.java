package com.example.demo.posts.domain.repository;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.example.demo.posts.domain.model.Post;

public interface PostDao{

	//投稿情報登録
	public int insertOne(Post post)throws DataAccessException;

	//自分の投稿情報取得
	public List< Map<String, Object>> mySelectMany(int userId)throws DataAccessException;

	//他人の投稿履歴取得
	public List< Map<String, Object>> otherSelectMany(int userId)throws DataAccessException;

	//記事数取得
	public int Count()throws DataAccessException;
}