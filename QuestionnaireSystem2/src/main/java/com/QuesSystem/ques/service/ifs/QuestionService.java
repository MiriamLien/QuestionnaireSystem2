package com.QuesSystem.ques.service.ifs;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;

public interface QuestionService {

	/**
	 * �P�_���D�O�_�����񶵥�
	 * @param list
	 * @return
	 */
	public boolean QuestionMust(List<Question> list);
	
	/**
	 * �R�����D
	 * �ϥ�questionnaireId
	 * @param questionnaire
	 */
	public void deleteQuestions(String[] questionnaire);
	
	/**
	 * �s�W/�s����D���b
	 * @param questionTitle
	 * @param questionChoices
	 * @param questionType
	 * @return
	 */
	public String ErrorMsg(String questionTitle, String questionChoices, int questionType);
	
	/**
	 * �s�W���D
	 * @param session
	 * @param model
	 * @param questionnaire
	 * @param questionTitle 	���D���D
	 * @param questionChoices 	���D�^��
	 * @param questionType 		���D����
	 * @param mustKeyin 		�O�_�����񶵥�
	 */
	public void createQuestion(HttpSession session, 
                           Model model,
                           Questionnaire questionnaire,
                           @RequestParam("questionTitle") String questionTitle,
                           @RequestParam("questionChoices") String questionChoices, 
                           @RequestParam("questionType") int questionType,
                           @RequestParam(name = "mustKeyin", defaultValue = "0") boolean mustKeyin);

	/**
	 * (�s��)�W�[���D
	 * @param session
	 * @param model
	 * @param questionnaire
	 * @param questionTitle
	 * @param questionChoices
	 * @param questionType
	 * @param mustKeyin
	 */
	public void editQuestion(HttpSession session, 
			             Model model, 
			             Questionnaire questionnaire, 
			             String questionTitle,
			             String questionChoices, 
			             int questionType, 
			             boolean mustKeyin);
	
	/**
	 * ���o���D�M��
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public List<Question> getQuestionList(HttpSession session) throws Exception;
	
	/**
	 * ��^���i��ϧǦC��
	 * @param questionnaireId
	 * @return
	 */
	public String questionToJson(Questionnaire questionnaireId);
}
