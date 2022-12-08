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

	// �P�_���D�O�_�����񶵥�
	@Override
	public boolean QuestionMust(List<Question> questionList) {

		// �]�w�ܼ�must���w�]��
		boolean must = false;

		for (Question question : questionList) {
			// �p�G���D�O���񶵥ت���
			if (question.isMustKeyin() == true) {

				// �⥬�L�����ܬ�true�A�æ^�Ǧ��ܼ�
				must = true;
				return must;
			}
		}

		return must;
	}

	// �R�����D
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

	// �s�W���D���b
	@Override
	public String ErrorMsg(String questionTitle, String questionChoices, int questionType) {

		// �Y���D���D����
		if (!StringUtils.hasText(questionTitle)) {

			return AlertMessage.QuestionMsg.No_QuesTitle;
			// �Y���D���D�֩�T�Ӧr
		} else if (questionTitle.length() < 3) {

			return AlertMessage.QuestionMsg.Title_AtLeastThree;
		}

		// <���D���פΰ��D�������b>
		// �����������J���D�M����
		if (questionType == QuestionType.�����.getCode() && questionChoices == null) {

			return AlertMessage.QuestionMsg.Must_QuesAndAns;
			// �����������J���D�A���צܤ֭n��6�Ӧr
		} else if (questionType == QuestionType.�����.getCode() && questionChoices.length() < 6) {

			return AlertMessage.QuestionMsg.Radio_Must_QuesAndAnsMustSix;
		}

		// �ƿ���������J���D�M����
		if (questionType == QuestionType.�ƿ���.getCode() && questionChoices == null) {

			return AlertMessage.QuestionMsg.Must_QuesAndAns;
			// �ƿ���������J���D�A���צܤ֭n��8�Ӧr
		} else if (questionType == QuestionType.�ƿ���.getCode() && questionChoices.length() < 8) {

			return AlertMessage.QuestionMsg.Check_Must_QuesAndAnsMustEight;
		}

		// <�P�_��r���: ��r���������J���D�A�����ݭn��J����>
		if (questionType == QuestionType.��r���.getCode() && questionChoices != null) {

			return AlertMessage.QuestionMsg.TextBox_MustQues_NoAns;
		}

		return "";
	}

	// �s�W���D
	@Override
	public void createQuestion(HttpSession session, Model model, Questionnaire questionnaire, String questionTitle,
			String questionChoices, int questionType, boolean mustKeyin) {

		@SuppressWarnings("unchecked")
		List<Question> questionList = (List<Question>) session.getAttribute("questions");
		Question question = new Question();

		// �]�w���D���D
		question.setQuestionTitle(questionTitle);
		// �]�w���D�^��
		question.setQuestionChoices(questionChoices);
		// �]�w���D����
		question.setQuestionType(questionType);
		// �]�w�O�_�����񶵥�
		question.setMustKeyin(mustKeyin);
		// �]�w���D�s��
		question.setQuestionNumber(questionNumber);
		// �]�w�ݨ��s��
		question.setQuestionnaireId(questionnaire);
		questionList.add(question);

		List<OftenUseQuestion> oftenList = oftenUseQuestionDao.findAll();
		model.addAttribute("oftenUseQues", oftenList);

		// ���D�s�����W
		questionNumber++;
	}

	// (�s��Ҧ�)�W�[���D
	@Override
	public void editQuestion(HttpSession session, Model model, Questionnaire questionnaireId, String questionTitle,
			String questionChoices, int questionType, boolean mustKeyin) {

		@SuppressWarnings("unchecked")
		List<Question> questionlist = (List<Question>) session.getAttribute("questions");
		Question ques = new Question();

		// �۰ʥͦ��s���y����
		String questionID = UUID.randomUUID().toString();
		// �]�w���DID
		ques.setQuestionId(questionID);
		// �]�w���D���D
		ques.setQuestionTitle(questionTitle);
		// �]�w���D�^��
		ques.setQuestionChoices(questionChoices);
		// �]�w���D����
		ques.setQuestionType(questionType);
		// �]�w�O�_�����񶵥�
		ques.setMustKeyin(mustKeyin);
		// �]�w���D�s��
		ques.setQuestionNumber(questionNumber);
		// �]�w�ݨ��s��
		ques.setQuestionnaireId(questionnaireId);
		questionlist.add(ques);

		List<OftenUseQuestion> oftenList = oftenUseQuestionDao.findAll();
		model.addAttribute("oftenUseQues", oftenList);

		// ���D�s�����W
		questionNumber++;
	}

	// ���o�Ҧ����D
	@SuppressWarnings("unchecked")
	@Override
	public List<Question> getQuestionList(HttpSession session) throws Exception {

		try {

			// ���oSession
			List<Question> questionSessionList = (List<Question>) session.getAttribute("questions");
			// �p�GSession���ŭȩά��Ū�
			if (questionSessionList == null || questionSessionList.isEmpty()) {

				// �^��null
				return null;
			}

			// �YSession���ȡA�h�^��questionSessionList
			return questionSessionList;

		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	// ��^���i��ϧǦC��
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
