package com.example.demo.findUser.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.controller.UserController;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.UserId;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.profile.domain.model.Profile;
import com.example.demo.profile.domain.service.ProfileService;

@Controller
public class FindUserController{

	@Autowired
	UserService userService;

	@Autowired
	ProfileService profileService;

	@Autowired
	UserController userController;

	@Autowired
	HttpSession session;

	//ユーザー検索画面の表示
	@GetMapping("/findUser")
	public String getFindUser(@ModelAttribute("UserId")UserId userId, Model model) {
		return "/findUser";
	}

	//ユーザー検索処理
	@PostMapping("/findUser")
	public String postFindUser(@ModelAttribute("UserId")UserId userId, Model model) {

		Profile profile = new Profile();

		profile = profileService.selectOne(userId.getUserId());

		profile.setUser_id(userId.getUserId());

		session.setAttribute("profile", profile);

		model.addAttribute("result",profile);

		return getUserProfile(model);
	}

	//ユーザー検索されたプロフィール画面表示
	@GetMapping("/userProfile")
	public String getUserProfile(Model model) {

		return "/userProfile";
	}

	//友達登録処理
	@PostMapping("/userProfile")
	public String postUserProfile(Model model) {

		Profile profile = (Profile)session.getAttribute("profile");

		System.out.println(profile.getUser_id()+"友達追加される人");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String address = auth.getName();

		System.out.println(address);

		User resultUser = userService.selectOne(address);

		int userId = resultUser.getUserId();


		//今ログインしている人がユーザー検索で見つけたユーザーを友達追加する
		profileService.followtOne(userId, profile.getUser_id());

		model.addAttribute("result",profile);

		session.invalidate();


		return "/userProfile";
	}


}