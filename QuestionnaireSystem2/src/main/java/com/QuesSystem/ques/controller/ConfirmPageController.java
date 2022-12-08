package com.QuesSystem.ques.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.QuesSystem.ques.model.Answers;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.repository.UserinfoDao;
import com.QuesSystem.ques.service.ifs.QuestionService;
import com.google.gson.Gson;

@Controller
public class ConfirmPageController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuestionnaireDao questionnaireDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private UserinfoDao userinfoDao;

	@ResponseBody
	@GetMapping(value = { "/getQuestions/{questionnaireId}" })
	public String getQuestions(Model model, 
			                   @PathVariable("questionnaireId") Questionnaire questionnaireId) {
		
		return questionService.questionToJson(questionnaireId);
	}
	
	// 前台確認頁顯示
	@GetMapping(UrlPath.Path.URL_FRONT_CONFIRM)
	public String confirmPage(Model model,
							  // 透過URL取得questionnaireId
							  @RequestParam(name = "ID", required = false) String questionnaireId,
							  HttpSession session,
							  HttpServletRequest request) {

		// 透過questionnaireId找出問卷
		Questionnaire questionnaire = questionnaireDao.findById(questionnaireId).get();
		// 找出問卷內的所有問題(清單)
		List<Question> questionList = questionDao.findListByQuestionnaireId(questionnaire);

		// 前端印出問卷資料
		model.addAttribute("questionnaire", questionnaire);
		// 前端印出所有問題資料
		model.addAttribute("questionList", questionList);
		
		// 跳轉至前台確認頁面
		return UrlPath.Path.URL_FRONT_CONFIRM;
	}

	@PostMapping(value = { "/confirmPage" })
	public String saveUserAnswers(Model model,
								  HttpSession session,
								  RedirectAttributes redirectAttrs,
								  HttpServletRequest request) throws UnsupportedEncodingException {

		Gson gson = new Gson();
		// 從Session取得問卷回答資料
		Userinfo sessionUserinfo = (Userinfo) session.getAttribute("userinfo");
		// 取得問卷ID
		Questionnaire questionnaireId = sessionUserinfo.getQuestionnaireId();
		
		// 藉由問卷ID取得問題清單
		List<Question> questionList = questionDao.findListByQuestionnaireId(questionnaireId);
		
		List<Question> newQuestionList = new ArrayList<>();
		// 把必填問題放入清單內(newQuestionList)
		for (Question question : questionList) {
			if (question.isMustKeyin() == true) {		// 1表示true，0表示false
				newQuestionList.add(question);
			}
		}
		
		model.addAttribute("newQuestionList", newQuestionList);
		
		
		 // 取得所有cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				// 如果有符合名字的cookie
				if (cookie.getName().equals("ansInfo")) {
					// 解碼
					String answer = URLDecoder.decode(cookie.getValue(), "UTF-8");
					
					List<Answers> targetList = new ArrayList<Answers>();
					
					Answers[] ans;
					ans = gson.fromJson(answer, Answers[].class);
					for (Answers a : ans) {
						targetList.add(a);
					}
					
					Integer targetId = null;
					//判斷必填問題是否有填值
					for (Question ques : newQuestionList) {
						
						targetId = ques.getQuestionNumber();
						boolean correct = false;
						for (Answers item : targetList) {
							
							if (newQuestionList.size() > targetList.size()) {
								break;
							}
							
							if ((targetId + "").equals(item.getQuestionNumber())) {
								correct = true;
								break;
							}
						}
						
						if (!correct) {
							
							redirectAttrs.addFlashAttribute("alertMessage", AlertMessage.ConfirmPageMsg.Check_Must_Keyin);
							return "redirect:/formPage?ID=" + questionnaireId;
						}
					}
					// 加入答案
					sessionUserinfo.setAnswer(answer);
					// 儲存
					userinfoDao.save(sessionUserinfo);
				}
			}
		}
		
		redirectAttrs.addFlashAttribute("alertMessage", AlertMessage.ConfirmPageMsg.Answer_Finish);
		return "redirect:/listPage";
	}
}
