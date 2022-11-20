package com.QuesSystem.ques.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.QuesSystem.ques.entity.Account;
import com.QuesSystem.ques.service.ifs.AccountService;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping(value = "/login", params = "login")
	public String login(Model model,
						RedirectAttributes redirectAttrs,
						HttpSession session,
						@RequestParam("account") String account,
						@RequestParam("password") String password) {

		Account accountInfo = accountService.getAccountInfo(account, password);
		if (accountInfo.getAccount() != null) {
			session.setAttribute("accountInfo", accountInfo);
			redirectAttrs.addFlashAttribute("alertMessage", "登入成功");
			
			return "redirect:/backListPage";
		}

		redirectAttrs.addFlashAttribute("alertMessage", "無此帳號, 請確認帳號是否輸入正確");
		
		return "redirect:/login";      // 密碼錯誤, 帳號錯誤(密碼對)防呆
	}

	@GetMapping(value = "/backListPage", params = "logout")
	public String logout(HttpSession session,
						 RedirectAttributes redirectAttrs) {
		session.removeAttribute("accountInfo");
		redirectAttrs.addFlashAttribute("alertMessage", "登出成功");
		return "redirect:/login";
	}

}
