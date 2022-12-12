package com.QuesSystem.ques.controller;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.QuesSystem.ques.constant.AlertMessage;
import com.QuesSystem.ques.constant.UrlPath;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.service.ifs.QuestionnaireService;

@Controller
public class ListPageController {

	@Autowired
	private QuestionnaireService questionnaireService;
	
	// 前台列表頁顯示
	@GetMapping(value = { UrlPath.Path.URL_FRONT_LISTPAGE })
	public String showQuestionnaires(Model model, 
				                     HttpSession session, 
				                     RedirectAttributes redirectAttrs,
				                     // 頁碼,不帶此參數, 預設為0
				                     @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
				                     // 筆數,不帶此參數, 預設為10(一頁10筆)
				                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
			
		Page<Questionnaire> questionnaires = questionnaireService.getQuestionnaireByPageListFront(pageNum, pageSize);
		model.addAttribute("questionnaires", questionnaires);
		model.addAttribute("pageSize", pageSize);
		// 清除Session
		session.removeAttribute("questionnaire");
		// 清除問題的Session
		session.removeAttribute("questions");

		// 跳轉至前台列表頁
		return UrlPath.Path.URL_FRONT_LISTPAGE;
	}
	
	// (前台列表頁)搜尋功能
	@GetMapping(value = { UrlPath.Path.URL_FRONT_LISTPAGE }, params = "search")
	public String backListSearchKey(Model model,
                                    RedirectAttributes redirectAttrs,
                                    @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
                                    @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                    @RequestParam(name = "title", required = false, defaultValue = "") String questionnaireTitle,
                                    @RequestParam(name = "startDate", required = false, defaultValue = "") String startDate,
                                    @RequestParam(name = "endDate", required = false, defaultValue = "") String endDate)throws ParseException{
		
		// 判斷問卷標題、開始日期以及結束日期是否皆為空
		if (questionnaireTitle.isEmpty() && startDate.isEmpty() && endDate.isEmpty()) {
			// 若皆為空的話，則給予提示訊息，並重新導向前台列表頁
			redirectAttrs.addFlashAttribute("alertMessage", AlertMessage.QuestionnaireMsg.Enter_Nothing);
			return "redirect:/listPage";
		  // 判斷輸入的問卷標題長度是否小於2
		} else if (questionnaireTitle.length() < 2) {
			   // 若標題的長度小於2的話，則給予提示訊息，並重新導向前台列表頁
			   redirectAttrs.addFlashAttribute("alertMessage", AlertMessage.QuestionnaireMsg.Keyword_Above_Two_Words);
			   return "redirect:/listPage";
		}
		
		Page<Questionnaire> questionnaires = null;
		
		// 判斷開始及結束日期是否皆為空
		if (startDate.isEmpty() && endDate.isEmpty()) {
			questionnaires = questionnaireService.searchQuestionnaireByQuesTitleFront(pageNum,pageSize, questionnaireTitle);
		  // 若開始及結束日期皆有值
		} else if (!startDate.isEmpty() && !endDate.isEmpty()) {
//			qList = questionnaireService.searchQuestionnaireByAllTime(pageNum, pageSize, null, null);
		} else {
			
			// 給予提示訊息，並重新導向前台列表頁
			redirectAttrs.addFlashAttribute("alertMessage", AlertMessage.QuestionnaireMsg.Enter_Nothing);
			return "redirect:/listPage";
		}
		
		model.addAttribute("questionnaires", questionnaires);
		redirectAttrs.addFlashAttribute("alertMessage", AlertMessage.QuestionnaireMsg.Search_Success);
		return UrlPath.Path.URL_FRONT_LISTPAGE;
	}
}
