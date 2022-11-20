package com.QuesSystem.ques.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.repository.UserinfoDao;
import com.QuesSystem.ques.service.ifs.QuestionService;
import com.QuesSystem.ques.service.ifs.QuestionnaireService;
import com.QuesSystem.ques.service.ifs.UserinfoService;

@Controller
public class BackListPageController {

	@Autowired
	private QuestionnaireService questionnaireService;

	@Autowired
	private QuestionnaireDao questionnaireDao;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private UserinfoService userinfoService;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private UserinfoDao userinfoDao;

	@GetMapping(value = { "/backListPage" }, params = "search")
	public String backListSearchKey(Model model,
									RedirectAttributes redirectAttrs,
									@RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
									@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
									@RequestParam(name = "title", required = false, defaultValue = "") String questionnaireTitle,
									@RequestParam(name = "startDate", required = false, defaultValue = "") String startDate,
									@RequestParam(name = "endDate", required = false, defaultValue = "") String endDate) throws ParseException {

		if (questionnaireTitle.isEmpty() && startDate.isEmpty() && endDate.isEmpty()) {
			redirectAttrs.addFlashAttribute("WrongMessage", "尚未輸入任何條件");
			return "redirect:/backListPage";
		} else if (questionnaireTitle.length() < 2) {
			redirectAttrs.addFlashAttribute("WrongMessage", "尚未輸入任何條件");
			return "redirect:/backListPage";
		}

		Page<Questionnaire> questionnaires = null;

		if (startDate.isEmpty() && endDate.isEmpty()) {
			questionnaires = questionnaireService.searchByQuestionnaireTitle(pageNum, pageSize, questionnaireTitle);
		} else if (!startDate.isEmpty() && !endDate.isEmpty()) {
			// questionnaires = questionnaireService.searchQuestionnaireByAllTime(pageNum, pageSize, null, null);
		} else {
			redirectAttrs.addFlashAttribute("WrongMessage", "尚未輸入任何條件");
			return "redirect:/backListPage";
		}

		model.addAttribute("questionnaires", questionnaires);
		redirectAttrs.addFlashAttribute("WrongMessage", "查詢問卷成功");
		return "backListPage";

	}

	@GetMapping(value = { "/backListPage" }, params = "delete")
	public String deleteByQuestionnaire(Model model,
										RedirectAttributes redirectAttrs,
										@RequestParam(name = "ID", required = false) String[] questionnaireId) {
		
		if (questionnaireId == null) {
			redirectAttrs.addFlashAttribute("WrongMessage", "尚未有這份問卷");
			return "redirect:/backListPage";
		}

		questionnaireService.deleteQuestionnaire(questionnaireId);
		questionDao.deleteByQuestionnaireId(questionnaireId);
		userinfoDao.deleteByQuestionnaireId(questionnaireId);

		redirectAttrs.addFlashAttribute("WrongMessage", "問卷刪除成功");
		return "redirect:/backListPage";
	}
}
