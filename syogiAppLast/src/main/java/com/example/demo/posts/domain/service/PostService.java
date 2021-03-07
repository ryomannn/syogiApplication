package com.example.demo.posts.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.controller.UserController;
import com.example.demo.posts.domain.model.Post;
import com.example.demo.posts.domain.repository.PostDao;
import com.example.demo.profile.domain.model.Page;

@Service
public class PostService{

	@Autowired
	PostDao dao;

	@Autowired
	UserController userController;

	@Autowired
	HttpSession session;

	//投稿情報登録
	public boolean insertOne(Post post) {

		int userId = userController.GetUserId();

		post.setUserId(userId);

		int insertNum = dao.insertOne(post);

		if(insertNum > 0) {
			System.out.println("Post情報insert成功");
			return true;
		}
		else {
			System.out.println("Post情報insert失敗");
			return false;
		}

	}

	//自分の投稿情報取得
	public List<Post> mySelectMany() {

		int userId = userController.GetUserId();

		List<Post> postList = new ArrayList<Post>();

		List<Map<String, Object>> list = dao.mySelectMany(userId);

		for(Map<String, Object> map : list) {

			Post post = new Post();

			post.setUserId((int)map.get("user_id"));
			post.setTitle((String)map.get("title"));
			post.setContents((String)map.get("contents"));

			postList.add(post);
		}

		return postList;
	}

	//他の人の投稿情報取得
	public List<Post> otherSelectMany(int userId){

		List<Post> postList = new ArrayList<Post>();

		List<Map<String, Object>> list = dao.otherSelectMany(userId);

		for(Map<String, Object> map : list) {

			Post post = new Post();

			post.setUserId((int)map.get("user_id"));
			post.setTitle((String)map.get("title"));
			post.setContents((String)map.get("contents"));

			postList.add(post);
		}

		return postList;
	}

	//記事数取得
	public int Count() {

		int count = dao.Count();

		return count;
	}

	//ページブロック数取得
	public int pageBlockCount() {

		double list = 5;

		double count = dao.Count();

		if(count%5 == 0) {

			return (int)Math.floor(count/list);
		}
		else {

			return (int) (Math.floor(count/list) +1);
		}
	}

	//ページブロックlist作成
	public List<Integer> createList(int count){

		List<Integer> list = new ArrayList<Integer>();

		for(int i = 1; i <= count; i++) {

			list.add(i);

		};
		return list;
	}

	//表示する範囲だけのPostのListを作る
	public List<Post> createPostList(int i){

		int a = i-1;
		int size =Count();
		Page nowPage = (Page)session.getAttribute("nowPage");
		List<Post> list = mySelectMany();

		int page = list.size() % 5;

		if(size < 4) {
			return list;
		}
		else if(nowPage.getLastPage()){

			List<Post> postList = list.subList(a*5,a*5 + page );

			return postList;

		}
		else {



			List<Post>postList = list.subList(a*5, (a+1)*5);

			return postList;
		}
	}
}