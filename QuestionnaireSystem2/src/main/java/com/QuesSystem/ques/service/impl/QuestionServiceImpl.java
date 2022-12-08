package com.QuesSystem.ques.service.impl;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.QuesSystem.ques.constant.AlertMessage;
import com.QuesSystem.ques.entity.OftenUseQuestion;
import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.enums.QuestionType;
import com.QuesSystem.ques.repository.OftenUseQuestionDao;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.service.ifs.QuestionService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class QuestionServiceImpl implements QuestionService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private int questionNumber = 0;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private OftenUseQuestionDao oftenUseQuestionDao;

	// 判斷問題是否為必填項目
	@Override
	public boolean QuestionMust(List<Question> questionList) {

		// 設定變數must的預設值
		boolean must = false;

		for (Question question : questionList) {
			// 如果問題是必填項目的話
			if (question.isMustKeyin() == true) {

				// 把布林值轉變為true，並回傳此變數
				must = true;
				return must;
			}
		}

		return must;
	}

	// 刪除問題
	@Override
	public void deleteQuestions(String[] questionnaireId) {

		try {

			for (String quesId : questionnaireId) {

				questionDao.deletebyQuestionnaireId(questionnaireId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	// 新增問題防呆
	@Override
	public String ErrorMsg(String questionTitle, String questionChoices, int questionType) {

		// 若問題標題為空
		if (!StringUtils.hasText(questionTitle)) {

			return AlertMessage.QuestionMsg.No_QuesTitle;
			// 若問題標題少於三個字
		} else if (questionTitle.length() < 3) {

			return AlertMessage.QuestionMsg.Title_AtLeastThree;
		}

		// <問題答案及問題種類防呆>
		// 單選方塊必須輸入問題和答案
		if (questionType == QuestionType.單選方塊.getCode() && questionChoices == null) {

			return AlertMessage.QuestionMsg.Must_QuesAndAns;
			// 單選方塊必須輸入問題，答案至少要有6個字
		} else if (questionType == QuestionType.單選方塊.getCode() && questionChoices.length() < 6) {

			return AlertMessage.QuestionMsg.Radio_Must_QuesAndAnsMustSix;
		}

		// 複選方塊必須輸入問題和答案
		if (questionType == QuestionType.複選方塊.getCode() && questionChoices == null) {

			return AlertMessage.QuestionMsg.Must_QuesAndAns;
			// 複選方塊必須輸入問題，答案至少要有8個字
		} else if (questionType == QuestionType.複選方塊.getCode() && questionChoices.length() < 8) {

			return AlertMessage.QuestionMsg.Check_Must_QuesAndAnsMustEight;
		}

		// <判斷文字方塊: 文字方塊必須輸入問題，但不需要輸入答案>
		if (questionType == QuestionType.文字方塊.getCode() && questionChoices != null) {

			return AlertMessage.QuestionMsg.TextBox_MustQues_NoAns;
		}

		return "";
	}

	// 新增問題
	@Override
	public void createQuestion(HttpSession session, Model model, Questionnaire questionnaire, String questionTitle,
			String questionChoices, int questionType, boolean mustKeyin) {

		@SuppressWarnings("unchecked")
		List<Question> questionList = (List<Question>) session.getAttribute("questions");
		Question question = new Question();

		// 設定問題標題
		question.setQuestionTitle(questionTitle);
		// 設定問題回答
		question.setQuestionChoices(questionChoices);
		// 設定問題種類
		question.setQuestionType(questionType);
		// 設定是否為必填項目
		question.setMustKeyin(mustKeyin);
		// 設定問題編號
		question.setQuestionNumber(questionNumber);
		// 設定問卷編號
		question.setQuestionnaireId(questionnaire);
		questionList.add(question);

		List<OftenUseQuestion> oftenList = oftenUseQuestionDao.findAll();
		model.addAttribute("oftenUseQues", oftenList);

		// 問題編號遞增
		questionNumber++;
	}

	// (編輯模式)增加問題
	@Override
	public void editQuestion(HttpSession session, Model model, Questionnaire questionnaireId, String questionTitle,
			String questionChoices, int questionType, boolean mustKeyin) {

		@SuppressWarnings("unchecked")
		List<Question> questionlist = (List<Question>) session.getAttribute("questions");
		Question ques = new Question();

		// 自動生成新的流水號
		String questionID = UUID.randomUUID().toString();
		// 設定問題ID
		ques.setQuestionId(questionID);
		// 設定問題標題
		ques.setQuestionTitle(questionTitle);
		// 設定問題回答
		ques.setQuestionChoices(questionChoices);
		// 設定問題種類
		ques.setQuestionType(questionType);
		// 設定是否為必填項目
		ques.setMustKeyin(mustKeyin);
		// 設定問題編號
		ques.setQuestionNumber(questionNumber);
		// 設定問卷編號
		ques.setQuestionnaireId(questionnaireId);
		questionlist.add(ques);

		List<OftenUseQuestion> oftenList = oftenUseQuestionDao.findAll();
		model.addAttribute("oftenUseQues", oftenList);

		// 問題編號遞增
		questionNumber++;
	}

	// 取得所有問題
	@SuppressWarnings("unchecked")
	@Override
	public List<Question> getQuestionList(HttpSession session) throws Exception {

		try {

			// 取得Session
			List<Question> questionSessionList = (List<Question>) session.getAttribute("questions");
			// 如果Session為空值或為空的
			if (questionSessionList == null || questionSessionList.isEmpty()) {

				// 回傳null
				return null;
			}

			// 若Session有值，則回傳questionSessionList
			return questionSessionList;

		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	// 把回答進行反序列化
	@Override
	public String questionToJson(Questionnaire questionnaireId) {
		
		Gson gson = new Gson();

		Type getType = new TypeToken<List<Question>>() {}.getType();

		List<Question> questionList = questionDao.findListByQuestionnaireId(questionnaireId);

		if (!questionList.isEmpty()) {
			
			return gson.toJson(questionList, getType);
		}
		
		return null;
	}
}
