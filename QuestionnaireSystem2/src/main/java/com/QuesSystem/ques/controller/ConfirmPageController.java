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
//	public String confirmPage(Model model, @RequestParam(name = "questionnaireId", required = false) String questionnaireId // �z�LURL���oquestionnaireId
//			, HttpSession session, HttpServletRequest request) {
//		Questionnaire questionnaire = questionnaireDao.findByQuestionnaireId(questionnaireId); // �z�LquestionnaireId���������ݨ�
//		List<Question> list = questionDao.findListByQuesId(questionnaireId); // ��Ҧ�questionnaireId�����D
//
//		model.addAttribute("questionnaire", questionnaire); // �e�ݦL�X�ݨ����
//		model.addAttribute("questionList", list); // �e�ݦL�X�Ҧ����D���
//		return "confirmPage";
//	}
	
	@PostMapping(value = { "/confirmPage" })
	public String saveFeedback(Model model,
							   HttpSession session,
							   RedirectAttributes redirectAttrs,
							   HttpServletRequest request) throws UnsupportedEncodingException {

//		Gson gson = new Gson();
		Userinfo sessionUserinfo = (Userinfo) session.getAttribute("userinfo"); // ���o�^���ݨ����
//		String userId = sessionUserinfo.getUserId();
//		List<Question> questionlist = questionDao.findByQuestionId(userId);
//		List<Question> list = new ArrayList<>();
//		for (Question question : questionlist) {
//			if (question.getNullable().equals("on")) {
//				list.add(question);
//			}
//		}
//		Cookie[] cookies = request.getCookies(); // ���o�Ҧ�cookie
//		if (cookies != null) {
//			for (Cookie cookie : cookies) {
//				if (cookie.getName().equals("ansVal")) { // �p�G���ŦX�W�r��cookie
//					String answer = URLDecoder.decode(cookie.getValue(), "UTF-8"); // �ѽX
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
//							redirectAttrs.addFlashAttribute("alertMessage", "���ˬd�����A�e�X");
//							return "redirect:/survey?postId=" + postId;
//						}
//					}
//					sessionUserinfo.setAnswer(answer); // �[�J����
					userinfoDao.save(sessionUserinfo); // �x�s
//				}
//			}
//		}
//		redirectAttrs.addFlashAttribute("alertMessage", "�@�����\!! �P�±z����g");
		return "redirect:/listPage";
	}
}
