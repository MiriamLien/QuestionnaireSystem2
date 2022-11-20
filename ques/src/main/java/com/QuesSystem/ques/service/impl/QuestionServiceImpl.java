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
	 * �R�����D
	 * �ϥ�questionnaireId
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
	 * �s�W���D���b
	 */
	@Override
	public String ErrorMsg(String questionTitle, String questionChoices, int questionType) {
		// <���D���D���b>
		// ���D���D����
		if (!StringUtils.hasText(questionTitle)) {
			return "*����J���D���D*";
		// ���D�ܤ֭n���T�Ӧr
		} else if (questionTitle.length() < 3) {
			return "*���D���D�֩�3�Ӧr*";
		}

		// <���D���קY���D�������b>
		// ������ݭn��J���D����
		if (questionType == QuestionType.�����.getCode() && questionChoices == null) {
			return "*��������D�M���׿�J����*";
		// ������ݭn��J���D����
		} else if (questionType == QuestionType.�����.getCode() && questionChoices.length() < 6) {
			return "*��������D��J����,���צܤֻݭn��4�Ӧr*";
		}

		// �ƿ����ݭn��J���D����
		if (questionType == QuestionType.�ƿ���.getCode() && questionChoices == null) {
			return "*��������D�M���׿�J����*";
		// ������ݭn��J���D����
		} else if (questionType == QuestionType.�ƿ���.getCode() && questionChoices.length() < 8) {
			return "*��������D��J����,���צܤֻݭn��4�Ӧr*";
		}

		// ��r������ݭn��J���D����
		if (questionType == QuestionType.��r���.getCode() && questionChoices != null) {
			return "*��ܤ�r������ݭn��J�^�����*";
		}
		return "";
	}

	/*
	 * �s�W���D
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

		// �]�w���D���D
		ques.setQuestionTitle(questionTitle);
		// �]�w���D�^��
		ques.setQuestionChoices(questionChoices);
		// �]�w���D����
		ques.setQuestionType(questionType);
		// �]�w�O�_�������^��
		ques.setMustKeyin(mustKeyin);
		// �]�w���D�s��
		ques.setQuestionNumber(questionNumber);
		// �]�w�ݨ��s��
		ques.setQuestionnaireId(questionnaire);
		questionlist.add(ques);

		List<OftenUseQuestion> oftenList = oftenUseQuestionDao.findAll();
		model.addAttribute("oftenUseQues", oftenList);

		// ���D�s�����W
		questionNumber++;		
	}

	/*
	 * �s����D
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

		// New�s���y����
		String questionID = UUID.randomUUID().toString();
		// �]�w���DID
		ques.setQuestionId(questionID);
		// �]�w���D���D
		ques.setQuestionTitle(questionTitle);
		// �]�w���D�^��
		ques.setQuestionChoices(questionChoices);
		// �]�w���D����
		ques.setQuestionType(questionType);
		// �]�w�O�_�������^��
		ques.setMustKeyin(mustKeyin);
		// �]�w���D�s��
		ques.setQuestionNumber(questionNumber);
		// �]�w�ݨ��s��
		ques.setQuestionnaireId(questionnaire);
		questionlist.add(ques);

		List<OftenUseQuestion> oftenList = oftenUseQuestionDao.findAll();
		model.addAttribute("oftenUseQues", oftenList);

		// ���D�s�����W
		questionNumber++;		
	}

}
