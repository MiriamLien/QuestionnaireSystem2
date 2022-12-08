package com.QuesSystem.ques.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.QuesSystem.ques.constant.UrlPath;
import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.entity.Userinfo;
import com.QuesSystem.ques.model.Answers;
import com.QuesSystem.ques.model.TotalAnswerVal2;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.repository.UserinfoDao;
import com.QuesSystem.ques.service.ifs.AmountService;
import com.QuesSystem.ques.service.ifs.AnswerService;
import com.google.gson.Gson;

@Controller
public class AnswerDetailController {

	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private UserinfoDao userinfoDao;
	
	@Autowired
	private AmountService amountService;
	
	@Autowired
	private AnswerService answerService;
	
	@GetMapping(UrlPath.Path.URL_FRONT_ANSWERDETAIL)
	public String answerDetail(Model model,
						   	   // �qURL�����o�ݨ�ID(questionnaireId)
						   	   @RequestParam(name = "ID", required = false) String questionnaireId) {

		// �z�LquestionnaireId��X�ݨ�
		Questionnaire questionnaire = questionnaireDao.findById(questionnaireId).get();

		// ��ݨ�(questionnaire)�ǰe�ܫe�ݨ���ܥX��
		model.addAttribute("questionnaire", questionnaire);
		// ����ܫe�x�έp����
		return UrlPath.Path.URL_FRONT_ANSWERDETAIL;
	}

	@ResponseBody
	@GetMapping(value = {"/loadAnswerDetail/{userId}"})
	public String loadAnswerDetail(Model model,
			                       @PathVariable("userId") String userId) {
		
		Gson gson = new Gson();
		
        Optional<Userinfo> userinfoOp = userinfoDao.findById(userId);
		if(!userinfoOp.isEmpty()) {
			Userinfo userinfo = userinfoOp.get();
			
			return gson.toJson(userinfo);
		}
		
		return "nothing";
	}
	
	// �έp��
	@ResponseBody
	@GetMapping(value = {"/loadTotals/{questionnaireId}"})
    public String CountTotals(Model model,
    		                  @PathVariable("questionnaireId") Questionnaire questionnaireId) {
		
		Gson gson = new Gson();
		
		List<Question> questionList = questionDao.findListByQuestionnaireId(questionnaireId);
        List<Answers> ansList = answerService.seperateAnswer(questionnaireId);
		List<TotalAnswerVal2> totalAnswerVal2List = amountService.getTotals(questionList, ansList);
		
		return gson.toJson(totalAnswerVal2List);
	} 
}
