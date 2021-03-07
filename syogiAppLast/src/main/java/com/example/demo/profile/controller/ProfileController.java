
package com.example.demo.profile.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.login.controller.UserController;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.profile.domain.model.Profile;
import com.example.demo.profile.domain.service.ProfileService;




@Controller
public class ProfileController{

	@Autowired
	ProfileService profileService;

	@Autowired
	UserService userService;

	@Autowired
	UserController userController;

	//プロフィール画面の表示
	@GetMapping("/profile")
	public String getProfile(Model model) {

		Profile result = new Profile();


		int userId = userController.GetUserId();

		//profile情報取得
		result = profileService.selectOne(userId);

		model.addAttribute("result", result);

		return "/profile";
	}

	//プロフィール更新画面の表示
	@GetMapping("/profileUpdate")
	public String getProfileInput(@ModelAttribute Profile profile,Model model) {

		return "/profileUpdate";
	}

	//入力されたプロフィール情報をDBに登録
	@PostMapping("/profileUpdate")
	public String postProfileInput(@ModelAttribute Profile profile, Model model) {

		int userId = userController.GetUserId();

		profile.setUser_id(userId);

		profileService.updateOne(profile);

		return getProfile(model);

	}

	//画像ファイルアップロード
	@PostMapping("/upload")
	public String postUpload(@ModelAttribute Profile profile, @RequestParam("upload")MultipartFile multipartFile, Model model) {

		String userId =String.valueOf(userController.GetUserId());


		if(!multipartFile.isEmpty()) {
			try {
				//ファイル名をユーザーID.jpgに変更
				File oldFileName = new File(multipartFile.getOriginalFilename());
				File newFileName = new File("image/" + userId + ".jpg");
				oldFileName.renameTo(newFileName);

				byte[] bytes = multipartFile.getBytes();

				//指定ファイル
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(oldFileName));

				stream.write(bytes);
				stream.close();
				System.out.println("ファイルアップロード成功");



			}catch(Exception e){

				System.out.println("読み込みエラー");
			}
			return getProfile(model);

		}
		else {
			System.out.println("画像ファイルをアップロードできませんでした");
			return getProfile(model);

		}
	}

}