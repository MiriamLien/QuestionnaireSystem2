package com.QuesSystem.ques.controller;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.entity.Userinfo;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.repository.UserinfoDao;
import com.google.gson.Gson;

@Controller
public class AmountController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private UserinfoDao userinfoDao;
	
	@GetMapping(value = {"/answers"} )
	public String answers (Model model,
			               @RequestParam(name="questionnaireId",required = false) String questionnaireId) {
       Questionnaire ques = questionnaireDao.findById(questionnaireId).get();
	   model.addAttribute("ques", ques);					
       return "answers";		
	}
	
	@ResponseBody
	@GetMapping(value = {"/loadanswers/{userId}"})
	public String loadAnswers(Model model,
			                  @PathVariable("userId") String userId) {
		
		Gson gson = new Gson();
		
		Optional<Userinfo> userinfoOp = userinfoDao.findById(userId);
		if(!userinfoOp.isEmpty()) {
			Userinfo userinfo = userinfoOp.get();
			return gson.toJson(userinfo);
		}
		return "notThing";
	}
}
