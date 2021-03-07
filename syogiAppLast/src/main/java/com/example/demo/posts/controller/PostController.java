package com.example.demo.posts.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.controller.UserController;
import com.example.demo.posts.domain.model.Post;
import com.example.demo.posts.domain.service.PostService;
import com.example.demo.profile.domain.model.Page;


@Controller
public class PostController{

	@Autowired
	PostService postService;

	@Autowired
	UserController userController;

	@Autowired
	HttpSession session;


	//投稿履歴画面表示（最初）
	@GetMapping("/post")
	public String getPost(Model model) {

		session.invalidate();

		Page nowPage = new Page(1,true,false);

		session.setAttribute("nowPage", nowPage);

		List<Integer> blockList = postService.createList(postService.pageBlockCount());

		List<Post> postList = postService.createPostList(nowPage.getPage());

		model.addAttribute("postLists", postList);
		model.addAttribute("blockLists", blockList);
		model.addAttribute("page", nowPage);

		return "/post";

	}

	//ページングによる画面表示
	@GetMapping("/page/{i}")
	public String getPage(@PathVariable("i")int page, Model model) {

		Page nowPage =(Page)session.getAttribute("nowPage");

		nowPage.isFirstPage(page);
		nowPage.setPage(page);

		nowPage.isLastPage(page, postService.pageBlockCount());

		session.setAttribute("nowPage", nowPage);

		List<Integer> blockList = postService.createList(postService.pageBlockCount());

		List<Post> postList = postService.createPostList(nowPage.getPage());

		model.addAttribute("postLists", postList);
		model.addAttribute("blockLists", blockList);
		model.addAttribute("page", nowPage);


		return "/post";

	}

	//ページング(前)
	@GetMapping("/previous")
	public String getPreviousPage(Model model) {

		Page nowPage =(Page)session.getAttribute("nowPage");

		int lastPage = postService.pageBlockCount();

		int latestPage = nowPage.getPage() - 1;

		if(latestPage == 1) {
			nowPage.setPage(latestPage);

			return getPost(model);
		}
		else if(nowPage.getPage() == lastPage ){

			nowPage.setLastPage(false);

			nowPage.setPage(latestPage);

			session.setAttribute("nowPage", nowPage);

			List<Integer> blockList = postService.createList(postService.pageBlockCount());

			List<Post> postList = postService.createPostList(nowPage.getPage());

			model.addAttribute("postLists", postList);
			model.addAttribute("blockLists", blockList);
			model.addAttribute("page", nowPage);

			return "/post";

		}
		else {

			nowPage.setPage(latestPage);

			session.setAttribute("nowPage", nowPage);

			List<Integer> blockList = postService.createList(postService.pageBlockCount());

			List<Post> postList = postService.createPostList(nowPage.getPage());

			model.addAttribute("postLists", postList);
			model.addAttribute("blockLists", blockList);
			model.addAttribute("page", nowPage);

			return "/post";
		}
	}

	//ページング(次)
	@GetMapping("/next")
	public String getNextPage(Model model) {

		Page nowPage =(Page)session.getAttribute("nowPage");

		int firstPage = 1;

		int lastPage = postService.pageBlockCount();

		int latestPage = nowPage.getPage() + 1;

		if(latestPage == lastPage) {

			nowPage.setPage(latestPage);

			return getLastPage(model);

		}

		else if(nowPage.getPage() == firstPage) {

			nowPage.setPage(latestPage);

			nowPage.setFirstPage(false);

			session.setAttribute("nowPage", nowPage);

			List<Integer> blockList = postService.createList(postService.pageBlockCount());

			List<Post> postList = postService.createPostList(nowPage.getPage());

			model.addAttribute("postLists", postList);
			model.addAttribute("blockLists", blockList);
			model.addAttribute("page", nowPage);

			return "/post";
		}
		else {

			nowPage.setPage(latestPage);

			session.setAttribute("nowPage", nowPage);

			List<Integer> blockList = postService.createList(postService.pageBlockCount());

			List<Post> postList = postService.createPostList(nowPage.getPage());

			model.addAttribute("postLists", postList);
			model.addAttribute("blockLists", blockList);
			model.addAttribute("page", nowPage);

			return "/post";

		}
	}

	//ページング(ラストページ)
	@GetMapping("/last")
	public String getLastPage(Model model) {

		int lastPage = postService.pageBlockCount();

		Page nowPage =(Page)session.getAttribute("nowPage");

		nowPage.setPage(lastPage);
		nowPage.isFirstPage(lastPage);
		nowPage.isLastPage(lastPage, lastPage);

		session.setAttribute("nowPage", nowPage);

		List<Integer> blockList = postService.createList(postService.pageBlockCount());

		List<Post> postList = postService.createPostList(nowPage.getPage());

		model.addAttribute("postLists", postList);
		model.addAttribute("blockLists", blockList);
		model.addAttribute("page", nowPage);

		return "/post";

	}

	//新規投稿画面の表示
	@GetMapping("/postInput")
	public String getPostInput( Model model) {

		model.addAttribute("post",new Post());

		return "/postInput";
	}

	//新規投稿内容の登録
	@PostMapping("/postInput")
	public String postPostInput(@ModelAttribute Post post, Model model) {

		postService.insertOne(post);

		return getPost(model);
	}





}