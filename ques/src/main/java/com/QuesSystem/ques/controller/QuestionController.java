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

import com.QuesSystem.ques.entity.OftenUseQuestion;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.repository.AnswerDao;
import com.QuesSystem.ques.repository.OftenUseQuestionDao;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.service.ifs.QuestionService;

@Controller
public class QuestionController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private QuestionnaireDao questionnaireDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private OftenUseQuestionDao oftenUseQuestionDao;

	@Autowired
	private QuestionService questionService;

	@PostMapping(value = { "/backAddquestionnaire" }, params = "add")
	public String createQuestion(Model model, HttpSession session, RedirectAttributes redirectAttrs,
			@RequestParam("questionTitle") String questionTitle,
			@RequestParam("questionChoices") String questionChoices, @RequestParam("questionType") int questionType,
			@RequestParam(name = "mustKeyin", defaultValue = "0") boolean mustKeyin) throws ParseException {

		Questionnaire changeQues = (Questionnaire) session.getAttribute("changeQues");
		if (changeQues != null) {
			// 取得問卷ID
			String questionnaireId = changeQues.getQuestionnaireId();

			Questionnaire questionnaire = (Questionnaire) session.getAttribute("questionnaire");

			if (questionnaire == null) {
				redirectAttrs.addFlashAttribute("quesErrorMsg", "請檢查是否有輸入問卷");
				return "redirect:/backAddquestionnaire?ID=" + questionnaireId;
			}

			// 新增問題防呆(使用ErrorMsg方法)
			String ErrorMsg = questionService.ErrorMsg(questionTitle, questionChoices, questionType);
			if (!ErrorMsg.isEmpty()) {
				model.addAttribute("ErrorMsg", ErrorMsg);
				return "backAddquestionnaire";
			}

			questionService.editQuestion(session, model, questionnaire, questionTitle, questionChoices, questionType,
					mustKeyin);

			return "redirect:/backAddquestionnaire?ID=" + questionnaireId;
		}

		Questionnaire questionnaire = (Questionnaire) session.getAttribute("questionnaire");
		if (questionnaire == null) {
			redirectAttrs.addFlashAttribute("quesErrorMsg", "請檢查是否有輸入問卷");
			return "redirect:/backAddquestionnaire";
		}

		String ErrorMsg = questionService.ErrorMsg(questionTitle, questionChoices, questionType);
		if (!ErrorMsg.isEmpty()) {
			model.addAttribute("ErrorMsg", ErrorMsg);
			return "backAddquestionnaire";
		}

		questionService.createQuestion(session, model, questionnaire, questionTitle, questionChoices, questionType,
				mustKeyin);

		return "backAddquestionnaire";
	}

	@PostMapping(value = { "/backAddquestionnaire" }, params = "remove")
	public String removeQuestion(Model model,
								 HttpSession session,
								 RedirectAttributes redirectAttrs,
								 @RequestParam(name = "index", required = false) Integer[] index,
								 @RequestParam(name = "Id", required = false) String[] id) {

		List<OftenUseQuestion> oftenuseList = oftenUseQuestionDao.findAll(); // ???
		model.addAttribute("oftenUseQues", oftenuseList);

		return null;
	}
}
