package com.QuesSystem.ques.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.QuesSystem.ques.constant.AlertMessage;
import com.QuesSystem.ques.constant.UrlPath;
import com.QuesSystem.ques.entity.OftenUseQuestion;
import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.repository.OftenUseQuestionDao;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.service.ifs.QuestionService;

@Controller
public class QuestionController implements Serializable {

	private static final long serialVersionUID = 1L;

	private int questionNumber = 0;
	
	@Autowired
	private QuestionnaireDao questionnaireDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private OftenUseQuestionDao oftenUseQuestionDao;

	@Autowired
	private QuestionService questionService;

	// 新增問題
	@PostMapping(value = { "/backAddquestionnaire" }, params = "add")
	public String createQuestion(Model model,
								 HttpSession session,
								 RedirectAttributes redirectAttrs,
								 @RequestParam("questionTitle") String questionTitle,
								 @RequestParam("questionChoices") String questionChoices,
								 @RequestParam("questionType") int questionType,
								 @RequestParam(name = "mustKeyin", defaultValue = "0") boolean mustKeyin) throws ParseException {

		// 取得Session
		Questionnaire changeques = (Questionnaire) session.getAttribute("changeques");
		// 如果Session不為空值的話
		if (changeques != null) {
			// 取得問卷ID
			String quesId = changeques.getQuestionnaireId();

			// 取得放有問卷資訊的Session
			Questionnaire questionnaire = (Questionnaire) session.getAttribute("questionnaire");

			// 如果Session為空值的話
			if (questionnaire == null) {
				
				// 給予提示訊息，並跳轉至(Url帶有問卷ID的)新增問卷頁面
				redirectAttrs.addFlashAttribute("quesErrorMsg", AlertMessage.QuestionnaireMsg.Check_Enter_Questionnaire);
				return "redirect:/backAddquestionnaire?ID=" + quesId;
			}

			// 新增問題防呆(使用ErrorMsg方法)
			String questionErrorMsg = questionService.ErrorMsg(questionTitle, questionChoices, questionType);
			// 如果ErrorMsg不為空的話
			if (!questionErrorMsg.isEmpty()) {
				
				// 把ErrorMsg顯示於前端畫面中
				model.addAttribute("questionErrorMsg", questionErrorMsg);
				// 跳轉至後台新增問卷頁面
				return UrlPath.Path.URL_BACK_ADDQUESTIONNAIRE;
			}

			// 使用帶有參數的方法進行編輯功能
			questionService.editQuestion(session, model, questionnaire, questionTitle, questionChoices, questionType, mustKeyin);
			// 跳轉至(Url帶有問卷ID的)後台新增問卷頁面
			return "redirect:/backAddquestionnaire?ID=" + quesId;
		}

		Questionnaire questionnaire = (Questionnaire) session.getAttribute("questionnaire");
		// 如果Session為空值的話
		if (questionnaire == null) {
			
			// 給予提示訊息，並跳轉至後台新增問卷頁面
			redirectAttrs.addFlashAttribute("quesErrorMsg", AlertMessage.QuestionnaireMsg.Check_Enter_Questionnaire);
			return "redirect:/backAddquestionnaire";
		}

		// 編輯問題防呆(使用ErrorMsg方法)
		String questionErrorMsg = questionService.ErrorMsg(questionTitle, questionChoices, questionType);
		if (!questionErrorMsg.isEmpty()) {
			
			model.addAttribute("questionErrorMsg", questionErrorMsg);
			return UrlPath.Path.URL_BACK_ADDQUESTIONNAIRE;
		}

		questionService.createQuestion(session, model, questionnaire, questionTitle, questionChoices, questionType, mustKeyin);

		return UrlPath.Path.URL_BACK_ADDQUESTIONNAIRE;
	}

	@PostMapping(value = { "/backAddquestionnaire" }, params = "remove")
	public String removeQuestion(Model model,
								 HttpSession session,
								 RedirectAttributes redirectAttrs,
								 @RequestParam(name = "index", required = false) Integer[] index,
								 @RequestParam(name = "Id", required = false) String[] id) {

		List<OftenUseQuestion> oftenuseList = oftenUseQuestionDao.findAll();
		model.addAttribute("oftenUseQues", oftenuseList);

		return null;
	}
	
	@PostMapping(value = "/backAddquestionnaire", params = "editQuestion")
	public String editQuestion(Model model,
			                   HttpSession session,
			                   RedirectAttributes redirectAttrs,
			                   Questionnaire quesId,
			                   @RequestParam("questionTitle") String questionTitle,
			                   @RequestParam("questionChoices") String questionChoices, 
			                   @RequestParam("questionType") int questionType,			                   
			                   @RequestParam("editQuestion") int count,
			                   @RequestParam(name = "mustKeyin", defaultValue = "0") boolean mustKeyin) {
								
		Questionnaire changeques = (Questionnaire) session.getAttribute("changeques");
		String questionnaireId = changeques.getQuestionnaireId();
		Questionnaire questionnaire = (Questionnaire) session.getAttribute("questionnaire");
		
		if(questionnaire == null) {
			redirectAttrs.addAttribute("quesErrorMsg", AlertMessage.QuestionnaireMsg.Check_Enter_Questionnaire);
				return "redirect:/backAddquestionnaire?ID=" + questionnaireId;
		}
		
		String ErrorMsg = questionService.ErrorMsg(questionTitle, questionChoices, questionType);
		if (!ErrorMsg.isEmpty()) {
			model.addAttribute("ErrorMsg", ErrorMsg);
			return "redirect:/backAddquestionnaire?ID=" + questionnaireId;
		}
		
		@SuppressWarnings("unchecked")
		List<Question> quesList = (List<Question>) session.getAttribute("questions");
		Question ques = quesList.get(count);
		// 設定問題標題
		ques.setQuestionTitle(questionTitle);
		// 設定問題回答
		ques.setQuestionChoices(questionChoices);
		// 設定問題種類
		ques.setQuestionType(questionType);
		// 設定是否為必須回答
		ques.setMustKeyin(mustKeyin);
		// 設定問題編號
		ques.setQuestionNumber(questionNumber);
		// 設定問卷編號
		ques.setQuestionnaireId(quesId);
		
		return "redirect:/backAddquestionnaire?ID=" + questionnaireId;
		
	}
}
