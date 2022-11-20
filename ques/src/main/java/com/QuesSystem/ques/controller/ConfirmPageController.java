package com.QuesSystem.ques.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.QuesSystem.ques.entity.Userinfo;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.repository.UserinfoDao;
import com.google.gson.Gson;

@Controller
public class ConfirmPageController {
	
	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private UserinfoDao userinfoDao;
	

//	@GetMapping(value = { "/confirmPage" })
//	public String confirmPage(Model model, @RequestParam(name = "questionnaireId", required = false) String questionnaireId // 透過URL取得questionnaireId
//			, HttpSession session, HttpServletRequest request) {
//		Questionnaire questionnaire = questionnaireDao.findByQuestionnaireId(questionnaireId); // 透過questionnaireId找到對應的問卷
//		List<Question> list = questionDao.findListByQuesId(questionnaireId); // 找所有questionnaireId的問題
//
//		model.addAttribute("questionnaire", questionnaire); // 前端印出問卷資料
//		model.addAttribute("questionList", list); // 前端印出所有問題資料
//		return "confirmPage";
//	}
	
	@PostMapping(value = { "/confirmPage" })
	public String saveFeedback(Model model,
							   HttpSession session,
							   RedirectAttributes redirectAttrs,
							   HttpServletRequest request) throws UnsupportedEncodingException {

//		Gson gson = new Gson();
		Userinfo sessionUserinfo = (Userinfo) session.getAttribute("userinfo"); // 取得回答問卷資料
//		String userId = sessionUserinfo.getUserId();
//		List<Question> questionlist = questionDao.findByQuestionId(userId);
//		List<Question> list = new ArrayList<>();
//		for (Question question : questionlist) {
//			if (question.getNullable().equals("on")) {
//				list.add(question);
//			}
//		}
//		Cookie[] cookies = request.getCookies(); // 取得所有cookie
//		if (cookies != null) {
//			for (Cookie cookie : cookies) {
//				if (cookie.getName().equals("ansVal")) { // 如果有符合名字的cookie
//					String answer = URLDecoder.decode(cookie.getValue(), "UTF-8"); // 解碼
//					List<Answer> targetList = new ArrayList<Answer>();
//					Answer[] ans;
//					ans = gson.fromJson(answer, Answer[].class);
//					for (Answer a : ans) {
//						targetList.add(a);
//					}
//					Integer targetId = null;
//					for (Question ques : list) {
//						targetId = ques.getQuId();
//						boolean correct = false;
//						for (Answer item : targetList) {
//							if (list.size() > targetList.size()) {
//								break;
//							}
//							if ((targetId + "").equals(item.getKey())) {
//								correct = true;
//								break;
//							}
//						}
//						if (!correct) {
//							redirectAttrs.addFlashAttribute("alertMessage", "請檢查必填後再送出");
//							return "redirect:/survey?postId=" + postId;
//						}
//					}
//					sessionUserinfo.setAnswer(answer); // 加入答案
					userinfoDao.save(sessionUserinfo); // 儲存
//				}
//			}
//		}
//		redirectAttrs.addFlashAttribute("alertMessage", "作答成功!! 感謝您的填寫");
		return "redirect:/listPage";
	}
}
