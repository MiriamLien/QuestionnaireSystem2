package com.QuesSystem.ques.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.QuesSystem.ques.constant.AlertMessage;
import com.QuesSystem.ques.constant.UrlPath;
import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.entity.Userinfo;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.service.ifs.QuestionService;
import com.QuesSystem.ques.service.ifs.UserinfoService;

@Controller
public class FormPageController {

	@Autowired
	private QuestionnaireDao questionnaireDao;

	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private QuestionService questionService;

	@Autowired
	private UserinfoService userInfoService;

	// 取得並顯示問卷及問題的資訊於前端
	@GetMapping(UrlPath.Path.URL_FRONT_FORM)
	public String formPage(Model model,
						   HttpSession session,
						   // 從URL中取得問卷ID(questionnaireId)
						   @RequestParam(name = "ID", required = false) String questionnaireId) {
		
		// 透過questionnaireId找出問卷
		Questionnaire questionnaire = questionnaireDao.findById(questionnaireId).get();
		// 找出問卷內的所有問題(清單)
		List<Question> questionList = questionDao.findListByQuestionnaireId(questionnaire);

		model.addAttribute("questionnaire", questionnaire);
		model.addAttribute("questionList", questionList);
		session.setAttribute("questionnaireInfo", questionnaire);
		session.removeAttribute("userinfo");

		return UrlPath.Path.URL_FRONT_FORM;
	}

	@PostMapping(value = { "/formPage" })
	public String createAnswer(HttpSession session,
							   RedirectAttributes redirectAttrs,	
							   @ModelAttribute Userinfo userinfo,
							   @RequestParam("username") String name,
							   @RequestParam("userphone") String phone,
							   @RequestParam("useremail") String email,
							   @RequestParam("userage") String age) throws Exception {

		Questionnaire questionnaire = (Questionnaire) session.getAttribute("questionnaireInfo");
		String questionnaireId = questionnaire.getQuestionnaireId();
		String userId = UUID.randomUUID().toString();

		if (questionnaireId == null) {
			redirectAttrs.addFlashAttribute("alertMessage", AlertMessage.QuestionnaireMsg.QuestionnaireId_Is_Null);
			return UrlPath.Path.URL_FRONT_LISTPAGE;
		}

		String errorMsg = userInfoService.ErrorMsg(name, phone, email, age);
		if(!errorMsg.isEmpty()) {
			
			redirectAttrs.addFlashAttribute("errorMsg", errorMsg);
			return "redirect:/formPage?ID=" + questionnaireId;
		}
		
		// 設定填寫者姓名(刪除字串頭尾的空格/白)
		userinfo.setName(name.trim());
		// 設定填寫者電話
		userinfo.setPhone(phone.trim());
		// 設定填寫者電子信箱
		userinfo.setEmail(email.trim());
		// 設定填寫者年齡
		userinfo.setAge(age.trim());
		// 設定使用者ID
		userinfo.setUserId(userId);
		// 設定問卷ID
		userinfo.setQuestionnaireId(questionnaire);
		// 把填寫資訊存入Session
		session.setAttribute("userinfo", userinfo);

		// 跳轉至(URL帶有問卷ID的)確認頁面
		return "redirect:/confirmPage?ID=" + questionnaireId;
	}
	
	@ResponseBody
	@GetMapping(value = { "/loadQuestions/{questionnaireId}" })
	public String loadQuestions(Model model,
						  		@PathVariable("questionnaireId") Questionnaire questionnaireId) {
		
		return questionService.questionToJson(questionnaireId);
	}
}
