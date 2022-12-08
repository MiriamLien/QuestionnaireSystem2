package com.QuesSystem.ques.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.QuesSystem.ques.constant.AlertMessage;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.service.ifs.QuestionnaireService;

@Service
public class QuestionnaireSereviceImpl implements QuestionnaireService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private QuestionnaireDao questionnaireDao;

	public QuestionnaireDao getQuestionnaireDao() {
		return questionnaireDao;
	}

	public void setQuestionnaireDao(QuestionnaireDao questionnaireDao) {
		this.questionnaireDao = questionnaireDao;
	}

	// ���o�Ҧ��ݨ�
	@Override
	public List<Questionnaire> getQuestionnaireList() {
		// ���o�Ҧ��ݨ�
		List<Questionnaire> questionnaireList = questionnaireDao.findAll();

		// �p�GquestionnaireList���šA�h�^�Ƿs���}�C
		if (questionnaireList.isEmpty()) {
			return new ArrayList<>();
		}

		// questionnaireList���Ȫ��ܡA�h�i��^��
		return questionnaireList;
	}

	// �R���ݨ�(�ϥ�questionnaireId)
	@Override
	public void deleteQuestionnaire(String[] questionnaireIds) {

		try {

			for (String questionnaireId : questionnaireIds) {

				questionnaireDao.deleteById(questionnaireId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	// <�s�W�ݨ����b>
	@Override
	public String ErrorMsg(String questionnaireTitle, String questionnaireBody, String startDate, String endDate) {

		// �Y�ݨ����D����
		if (!StringUtils.hasText(questionnaireTitle)) {

			return AlertMessage.QuestionnaireMsg.No_Title;
		// �Y�ݨ����D�֩�5�Ӧr
		} else if (questionnaireTitle.length() < 5) {

			return AlertMessage.QuestionnaireMsg.Title_AtLeastFive;
		}

		// �Y�ݨ����e����
		if (!StringUtils.hasText(questionnaireBody)) {

			return AlertMessage.QuestionnaireMsg.No_Body;
		// �Y�ݨ����e�֩�20�Ӧr
		} else if (questionnaireBody.length() < 20) {

			return AlertMessage.QuestionnaireMsg.Body_AtLeastTwenty;
		}

		// �Y�}�l�������
		if (!StringUtils.hasText(startDate)) {

			return AlertMessage.QuestionnaireMsg.No_StartDate;
		// �Y�����������
		} else if (!StringUtils.hasText(endDate)) {

			return AlertMessage.QuestionnaireMsg.No_EndDate;
		}

		return "";
	}

	// (�e�x)�ϥΤ����i��d��
	@Override
	public Page<Questionnaire> getQuestionnaireByPageListFront(int pageNum, int pageSize) {
		// 1. �ϥ� Sort.by()�A�ھڳЫؤ���i��(�f��)�Ƨ�
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2. �A������
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3. �۩w�q�� --> Page<> ����k(�C�X���O�`�ΰ��D��)
		Page<Questionnaire> questionnaires = questionnaireDao.findByPageableFront(pageable, true);
		// 4. �^��questionnaires
		return questionnaires;
	}

	// (�e�x)�ϥΤ����i��d��
	@Override
	public Page<Questionnaire> searchQuestionnaireByQuesTitleFront(int pageNum, int pageSize,
			String questionnaireTitle) {
		// 1. �ϥ� Sort.by()�A�ھڳЫؤ���i��(�f��)�Ƨ�
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2. �A������
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3. �۩w�q�� --> Page<> ����k(�ϥΰݨ����D�i��j�M)
		Page<Questionnaire> questionnaires = questionnaireDao.findByQuesTitleFront(pageable, questionnaireTitle, true);
		// 4. �^��questionnaires
		return questionnaires;
	}

	// (��x)�ϥΤ����i��d��
	@Override
	public Page<Questionnaire> getQuestionnaireByPageList(int pageNum, int pageSize) {
		// 1. �ϥ� Sort.by()�A�ھڳЫؤ���i��(�f��)�Ƨ�
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2. �A������
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3. �۩w�q�� --> Page<> ����k(�C�X���O�`�ΰ��D��)
		Page<Questionnaire> questionnaires = questionnaireDao.findAll(pageable);
		// 4. �^��questionnaires
		return questionnaires;
	}

	// (��x)�ϥΤ����i��d��
	@Override
	public Page<Questionnaire> searchQuestionnaireByAllTime(int pageNum, int pageSize, Date startDate, Date endDate) {
		// 1. �ϥ� Sort.by()�A�ھڳЫؤ���i��(�f��)�Ƨ�
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2. �A������
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3. �۩w�q�� --> Page<> ����k(�ϥζ}�l�ε�������i��j�M)
		Page<Questionnaire> questionnaires = questionnaireDao.findByStartDateAndEndDate(pageable, startDate, endDate);
		// 4. �^��questionnaires
		return questionnaires;
	}

	// (��x)�ϥΤ����i��d��
	@Override
	public Page<Questionnaire> searchQuestionnaireByQuesTitle(int pageNum, int pageSize, String questionnaireTitle) {
		// 1. �ϥ� Sort.by()�A�ھڳЫؤ���i��(�f��)�Ƨ�
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2. �A������
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3. �۩w�q�� --> Page<> ����k(�ϥΰݨ����D�i��j�M)
		Page<Questionnaire> questionnaires = questionnaireDao.findByQuesTitle(pageable, questionnaireTitle);
		// 4. �^��questionnaires
		return questionnaires;
	}
}
