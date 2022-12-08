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

	// ���o����ܰݨ��ΰ��D����T��e��
	@GetMapping(UrlPath.Path.URL_FRONT_FORM)
	public String formPage(Model model,
						   HttpSession session,
						   // �qURL�����o�ݨ�ID(questionnaireId)
						   @RequestParam(name = "ID", required = false) String questionnaireId) {
		
		// �z�LquestionnaireId��X�ݨ�
		Questionnaire questionnaire = questionnaireDao.findById(questionnaireId).get();
		// ��X�ݨ������Ҧ����D(�M��)
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
		
		// �]�w��g�̩m�W(�R���r���Y�����Ů�/��)
		userinfo.setName(name.trim());
		// �]�w��g�̹q��
		userinfo.setPhone(phone.trim());
		// �]�w��g�̹q�l�H�c
		userinfo.setEmail(email.trim());
		// �]�w��g�̦~��
		userinfo.setAge(age.trim());
		// �]�w�ϥΪ�ID
		userinfo.setUserId(userId);
		// �]�w�ݨ�ID
		userinfo.setQuestionnaireId(questionnaire);
		// ���g��T�s�JSession
		session.setAttribute("userinfo", userinfo);

		// �����(URL�a���ݨ�ID��)�T�{����
		return "redirect:/confirmPage?ID=" + questionnaireId;
	}
	
	@ResponseBody
	@GetMapping(value = { "/loadQuestions/{questionnaireId}" })
	public String loadQuestions(Model model,
						  		@PathVariable("questionnaireId") Questionnaire questionnaireId) {
		
		return questionService.questionToJson(questionnaireId);
	}
}
