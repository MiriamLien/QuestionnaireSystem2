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

import com.QuesSystem.ques.constant.AlertMessage;
import com.QuesSystem.ques.constant.UrlPath;
import com.QuesSystem.ques.entity.OftenUseQuestion;
import com.QuesSystem.ques.repository.OftenUseQuestionDao;
import com.QuesSystem.ques.service.ifs.OftenUseQuestionService;
import com.QuesSystem.ques.service.ifs.QuestionService;

@Controller
public class OftenUseController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private OftenUseQuestionService oftenUseQuestionService;

    @Autowired
    private OftenUseQuestionDao oftenUseQuestionDao;
	
	// 後台常用問題管理頁面顯示
	@GetMapping(UrlPath.Path.URL_BACK_OFTENUSE)
	public String oftenUse(Model model, 
			               HttpSession session, 
			               RedirectAttributes redirectAttrs,
			               // 頁碼, 不帶此參數, 預設為0
			               @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
			               // 筆數,不帶此參數, 預設為10(一頁10筆)
			               @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
			
		// 取得常用問題
		Page<OftenUseQuestion> oftenuses = oftenUseQuestionService.getOftenUseByPageList(pageNum, pageSize);
		model.addAttribute("oftenuses", oftenuses);
		model.addAttribute("pageSize", pageSize);
		// 清除Session
		session.removeAttribute("oftenuses");
		session.removeAttribute("changeoften");

		// 跳轉至後台常用問題頁面
		return UrlPath.Path.URL_BACK_OFTENUSE;
	}
	
	// 搜尋常用問題
	@GetMapping(value = { "/backOftenUse" }, params = "search")
	public String backOftenUseListSearchKey(Model model,
                                            RedirectAttributes redirectAttrs,
                                            @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
                                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                            @RequestParam(name = "oftenuseTitle", required = false, defaultValue = "") String oftenuseTitle) {
		
		// 如果常用問題的標題為空的話
		if (oftenuseTitle.isEmpty()) {
			
			// 給予提示訊息，並跳轉至常用問題管理頁面
			redirectAttrs.addFlashAttribute("oftenuseMessage", AlertMessage.QuestionMsg.Enter_Nothing);
			return "redirect:/backOftenUse";
		// 如果常用問題的標題長度少於2個字的話
		} else if (oftenuseTitle.length() < 2) {
			
			// 給予提示訊息，並跳轉至常用問題管理頁面
			redirectAttrs.addFlashAttribute("oftenuseMessage", AlertMessage.QuestionMsg.Keyword_Above_Two_Words);
			return "redirect:/backOftenUse";
		}

		Page<OftenUseQuestion> oftenuses = null;
		
		// 如果常用問題的標題不是空的話
		if (!oftenuseTitle.isEmpty()) {
			
			// 藉由標題搜尋常用問題
			oftenuses = oftenUseQuestionService.searchOftenUseByoftenuseTitle(pageNum, pageSize, oftenuseTitle);
		} else {
			
			// 給予提示訊息，並跳轉至常用問題管理頁面
			redirectAttrs.addFlashAttribute("oftenuseMessage", AlertMessage.QuestionMsg.Enter_Nothing);
			return "redirect:/backOftenUse";
		}
		
		// 把oftenuses傳送到前端顯示
		model.addAttribute("oftenuses", oftenuses);
		// 跳轉至後台常用問題管理頁面
		return UrlPath.Path.URL_BACK_OFTENUSE;
	}
	
	// 刪除常用問題
	@GetMapping(value = "/backOftenUse", params = "delete")
	public String deleteOftenUseQuestion(Model model,
			                           	 RedirectAttributes redirectAttrs,
			                             @RequestParam(name = "oftenuseId", required = false) String[] oftenuseId) {
		// 如果常用問題ID是空值的話
		if (oftenuseId == null) {
			
			// 給予提示訊息，並跳轉至後台常用問題管理頁面
			redirectAttrs.addFlashAttribute("oftenuseMessage", AlertMessage.QuestionMsg.Click_Delete_OftenUse_Question);
			return "redirect:/backOftenUse";
		}
		
		// 常用問題ID有值的話，則使用ID進行刪除
		oftenUseQuestionService.deleteOftenUseQuestion(oftenuseId);
		// 給予提示訊息，並跳轉至後台常用問題管理頁面
		redirectAttrs.addFlashAttribute("oftenuseMessage", AlertMessage.QuestionMsg.OftenUse_Question_Has_Deleted);
		return "redirect:/backOftenUse";
	}
	
	// 新增常用問題頁面顯示
	@GetMapping(UrlPath.Path.URL_BACK_ADDOFTENUSE)
	public String addOftenUse(Model model, 
			                  HttpSession session,
			                  @RequestParam(name = "ID", required = false) String oftenuseId) {
			
		// 如果常用問題ID是空值的話
		if(oftenuseId == null) {
				
			// 清除Session
			session.removeAttribute(oftenuseId);
			// 跳轉至後台新增常用問題頁面
			return UrlPath.Path.URL_BACK_ADDOFTENUSE;
		}
			
		// 如果常用問題ID有值的話，則藉由ID尋找該常用問題
		OftenUseQuestion oftenUseQuestion = oftenUseQuestionDao.findById(oftenuseId).get();
		// 傳送至前端畫面顯示
		model.addAttribute("changeoften", oftenUseQuestion);
		// 儲存至Session中
		session.setAttribute("changeoften", oftenUseQuestion);
		
		// 跳轉至後台新增常用問題頁面
		return UrlPath.Path.URL_BACK_ADDOFTENUSE;
	}
	
	// 新增常用問題
	@PostMapping(value = { "/backAddOftenUse" })
	public String createOftenUse(Model model,
								 RedirectAttributes redirectAttrs,
			                     @RequestParam("oftenuseTitle") String oftenuseTitle,
			                     @RequestParam("oftenuseChoices") String oftenuseChoices, 
			                     @RequestParam("oftenuseType") int oftenuseType,
			                     @RequestParam(name = "mustKeyin", defaultValue = "0") boolean mustKeyin) throws ParseException {

		// 如果常用問題的標題及回答皆為空
		if (oftenuseTitle.isEmpty() && oftenuseChoices.isEmpty()) {
			
			// 給予提示訊息，並重新導向(後台)新增常用問題的頁面
			redirectAttrs.addFlashAttribute("oftenuseMessage", AlertMessage.QuestionMsg.Not_Enter_QuestionTitleAndAns);
			return "redirect:/backAddOftenUse";
		}
					
		try {		
			// 常用問題防呆
			String ErrorMsg = oftenUseQuestionService.ErrorMsg(oftenuseTitle, oftenuseChoices, oftenuseType);
			if (!ErrorMsg.isEmpty()) {
				model.addAttribute("ErrorMsg", ErrorMsg);
				return "backAddOftenUse";
			}
				
		    OftenUseQuestion often = new OftenUseQuestion();
		    // *設定常用問題標題
		    often.setOftenuseTitle(oftenuseTitle);
		    // *設定常用問題回答
		    often.setOftenuseChoices(oftenuseChoices);
		    // *設定常用問題種類
		    often.setOftenuseType(oftenuseType);
		    // *設定常用問題是否為必須回答
		    often.setMustKeyin(mustKeyin);
		    // 把often內所設定的內容儲存至資料庫
		    oftenUseQuestionDao.save(often);
		} catch (Exception e) {
			
			redirectAttrs.addFlashAttribute("oftenuseMessage", AlertMessage.QuestionMsg.Not_Enter_QuestionTitleAndAns);
		    return "redirect:/backAddOftenUse";
		}
					
		redirectAttrs.addFlashAttribute("oftenuseMessage", AlertMessage.QuestionMsg.Save_OftenUse_Success);
		return "redirect:/backOftenUse";
	}
		
	// 編輯常用問題
	@PostMapping(value = { "/backAddOftenUse" }, params = "edit")
	public String editQuestion(Model model, 
			                   RedirectAttributes redirectAttrs,
			                   HttpSession session,
			                   @RequestParam(name = "ID", required = false) String oftenuseId,
			                   @RequestParam("oftenuseTitle") String oftenuseTitle,
			                   @RequestParam("oftenuseChoices") String oftenuseChoices, 
			                   @RequestParam("oftenuseType") int oftenuseType,
			                   @RequestParam(name = "mustKeyin", defaultValue = "0") boolean mustKeyin) throws ParseException {
	        
		// 取得Session
		OftenUseQuestion changeoften = (OftenUseQuestion) session.getAttribute("changeoften");
        String oftenId = changeoften.getOftenuseId();
	        
        // 常用問題防呆
		String ErrorMsg = oftenUseQuestionService.ErrorMsg(oftenuseTitle, oftenuseChoices, oftenuseType);
		// 如果ErrorMsg不為空的話
		if(!ErrorMsg.isEmpty()) {
				
			// 把ErrorMsg傳送到前端顯示
			model.addAttribute("ErrorMsg", ErrorMsg);
			// 跳轉至(Url帶有常用問題ID)後台新增常用問題的頁面
			return "rediredt:/backAddOftenUse?ID=" + oftenId;
		}		
			
		try {
				
		    // *設定常用問題標題
			changeoften.setOftenuseTitle(oftenuseTitle);
		    // *設定常用問題回答
			changeoften.setOftenuseChoices(oftenuseChoices);
		    // *設定常用問題種類
			changeoften.setOftenuseType(oftenuseType);
		    // *設定常用問題是否為必須回答
			changeoften.setMustKeyin(mustKeyin);
			// 把changeoften內所設定好的資訊儲存至資料庫
		    oftenUseQuestionDao.save(changeoften);
		} catch (Exception e) {
			
			// (若try{}裡的內容有問題的話)給予提示訊息，並跳轉至後台新增常用問題的頁面
			redirectAttrs.addFlashAttribute("oftenuseMessage", AlertMessage.QuestionMsg.Not_Enter_QuestionTitleAndAns);
		    return "redirect:/backAddOftenUse";
		}
		
		redirectAttrs.addFlashAttribute("oftenuseMessage", AlertMessage.QuestionMsg.Save_EditedOftenUse_Success);
		return "redirect:/backOftenUse";
	}
}
