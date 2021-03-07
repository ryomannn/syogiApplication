package com.example.demo.friendList.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.UserId;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.profile.controller.ProfileController;
import com.example.demo.profile.domain.model.Profile;
import com.example.demo.profile.domain.service.ProfileService;


@Controller
public class FriendListController{

	@Autowired
	ProfileService profileService;

	@Autowired
	UserService userService;

	@Autowired
	ProfileController profileController;

	@GetMapping("/findUserList")
	public String getFindUserList(Model model) {

		List<User> userList = new ArrayList<User>();

		List<UserId> friendList = profileService.selectMany();

		for(UserId userId : friendList) {

			User friend = new User();

			friend.setUserId(userId.getUserId());

			User user = userService.selectOne(userId.getUserId());

			friend.setUserName(user.getUserName());

			userList.add(friend);
		}
		model.addAttribute("userLists", userList);

		return "/friendList";
	}

	@GetMapping("/friendProfile/{id}")
	public String getFriendProfile(@PathVariable("id")int userId, Model model) {

		Profile profile = new Profile();

		profile = profileService.selectOne(userId);

		model.addAttribute("result",profile);

		return profileController.getProfile(model);
	}

}