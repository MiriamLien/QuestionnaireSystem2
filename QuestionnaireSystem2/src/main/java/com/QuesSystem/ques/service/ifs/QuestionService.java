package com.QuesSystem.ques.service.ifs;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;

public interface QuestionService {

	/**
	 * 判斷問題是否為必填項目
	 * @param list
	 * @return
	 */
	public boolean QuestionMust(List<Question> list);
	
	/**
	 * 刪除問題
	 * 使用questionnaireId
	 * @param questionnaire
	 */
	public void deleteQuestions(String[] questionnaire);
	
	/**
	 * 新增/編輯問題防呆
	 * @param questionTitle
	 * @param questionChoices
	 * @param questionType
	 * @return
	 */
	public String ErrorMsg(String questionTitle, String questionChoices, int questionType);
	
	/**
	 * 新增問題
	 * @param session
	 * @param model
	 * @param questionnaire
	 * @param questionTitle 	問題標題
	 * @param questionChoices 	問題回答
	 * @param questionType 		問題類型
	 * @param mustKeyin 		是否為必填項目
	 */
	public void createQuestion(HttpSession session, 
                           Model model,
                           Questionnaire questionnaire,
                           @RequestParam("questionTitle") String questionTitle,
                           @RequestParam("questionChoices") String questionChoices, 
                           @RequestParam("questionType") int questionType,
                           @RequestParam(name = "mustKeyin", defaultValue = "0") boolean mustKeyin);

	/**
	 * (編輯)增加問題
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
	 * 取得問題清單
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public List<Question> getQuestionList(HttpSession session) throws Exception;
	
	/**
	 * 把回答進行反序列化
	 * @param questionnaireId
	 * @return
	 */
	public String questionToJson(Questionnaire questionnaireId);
}
