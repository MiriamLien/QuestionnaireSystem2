package com.QuesSystem.ques.controller;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.entity.Userinfo;
import com.QuesSystem.ques.repository.AnswerDao;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
public class FormPageController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	public QuestionnaireDao questionnaireDao;

	@Autowired
	public QuestionDao questionDao;

	@Autowired
	public AnswerDao answerDao;

	@GetMapping(value = {"/formPage"})
	public String formInfo(Model model,
						HttpSession session,
			            @RequestParam(name = "ID", required = false) String questionnaireId) {
		
		// 透過questionnaireId找出問卷
		Questionnaire questionnaire = questionnaireDao.findById(questionnaireId).get();
		// 透過questionnaireId找出問卷內的所有問題(清單)
		List<Question> questionList = questionDao.findListByQuestionnaireId(questionnaireId);

		model.addAttribute("questionnaire", questionnaire);
		model.addAttribute("questionList", questionList);
		session.setAttribute("questionnaireInfo", questionnaire);

		return "formPage";
	}

	@PostMapping("/formPage")
	public String formInfoPost(Model model, HttpSession session,
			                	@ModelAttribute Userinfo userinfo,
			                	@RequestParam("username") String name,
			                	@RequestParam("userphone") String phone,
			                	@RequestParam("useremail") String email,
			                	@RequestParam("userage") String age) {

		Questionnaire questionnaire = (Questionnaire)session.getAttribute("questionnaireInfo");
		String questionnaireId = questionnaire.getQuestionnaireId();
		
		if (questionnaireId == null) {
			return "listPage";
		}
		
		userinfo.setName(name);
		userinfo.setPhone(phone);
	    userinfo.setEmail(email);
	    userinfo.setAge(age);
	    userinfo.setQuestionnaireId(questionnaire);
	    session.setAttribute("userinfo", userinfo);

		return "redirect:/confirmPage?ID=" + questionnaireId;
	}

	@ResponseBody
	@GetMapping(value = {"/runQues/{questionnaireId}"})
    public String runQues(Model model, @PathVariable("questionnaireId") String questionnaireId) {
		Gson gson = new Gson();
        Type gainType = new TypeToken<List<Question>>(){}.getType();
        List<Question> questionList = questionDao.findListByQuestionnaireId(questionnaireId);
        
        if(!questionList.isEmpty()){
            return gson.toJson(questionList, gainType);
        }
        
        return null;
    }
}
