package com.QuesSystem.ques.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.QuesSystem.ques.entity.OftenUseQuestion;
import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.repository.OftenUseQuestionDao;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.service.ifs.QuestionService;
import com.QuesSystem.ques.service.ifs.QuestionnaireService;
import com.google.gson.Gson;

@Controller
public class QuestionnaireController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private QuestionnaireService questionnaireService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionnaireDao questionnaireDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private OftenUseQuestionDao oftenUseQuestionDao;

	/*
	 * 新增問卷
	 */
	@PostMapping(value = "/backAddquestionnaire", params = "createq")
	public String createQuestionnaire(Model model,
									  HttpSession session,
									  @RequestParam("title") String questionnaireTitle,
									  @RequestParam("body") String questionnaireBody,
									  @RequestParam("startDate") String startDate,
									  @RequestParam("endDate") String endDate,
									  @RequestParam(name = "state", defaultValue = "0") boolean questionnaireStates) throws ParseException {

		// 新增問卷(使用ErrorMsg方法)
		String errorMsg = questionnaireService.ErrorMsg(questionnaireTitle, questionnaireBody, startDate, endDate);
		if (!errorMsg.isEmpty()) {
			model.addAttribute("errorMsg", errorMsg);

			return "backAddquestionnaire";
		}

		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setQuestionnaireTitle(questionnaireTitle);
		questionnaire.setQuestionnaireBody(questionnaireBody);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = formatter.parse(startDate);
		questionnaire.setStartDate(sDate);
		Date eDate = formatter.parse(endDate);
		questionnaire.setEndDate(eDate);
		questionnaire.setQuestionnaireStates(questionnaireStates);

		// 先裝進session，供下一個頁面使用
		session.setAttribute("questionnaire", questionnaire);

		List<OftenUseQuestion> oftenuseList = oftenUseQuestionDao.findAll();
		model.addAttribute("OftenUseQues", oftenuseList);

		return "backAddquestionnaire";
	}

	@ResponseBody
	@GetMapping(value = { "/ViewOftenUseQuestion/{Id}" })
	public String ViewOftenUseQuestion(Model model, @PathVariable("Id") String Id) {

		Gson gson = new Gson();
		Optional<OftenUseQuestion> oftenusequestion = oftenUseQuestionDao.findById(Id);

		if (!oftenusequestion.isEmpty()) {
			return gson.toJson(oftenusequestion.get());
		}

		return "notThing";
	}

	/*
	 * 修改問卷
	 */
	@PostMapping(value = { "/backAddquestionnaire" }, params = "change")
	public String changeQuestionnaire(Model model,
									  HttpSession session,
									  RedirectAttributes redirectAttrs,
									  @RequestParam(name = "ID", required = false) String questionnaireId,
									  @RequestParam("title") String questionnaireTitle,
									  @RequestParam("body") String questionnaireBody,
									  @RequestParam("startDate") String startDate,
									  @RequestParam("endDate") String endDate,
									  @RequestParam(name = "state", defaultValue = "0") boolean questionnaireStates) throws ParseException {

		// 取得問卷的Session
		Questionnaire changeQues = (Questionnaire) session.getAttribute("changeques");
		// 取得QuestionnaireId(問卷ID)
		String questionnaireID = changeQues.getQuestionnaireId();

		String errorMsg = questionnaireService.ErrorMsg(questionnaireTitle, questionnaireBody, startDate, endDate);
		
		if (!errorMsg.isEmpty()) {
			model.addAttribute("errorMsg", errorMsg);
			return "redirect:/backAddquestionnaire?ID=" + questionnaireID;
		}
		// 設定問卷標題
		changeQues.setQuestionnaireTitle(questionnaireTitle);
		// 設定問卷內容
		changeQues.setQuestionnaireBody(questionnaireBody);
		// 時間格式轉換
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = formatter.parse(startDate);
		// 設定問卷起始日期
		changeQues.setStartDate(sDate);
		Date eDate = formatter.parse(endDate);
		// 設定問卷結束日期
		changeQues.setEndDate(eDate);
		// 設定問卷狀態
		changeQues.setQuestionnaireStates(questionnaireStates);

		// 把問卷資料存入Session
		session.setAttribute("questionnaire", changeQues);

		return "redirect:/backAddquestionnaire?questionnaireId=" + questionnaireID;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/backAddquestionnaire" }, params = "createquestion")
	public String createQues(Model model, HttpSession session, RedirectAttributes redirectAttrs) {

		Questionnaire questionnaire = (Questionnaire)session.getAttribute("questionnaire");
		if (questionnaire == null) {
			redirectAttrs.addFlashAttribute("quesErrorMsg", "*尚未填寫問卷*");
			return "redirect:/backAddquestionnaire";
		}
		List<Question> questionList = (List<Question>)session.getAttribute("questions");
		boolean getMust = questionService.QuestionMust(questionList);

		if (getMust == false) {
			model.addAttribute("quesErrorMsg", "至少要有一個必填項目");
			List<OftenUseQuestion> oftenList = oftenUseQuestionDao.findAll();
			model.addAttribute("oftenUseQues", oftenList);

			return "backAddquestionnaire";
		}

		questionnaireDao.save(questionnaire);
		questionDao.saveAll(questionList);

		redirectAttrs.addFlashAttribute("quesErrorMsg", "問卷建立成功");
		return "redirect:/backListPage";
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/backAddquestionnaire" }, params = "editquestion")
	public String editQues(Model model, HttpSession session, RedirectAttributes redirectAttrs) {

		Questionnaire changequesId = (Questionnaire)session.getAttribute("changeques");
		String changequestionnaireId = changequesId.getQuestionnaireId();
		Questionnaire changeques = (Questionnaire)session.getAttribute("questionnaire");
		if (changeques == null) {
			redirectAttrs.addFlashAttribute("quesErrorMsg", "*尚未填寫問卷*");
			return "redirect:/backAddquestionnaire?ID=" + changequestionnaireId;
		}
		String quesId = changeques.getQuestionnaireId();

		List<Question> questionList = (List<Question>)session.getAttribute("questions");
		boolean getMust = questionService.QuestionMust(questionList);

		if (getMust == false) {
			model.addAttribute("quesErrorMsg", "至少要有一個必填項目");
			List<OftenUseQuestion> oftenList = oftenUseQuestionDao.findAll();
			model.addAttribute("oftenUseQues", oftenList);
			return "redirect:/backAddquestionnaire?ID=" + quesId;
		}

		questionnaireDao.save(changeques);
		for (Question question : questionList) {
			// 取得QuestionId
			String Id = question.getQuestionId();
			Optional<Question> questionOp = questionDao.findById(Id);

			// 如果questionOp是空的, 則儲存問題
			if (questionOp.isEmpty()) {
				questionDao.save(question);
			}
			// 若不是則取得Id
			else {
				Question editQues = questionOp.get();
				editQues.setQuestionTitle(question.getQuestionTitle());
				editQues.setQuestionChoices(question.getQuestionChoices());
				editQues.setQuestionType(question.getQuestionType());
				editQues.setMustKeyin(question.isMustKeyin());
				questionDao.save(editQues);
			}
		}

		redirectAttrs.addFlashAttribute("quesErrorMsg", "問卷建立成功");
		return "redirect:/backListPage";
	}
}
