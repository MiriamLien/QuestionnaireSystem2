package com.QuesSystem.ques.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.QuesSystem.ques.constant.AlertMessage;
import com.QuesSystem.ques.constant.UrlPath;
import com.QuesSystem.ques.entity.Account;
import com.QuesSystem.ques.service.ifs.AccountService;

@Controller
public class LoginController {

	@Autowired
	private AccountService accountService;

	// 登入頁面顯示
	@GetMapping(UrlPath.Path.URL_FRONT_LOGIN)
	public String login(Model model) {
		
		return UrlPath.Path.URL_FRONT_LOGIN;
	}

	// 登入功能
	@PostMapping(value = { UrlPath.Path.URL_FRONT_LOGIN }, params = "login")
	public String loginPost(Model model,
							RedirectAttributes redirectAttrs,
							HttpSession session,
							@RequestParam("account") String account,
							@RequestParam("password") String password) {

		// 取得帳戶資訊
		Account accountInfo = accountService.getAccount(account, password);

		// 如果帳戶資訊內的帳號不是空值
		if (accountInfo.getAccount() != null) {
			
			// 設定Session
			session.setAttribute("accountInfo", accountInfo);
			// 給予提示訊息(登入成功)
			redirectAttrs.addFlashAttribute("alertMessage", AlertMessage.LoginMsg.Login_Success);

			// 回傳頁面，跳轉至後台列表頁
			return "redirect:/backListPage";
		}

		// 如果帳號是找不到(是空值)的話，則給予提示訊息(確認帳號密碼)
		redirectAttrs.addFlashAttribute("alertMessage", AlertMessage.LoginMsg.Check_Account_Password);
		
		// 回傳頁面，跳轉至登入頁面
		return "redirect:/login";
	}

	// 登出功能
	@GetMapping("/logout")
	public String logout(HttpSession session, 
						 RedirectAttributes redirectAttrs) {

		// 清除Session
		session.removeAttribute("accountInfo");
		// 給予提示訊息(登出成功)
		redirectAttrs.addFlashAttribute("alertMessage", AlertMessage.LoginMsg.Logout_Success);
		
		// 回傳頁面, 跳轉至登入頁面
		return "redirect:/login";
	}
}
