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
import com.QuesSystem.ques.service.ifs.QuestionnaireService;

@Controller
public class ListPageController {

	@Autowired
	private QuestionnaireService questionnaireService;

	@GetMapping(value = { "/listPage" }, params = "search")
	public String backListSearchKey(Model model,
									RedirectAttributes redirectAttrs,
									@RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
									@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
									@RequestParam(name = "title", required = false, defaultValue = "") String questionnaireTitle,
									@RequestParam(name = "startDate", required = false, defaultValue = "") String startDate,
									@RequestParam(name = "endDate", required = false, defaultValue = "") String endDate) throws ParseException {

		if (questionnaireTitle.isEmpty() && startDate.isEmpty() && endDate.isEmpty()) {
			redirectAttrs.addFlashAttribute("WrongMessage", "尚未輸入任何條件");
			return "redirect:/listPage";
		}

		Page<Questionnaire> qList = null;

		if (startDate.isEmpty() && endDate.isEmpty()) {
			qList = questionnaireService.searchByQuestionnaireTitle(pageNum, pageSize, questionnaireTitle);
		} else if (!startDate.isEmpty() && !endDate.isEmpty()) {
			// qList = questionnaireService.searchQuestionnaireByAllTime(pageNum, pageSize, null, null);
		} else {
			redirectAttrs.addFlashAttribute("WrongMessage", "尚未輸入任何條件");
			return "redirect:/listPage";
		}

		model.addAttribute("qList", qList);
		redirectAttrs.addFlashAttribute("WrongMessage", "查詢問卷成功");
		return "listPage";

	}
}
