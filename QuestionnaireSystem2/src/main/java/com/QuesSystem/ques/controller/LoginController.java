package com.QuesSystem.ques.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.QuesSystem.ques.constant.UrlPath;

@Controller
public class LoginController {

	// 登入頁面顯示
	@GetMapping(UrlPath.Path.URL_FRONT_LOGIN)
	public String login(Model model) {
		return UrlPath.Path.URL_FRONT_LOGIN;
	}
}
