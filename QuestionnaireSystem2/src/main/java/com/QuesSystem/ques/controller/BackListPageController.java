package com.QuesSystem.ques.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.QuesSystem.ques.constant.AlertMessage;
import com.QuesSystem.ques.constant.UrlPath;
import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.entity.Userinfo;
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
	
	// 後台列表頁顯示
	@GetMapping(UrlPath.Path.URL_BACK_LISTPAGE)
	public String backListPage(Model model, 
			                   HttpSession session, 
			                   RedirectAttributes redirectAttrs,
			                   // 頁碼,不帶此參數, 預設為0
			                   @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
			                   // 筆數,不帶此參數, 預設為10(一頁10筆)
			                   @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {

		Page<Questionnaire> questionnaires = questionnaireService.getQuestionnaireByPageList(pageNum, pageSize);
		model.addAttribute("questionnaires", questionnaires);
		model.addAttribute("pageSize", pageSize);
		// 清除問卷、問題以及問卷編輯後的Session
		session.removeAttribute("questionnaire");
		session.removeAttribute("changeques");
		session.removeAttribute("questions");

		// 跳轉至後台列表頁
		return UrlPath.Path.URL_BACK_LISTPAGE;
	}

	// (後台列表頁)搜尋功能
	@GetMapping(value = { "/backListPage" }, params = "search")
	public String backListSearchKey(Model model,
									RedirectAttributes redirectAttrs,
									@RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
									@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
									@RequestParam(name = "title", required = false, defaultValue = "") String questionnaireTitle,
									@RequestParam(name = "startDate", required = false, defaultValue = "") String startDate,
									@RequestParam(name = "endDate", required = false, defaultValue = "") String endDate) throws ParseException {

		// 判斷問卷標題、開始及結束日期三者是否皆為空
		if (questionnaireTitle.isEmpty() && startDate.isEmpty() && endDate.isEmpty()) {
			
			// 若皆為空的話，則給予提示訊息，並重新導向後台列表頁
			redirectAttrs.addFlashAttribute("WrongMessage", AlertMessage.QuestionnaireMsg.Enter_Nothing);
			return "redirect:/backListPage";
		  // 判斷輸入的問卷標題長度是否小於2
		} else if (questionnaireTitle.length() < 2) {
			
			// 若標題的長度小於2的話，則給予提示訊息，並重新導向後台列表頁
			redirectAttrs.addFlashAttribute("WrongMessage", AlertMessage.QuestionnaireMsg.Keyword_Above_Two_Words);
			return "redirect:/backListPage";
		}

		Page<Questionnaire> questionnaire = null;

		// 判斷開始及結束日期是否皆為空
		if (startDate.isEmpty() && endDate.isEmpty()) {
			
			questionnaire = questionnaireService.searchQuestionnaireByQuesTitle(pageNum, pageSize, questionnaireTitle);
		  // 若開始及結束日期皆有值
		} else if (!startDate.isEmpty() && !endDate.isEmpty()) {
			
//			questionnaires = questionnaireService.searchQuestionnaireByAllTime(pageNum, pageSize, null, null);
		} else {
			
			// 給予提示訊息，並重新導向後台列表頁
			redirectAttrs.addFlashAttribute("WrongMessage", AlertMessage.QuestionnaireMsg.Enter_Nothing);
			return "redirect:/backListPage";
		}

		model.addAttribute("questionnaire", questionnaire);
		redirectAttrs.addFlashAttribute("WrongMessage", AlertMessage.QuestionnaireMsg.Search_Success);
		return UrlPath.Path.URL_BACK_LISTPAGE;
	}

	// 問卷刪除功能
	@GetMapping(value = { "/backListPage" }, params = "delete")
	public String deleteQuestionnaire(Model model,
									  RedirectAttributes redirectAttrs,
									  @RequestParam(name = "ID", required = false) String[] questionnaireId) {
		
		// 如果問卷ID為空值的話
		if (questionnaireId == null) {
			// 給予提示訊息，並重新導向後台列表頁
			redirectAttrs.addFlashAttribute("WrongMessage", AlertMessage.QuestionnaireMsg.Click_Delete_Questionnaire);
			return "redirect:/backListPage";
		}

		// 刪除問卷、問題以及使用者資訊
		questionnaireService.deleteQuestionnaire(questionnaireId);
		questionDao.deletebyQuestionnaireId(questionnaireId);
		userinfoDao.deleteByQuestionnaireId(questionnaireId);

		// 刪除後給予刪除成功的提示訊息，並重新導向後台列表頁
		redirectAttrs.addFlashAttribute("WrongMessage", AlertMessage.QuestionnaireMsg.Questionnaire_Has_Deleted);
		return "redirect:/backListPage";
	}
}
