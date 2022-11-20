package com.QuesSystem.ques.service.ifs;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;

public interface QuestionService {

	public boolean QuestionMust(List<Question> list);

	/*
	 * �R�����D
	 * �ϥ�questionnaireId
	 */
	public void deleteQuestions(String[] questionnaireId);

	/*
	 * �s�W���D���b
	 */
	public String ErrorMsg(String questionTitle, String questionChoices, int questionType);
	
	/*
	 * �s�W���D�H�έק���D
	 * �bQuestionController�̪�74.94��Q�ϥ�
	 */
	public void createQuestion(HttpSession session, 
							   Model model,
							   Questionnaire questionnaire,
							   @RequestParam("questionTitle") String questionTitle,
							   @RequestParam("questionChoices") String questionChoices, 
							   @RequestParam("questionType") int questionType,
							   @RequestParam(name = "mustKeyin", defaultValue = "0") boolean mustKeyin);

	public void editQuestion(HttpSession session, 
			             	 Model model, 
			             	 Questionnaire questionnaire, 
			             	 String questionTitle,
			             	 String questionChoices, 
			             	 int questionType, 
			             	 boolean mustKeyin);
}
