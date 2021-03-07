package com.example.demo.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.profile.domain.model.Profile;
import com.example.demo.profile.domain.service.ProfileService;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	ProfileService profileService;

	@Autowired
	HttpSession session;

	//login画面の表示
	@GetMapping("/login")
	public String getLogin(Model model) {

		return "/login";
	}

	//新規登録画面の表示
	@GetMapping("/loginInput")
	public String getLoginInput(@ModelAttribute User user, Model model) {

		return "/loginInput";
	}

	//user情報を登録
	@PostMapping("/loginInput")
	public String postLoginInput(@ModelAttribute @Validated User user,BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			return getLoginInput(user, model);
		}


		Profile profile = new Profile();

		//user情報登録
		userService.insertOne(user);

		//user情報取り出し
		User resultUser = userService.selectOne(user.getUserAddress());

		System.out.println(resultUser.getUserId());

		profile.setUser_id(resultUser.getUserId());

		session.setAttribute("profile", profile);

		return "/profileInput";
	}

	@GetMapping("/profileInput")
	public String getProfileInput(@ModelAttribute Profile profile, Model model) {

		return "/profileInput";
	}

	//profile情報を登録
	@PostMapping("/profileInput")
	public String postProfileLosin(@RequestParam("gender") String gender, @RequestParam("hobby") String hobby,
			@RequestParam("grade") String grade, @RequestParam("history") String history,
			@RequestParam("fav_battleType") String fav_battleType, @RequestParam("comment") String comment,
			Model model) {

		Profile profile = new Profile();

		Profile SessionProfile = (Profile)session.getAttribute("profile");


	    System.out.println(SessionProfile.getUser_id());
		profile.setUser_id(SessionProfile.getUser_id());
		profile.setGender(gender);
		profile.setComment(comment);
		profile.setFav_battleType(fav_battleType);
		profile.setHistory(history);
		profile.setHobby(hobby);
		profile.setGrade(grade);

		System.out.println(profile);

		//profile情報登録
		profileService.insertOne(profile);

		return "redirect:/login";
	}

	public int GetUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String address = auth.getName();

		System.out.println(address);

		User resultUser = userService.selectOne(address);

		int userId = resultUser.getUserId();

		return userId;
	}

}