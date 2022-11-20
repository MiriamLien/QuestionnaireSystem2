package com.QuesSystem.ques.service.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.QuesSystem.ques.entity.OftenUseQuestion;
import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.enums.QuestionType;
import com.QuesSystem.ques.repository.OftenUseQuestionDao;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.service.ifs.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private int questionNumber = 0;

	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private OftenUseQuestionDao oftenUseQuestionDao;

	@Override
	public boolean QuestionMust(List<Question> questionList) {
		boolean must = false;
		for (Question question : questionList) {
			if (question.isMustKeyin() == true) {
				must = true;
				return must;
			}
		}
		return must;
	}

	/*
	 * 刪除問題
	 * 使用questionnaireId
	 */
	@Override
	public void deleteQuestions(String[] questionnaireId) {
		try {
			for (String quesId : questionnaireId) {
				questionDao.deleteByQuestionnaireId(questionnaireId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/*
	 * 新增問題防呆
	 */
	@Override
	public String ErrorMsg(String questionTitle, String questionChoices, int questionType) {
		// <問題標題防呆>
		// 問題標題為空
		if (!StringUtils.hasText(questionTitle)) {
			return "*未輸入問題標題*";
		// 問題至少要有三個字
		} else if (questionTitle.length() < 3) {
			return "*問題標題少於3個字*";
		}

		// <問題答案即問題種類防呆>
		// 單選方塊需要輸入問題答案
		if (questionType == QuestionType.單選方塊.getCode() && questionChoices == null) {
			return "*必須把問題和答案輸入完整*";
		// 單選方塊需要輸入問題答案
		} else if (questionType == QuestionType.單選方塊.getCode() && questionChoices.length() < 6) {
			return "*必須把問題輸入完整,答案至少需要有4個字*";
		}

		// 複選方塊需要輸入問題答案
		if (questionType == QuestionType.複選方塊.getCode() && questionChoices == null) {
			return "*必須把問題和答案輸入完整*";
		// 單選方塊需要輸入問題答案
		} else if (questionType == QuestionType.複選方塊.getCode() && questionChoices.length() < 8) {
			return "*必須把問題輸入完整,答案至少需要有4個字*";
		}

		// 文字方塊不需要輸入問題答案
		if (questionType == QuestionType.文字方塊.getCode() && questionChoices != null) {
			return "*選擇文字方塊不需要輸入回答欄位*";
		}
		return "";
	}

	/*
	 * 新增問題
	 */
	@Override
	public void createQuestion(HttpSession session,
							   Model model,
							   Questionnaire questionnaire,
							   String questionTitle,
							   String questionChoices,
							   int questionType,
							   boolean mustKeyin) {
		
		@SuppressWarnings("unchecked")
		List<Question> questionlist = (List<Question>) session.getAttribute("questions");
		Question ques = new Question();

		// 設定問題標題
		ques.setQuestionTitle(questionTitle);
		// 設定問題回答
		ques.setQuestionChoices(questionChoices);
		// 設定問題種類
		ques.setQuestionType(questionType);
		// 設定是否為必須回答
		ques.setMustKeyin(mustKeyin);
		// 設定問題編號
		ques.setQuestionNumber(questionNumber);
		// 設定問卷編號
		ques.setQuestionnaireId(questionnaire);
		questionlist.add(ques);

		List<OftenUseQuestion> oftenList = oftenUseQuestionDao.findAll();
		model.addAttribute("oftenUseQues", oftenList);

		// 問題編號遞增
		questionNumber++;		
	}

	/*
	 * 編輯問題
	 */
	@Override
	public void editQuestion(HttpSession session,
							 Model model,
							 Questionnaire questionnaire,
							 String questionTitle,
							 String questionChoices,
							 int questionType,
							 boolean mustKeyin) {
		
		@SuppressWarnings("unchecked")
		List<Question> questionlist = (List<Question>) session.getAttribute("questions");
		Question ques = new Question();

		// New新的流水號
		String questionID = UUID.randomUUID().toString();
		// 設定問題ID
		ques.setQuestionId(questionID);
		// 設定問題標題
		ques.setQuestionTitle(questionTitle);
		// 設定問題回答
		ques.setQuestionChoices(questionChoices);
		// 設定問題種類
		ques.setQuestionType(questionType);
		// 設定是否為必須回答
		ques.setMustKeyin(mustKeyin);
		// 設定問題編號
		ques.setQuestionNumber(questionNumber);
		// 設定問卷編號
		ques.setQuestionnaireId(questionnaire);
		questionlist.add(ques);

		List<OftenUseQuestion> oftenList = oftenUseQuestionDao.findAll();
		model.addAttribute("oftenUseQues", oftenList);

		// 問題編號遞增
		questionNumber++;		
	}

}
