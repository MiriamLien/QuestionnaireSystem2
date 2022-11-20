package com.QuesSystem.ques.controller;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.QuesSystem.ques.constant.UrlPath;
import com.QuesSystem.ques.entity.OftenUseQuestion;
import com.QuesSystem.ques.repository.OftenUseQuestionDao;
import com.QuesSystem.ques.service.ifs.OftenUseQuestionService;
import com.QuesSystem.ques.service.ifs.QuestionService;

@Controller
public class OftenUseQuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private OftenUseQuestionService oftenUseQuestionService;

	@Autowired
	private OftenUseQuestionDao oftenUseQuestionDao;

	// 新增常用問題
	@PostMapping(value = { "/backAddOftenUse" }, params = "create")
	public String createQuestion(Model model,
								 RedirectAttributes redirectAttrs,
								 @RequestParam("title") String title,
								 @RequestParam("choices") String choices,
								 @RequestParam("type") int type,
								 @RequestParam(name = "mustKeyin", defaultValue = "0") boolean mustKeyin) throws ParseException {

		if (title.isEmpty() && choices.isEmpty()) {
			redirectAttrs.addFlashAttribute("oftenUseMsg", "尚未輸入問題標題以及問題回答");
			return "redirect:/backAddOftenUse";
		}

		try {
			// 常用問題防呆
			String ErrorMsg = oftenUseQuestionService.ErrorMsg(title, choices, type);
			if (!ErrorMsg.isEmpty()) {
				model.addAttribute("ErrorMsg", ErrorMsg);
				return "backAddOftenUse";
			}

			OftenUseQuestion often = new OftenUseQuestion();
			// *設定問題標題
			often.setTitle(title);
			// *設定問題回答
			often.setChoices(choices);
			// *設定問題種類
			often.setType(type);
			// *設定是否為必須回答
			often.setMustKeyin(mustKeyin);
			oftenUseQuestionDao.save(often);
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("oftenUseMsg", "尚未輸入問題標題以及問題回答"); // 修改
			return "redirect:/backAddOftenUse";
		}

		redirectAttrs.addFlashAttribute("oftenUseMsg", "常用問題建立完成");
		return "redirect:/backOftenUse";
	}
	
	// 編輯常用問題
	@PostMapping(value = { "/backAddOftenUse" }, params = "edit")
	public String editQuestion(Model model, 
			                   RedirectAttributes redirectAttrs,
			                   HttpSession session,
			                   @RequestParam(name = "ID", required = false) String id,
			                   @RequestParam("title") String title,
			                   @RequestParam("choices") String choices, 
			                   @RequestParam("type") int type,
			                   @RequestParam(name = "mustKeyin", defaultValue = "0") boolean mustKeyin) throws ParseException {
        OftenUseQuestion changeoften = (OftenUseQuestion) session.getAttribute("changeoften");
        String oftenId = changeoften.getOftenUseId();

		// 常用問題防呆
		String ErrorMsg = oftenUseQuestionService.ErrorMsg(title, choices, type);
		if(!ErrorMsg.isEmpty()) {
			model.addAttribute("ErrorMsg", ErrorMsg);
			return "rediredt:/backAddOftenUse?ID=" + oftenId;
		}		

		try {		
		    // *設定問題標題
			changeoften.setTitle(title);
		    // *設定問題回答
			changeoften.setChoices(choices);
		    // *設定問題種類
			changeoften.setType(type);
		    // *設定是否為必須回答
			changeoften.setMustKeyin(mustKeyin);
		    oftenUseQuestionDao.save(changeoften);
		}
		catch (Exception e){
			redirectAttrs.addFlashAttribute("oftenUseMsg", "尚未輸入問題標題以及問題回答"); //修改
		    return "redirect:/backAddOftenUse";
		}

		redirectAttrs.addFlashAttribute("oftenUseMsg", "常用問題建立完成");
		return "redirect:/backOftenUse";
	}
	
	// 刪除常用問題
	@GetMapping(value = "/backOftenUse", params = "delete")
	public String deleteByOftenUseQues(Model model,
									   RedirectAttributes redirectAttrs,
									   @RequestParam(name = "ID", required = false) String[] id) {
		if (id == null) {
			redirectAttrs.addFlashAttribute("oftenUseMsg", "尚未有這個常用問題");
			return "redirect:/backOftenUse";
		}

		oftenUseQuestionService.deleteOftenUseQuestion(id);

		redirectAttrs.addFlashAttribute("oftenUseMsg", "常用問題刪除成功");
		return "redirect:/backOftenUse";
	}

	// 搜尋常用問題
	@GetMapping(value = { "/backOftenUse" }, params = "search")
	public String backOftenUseListSearchKey(Model model,
											RedirectAttributes redirectAttrs,
											@RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
											@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
											@RequestParam(name = "title", required = false, defaultValue = "") String title) {

		if (title.isEmpty()) {
			redirectAttrs.addFlashAttribute("oftenUseMsg", "尚未輸入任何條件");
			return "redirect:/backOftenUse";
		} else if (title.length() < 2) {
			redirectAttrs.addFlashAttribute("oftenUseMsg", "關鍵字至少需要有2個字");
			return "redirect:/backOftenUse";
		}

		Page<OftenUseQuestion> oftenuses = null;

		if (!title.isEmpty()) {
			oftenuses = oftenUseQuestionService.searchOftenUseByoftenuseTitle(pageNum, pageSize, title);
		} else {
			redirectAttrs.addFlashAttribute("oftenUseMsg", "尚未輸入任何條件");
			return "redirect:/backOftenUse";
		}

		model.addAttribute("oftenuses", oftenuses);

		return UrlPath.Path.URL_BACK_OFTENUSE;
	}
}
