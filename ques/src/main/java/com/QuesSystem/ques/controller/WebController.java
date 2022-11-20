package com.QuesSystem.ques.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.QuesSystem.ques.constant.UrlPath;
import com.QuesSystem.ques.entity.Answer;
import com.QuesSystem.ques.entity.OftenUseQuestion;
import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.entity.Userinfo;
import com.QuesSystem.ques.model.TotalAnswerValue;
import com.QuesSystem.ques.repository.AnswerDao;
import com.QuesSystem.ques.repository.OftenUseQuestionDao;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.repository.UserinfoDao;
import com.QuesSystem.ques.service.ifs.AmountService;
import com.QuesSystem.ques.service.ifs.AnswerService;
import com.QuesSystem.ques.service.ifs.OftenUseQuestionService;
import com.QuesSystem.ques.service.ifs.QuestionnaireService;

@Controller
public class WebController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private QuestionnaireService questionnaireService;

	@Autowired
	private OftenUseQuestionService oftenUseService;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private QuestionnaireDao questionnaireDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private OftenUseQuestionDao oftenUseQuestionDao;

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private UserinfoDao userInfoDao;

	@Autowired
	private AmountService amountService;

	/*
	 * 登入頁面
	 */
	@GetMapping(UrlPath.Path.URL_FRONT_LOGIN)
	public String login(Model model) {
		return UrlPath.Path.URL_FRONT_LOGIN;
	}

	/*
	 * 前台 - 問卷列表頁
	 */
	@GetMapping(UrlPath.Path.URL_FRONT_LISTPAGE)
	public String listPage(Model model,
						   HttpSession session,
						   RedirectAttributes redirectAttrs,
						   // 頁碼, 不帶此參數, 預設為0
						   @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
						   // 單一頁面顯示10筆問卷清單
						   @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
		
		Page<Questionnaire> questionnaireList = questionnaireService.getQuestionnaireByPageListFront(pageNum, pageSize);
		model.addAttribute("questionnaireList", questionnaireList);
		model.addAttribute("pageSize", pageSize);
		session.removeAttribute("questionnaire");
		session.removeAttribute("questions");

		return UrlPath.Path.URL_FRONT_LISTPAGE;
	}

	/*
	 * 前台 - 內頁(回答頁面)
	 */
//	@GetMapping(UrlPath.Path.URL_FRONT_FORM)
//	public String formPage(Model model) {
//		return UrlPath.Path.URL_FRONT_FORM;
//	}

	/*
	 * 前台 - 回答確認頁
	 */
	@GetMapping(UrlPath.Path.URL_FRONT_CONFIRM)
	public String confirmPage(Model model) {
		List<Questionnaire> questionnairesList = questionnaireService.getQuestionnaireList();
		model.addAttribute("questionnairesList", questionnairesList);

		return UrlPath.Path.URL_FRONT_CONFIRM;
	}

	/*
	 * 前台 - 統計資料頁
	 */
	@GetMapping(UrlPath.Path.URL_FRONT_ANSWERDETAIL)
	public String answerDetail(Model model) {
		return UrlPath.Path.URL_FRONT_ANSWERDETAIL;
	}

	/*
	 * 後台 - 問卷列表頁
	 */
	@GetMapping(UrlPath.Path.URL_BACK_LISTPAGE)
	public String backListPage(Model model,
							   HttpSession session,
							   RedirectAttributes redirectAttrs,
							   // 頁碼, 不帶此參數, 預設為0
							   @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
							   // 單一頁面顯示10筆問卷清單
							   @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
		
		Page<Questionnaire> questionnaires = questionnaireService.getQuestionnaireByPageList(pageNum, pageSize);
		model.addAttribute("questionnaires", questionnaires);
		model.addAttribute("pageSize", pageSize);
		// 初始化, 讓session清空
		session.removeAttribute("questionnaire");
		session.removeAttribute("questions");
		session.removeAttribute("changeques");

		return UrlPath.Path.URL_BACK_LISTPAGE;
	}

	/*
	 * 後台內頁 - 新增問卷
	 */
	@GetMapping(UrlPath.Path.URL_BACK_ADDQUESTIONNAIRE)
	public String addques(Model model,
						  HttpSession session,
						  RedirectAttributes redirectAttrs,
						  @RequestParam(name = "questionnaireId", required = false) String questionnaireId,
						  // 頁碼,不帶此參數, 預設為0
						  @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
						  // 單一頁面顯示10筆問卷清單
						  @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
		
		// 判斷問卷ID是否為null
		if (questionnaireId != null) {
			Questionnaire questionnaire = questionnaireDao.findById(questionnaireId).get();
			List<Question> quesList = questionDao.findListByQuestionnaireId(questionnaireId);
			List<Userinfo> useranswers = userInfoDao.findByQuestionnaireId(questionnaire);

			model.addAttribute("changeques", questionnaire);
			session.setAttribute("changeques", questionnaire);

			if (session.getAttribute("questions") == null) {
				session.setAttribute("questions", quesList);
			}
			if (!useranswers.isEmpty()) {
				model.addAttribute("useranswers", useranswers);
			}

			List<Answer> ansList = answerDao.findByQuestionnaireId(questionnaireId);

			List<TotalAnswerValue> totalList = amountService.getTotalAnswers(quesList, ansList);
			model.addAttribute("totalanswers", totalList);

			List<OftenUseQuestion> oftenuseList = oftenUseQuestionDao.findAll(); 
			model.addAttribute("OftenUseQues", oftenuseList);		    

			return UrlPath.Path.URL_BACK_ADDQUESTIONNAIRE;
		}
		else {		
				List<Question> questionlist = new ArrayList<>();
				session.setAttribute("questions", questionlist);

				return UrlPath.Path.URL_BACK_ADDQUESTIONNAIRE;
			}
	}

	/*
	 * 後台 - 新增常用問題
	 */
	@GetMapping(UrlPath.Path.URL_BACK_ADDOFTENUSE)
	public String addOftenUse(Model model, HttpSession session, @RequestParam(name = "ID", required = false) String id) {

		if(id == null) {
			session.removeAttribute(id);
			return UrlPath.Path.URL_BACK_ADDOFTENUSE;
		}

		OftenUseQuestion oftenUseQuestion = oftenUseQuestionDao.findById(id).get();
		model.addAttribute("changeoften", oftenUseQuestion);
		session.setAttribute("changeoften", oftenUseQuestion);

		return UrlPath.Path.URL_BACK_ADDOFTENUSE;
	}

	/*
	 * 後台 - 常用問題列表頁
	 */
	@GetMapping(UrlPath.Path.URL_BACK_OFTENUSE)
	public String oftenUse(Model model,
						   HttpSession session,
						   RedirectAttributes redirectAttrs,
						   // 頁碼,不帶此參數, 預設為0
						   @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
						   // 單一頁面顯示10筆問卷清單
						   @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
		
		// 這邊要跟th:each{ }裡的命名一致
		Page<OftenUseQuestion> oftenuses = oftenUseService.getOftenUseByPageList(pageNum, pageSize);
		model.addAttribute("oftenuses", oftenuses);
		model.addAttribute("pageSize", pageSize);
		session.removeAttribute("oftenuses");
		session.removeAttribute("changeoften");

		return UrlPath.Path.URL_BACK_OFTENUSE;
	}
}
