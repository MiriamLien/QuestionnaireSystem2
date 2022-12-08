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
	
	// �e�x�T�{�����
	@GetMapping(UrlPath.Path.URL_FRONT_CONFIRM)
	public String confirmPage(Model model,
							  // �z�LURL���oquestionnaireId
							  @RequestParam(name = "ID", required = false) String questionnaireId,
							  HttpSession session,
							  HttpServletRequest request) {

		// �z�LquestionnaireId��X�ݨ�
		Questionnaire questionnaire = questionnaireDao.findById(questionnaireId).get();
		// ��X�ݨ������Ҧ����D(�M��)
		List<Question> questionList = questionDao.findListByQuestionnaireId(questionnaire);

		// �e�ݦL�X�ݨ����
		model.addAttribute("questionnaire", questionnaire);
		// �e�ݦL�X�Ҧ����D���
		model.addAttribute("questionList", questionList);
		
		// ����ܫe�x�T�{����
		return UrlPath.Path.URL_FRONT_CONFIRM;
	}

	@PostMapping(value = { "/confirmPage" })
	public String saveUserAnswers(Model model,
								  HttpSession session,
								  RedirectAttributes redirectAttrs,
								  HttpServletRequest request) throws UnsupportedEncodingException {

		Gson gson = new Gson();
		// �qSession���o�ݨ��^�����
		Userinfo sessionUserinfo = (Userinfo) session.getAttribute("userinfo");
		// ���o�ݨ�ID
		Questionnaire questionnaireId = sessionUserinfo.getQuestionnaireId();
		
		// �ǥѰݨ�ID���o���D�M��
		List<Question> questionList = questionDao.findListByQuestionnaireId(questionnaireId);
		
		List<Question> newQuestionList = new ArrayList<>();
		// �⥲����D��J�M�椺(newQuestionList)
		for (Question question : questionList) {
			if (question.isMustKeyin() == true) {		// 1���true�A0���false
				newQuestionList.add(question);
			}
		}
		
		model.addAttribute("newQuestionList", newQuestionList);
		
		
		 // ���o�Ҧ�cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				// �p�G���ŦX�W�r��cookie
				if (cookie.getName().equals("ansInfo")) {
					// �ѽX
					String answer = URLDecoder.decode(cookie.getValue(), "UTF-8");
					
					List<Answers> targetList = new ArrayList<Answers>();
					
					Answers[] ans;
					ans = gson.fromJson(answer, Answers[].class);
					for (Answers a : ans) {
						targetList.add(a);
					}
					
					Integer targetId = null;
					//�P�_������D�O�_�����
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
					// �[�J����
					sessionUserinfo.setAnswer(answer);
					// �x�s
					userinfoDao.save(sessionUserinfo);
				}
			}
		}
		
		redirectAttrs.addFlashAttribute("alertMessage", AlertMessage.ConfirmPageMsg.Answer_Finish);
		return "redirect:/listPage";
	}
}
