package com.example.demo.chooseBattleType.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BattleTypeController{
	@GetMapping("battleType")
	public void getBatleType(HttpServletResponse httpServletResponse,Model model) {

		String projectUrl = "http://localhost:3000";


	    httpServletResponse.setHeader("Location", projectUrl);
	    httpServletResponse.setStatus(302);

	}


}