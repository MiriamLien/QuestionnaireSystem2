package com.QuesSystem.ques.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.service.ifs.QuestionnaireService;

@Service
public class QuestionnaireSereviceImpl implements QuestionnaireService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private QuestionnaireDao questionnaireDao;

	/*
	 * ���o�ݨ��M��
	 */
	@Override
	public List<Questionnaire> getQuestionnaireList() {
		List<Questionnaire> questionnaireList = questionnaireDao.findAll();
		// �P�_�ݨ��M��O�_����
		if (questionnaireList.isEmpty()) {
			return new ArrayList<>();
		}
		return questionnaireList;
	}

	/*
	 * �����d��(�e�x)
	 */
	@Override
	public Page<Questionnaire> getQuestionnaireByPageListFront(int pageNum, int pageSize) {
		// 1.�ϥ� Sort.by()���i��Ƨ�
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.�A������
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.�۩w�q�� --> Page<> ����k
		Page<Questionnaire> questionnaires = questionnaireDao.findByPageableFront(pageable, true);
		// 4.�^��questionnaires
		return questionnaires;
	}

	/*
	 * (�ǥѰݨ����D)�j�M�ݨ�(�e�x)
	 */
	@Override
	public Page<Questionnaire> searchByQuestionnaireTitleFront(int pageNum, int pageSize, String questionnaireTitle) {
		// �ϥ� Sort.by()���i��Ƨ�
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.�A������
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.�ۭq�q�� --> Page<> ����k(�ϥΰݨ����D�j�M)
		Page<Questionnaire> questionnaires = questionnaireDao.findByQuestionnaireTitleFront(pageable, questionnaireTitle, true);
		// 4.�^��questionnaires
		return questionnaires;
	}

	/*
	 * �����d��(��x)
	 */
	@Override
	public Page<Questionnaire> getQuestionnaireByPageList(int pageNum, int pageSize) {
		// 1.�ϥ� Sort.by()���i��Ƨ�
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.�A������
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.�۩w�q�� --> Page<> ����k
		Page<Questionnaire> questionnaires = questionnaireDao.findAll(pageable);
		// 4.�^��questionnaires
		return questionnaires;
	}

	/*
	 * (�ǥѰݨ����D)�j�M�ݨ�(��x)
	 */
	@Override
	public Page<Questionnaire> searchByQuestionnaireTitle(int pageNum, int pageSize, String questionnaireTitle) {
		// �ϥ� Sort.by()���i��Ƨ�
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.�A������
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.�ۭq�q�� --> Page<> ����k(�ϥΰݨ����D�j�M)
		Page<Questionnaire> questionnaires = questionnaireDao.findByQuestionnaireTitle(pageable, questionnaireTitle);
		// 4.�^��questionnaires
		return questionnaires;
	}

	/*
	 * (�ǥѰ_�l&�������)�j�M�ݨ�
	 */
	@Override
	public Page<Questionnaire> searchQuestionnaireByAllTime(int pageNum, int pageSize, Date startDate, Date endDate) {
		// 1.�ϥ� Sort.by()���i��Ƨ�
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.�A������
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.�۩w�q�� --> Page<> ����k(�ϥζ}�l����ε�������j�M)
		Page<Questionnaire> questionnaires = questionnaireDao.findByStartDateAndEndDate(pageable, startDate, endDate);
		// 4.�^��questionnaires
		return questionnaires;
	}
	
	/*
	 * �R���ݨ�
	 * �ϥ�questionnaireId
	 */
	@Override
	public void deleteQuestionnaire(String[] questionnaireId) {
		try {
			for (String quesId : questionnaireId) {
				questionnaireDao.deleteById(quesId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/*
	 * �s�W�ݨ����b(�ˬd�ݨ��e�X�e�ˬd���L���~)
	 */
	@Override
	public String ErrorMsg(String questionnaireTitle, String questionnaireBody, String startDate, String endDate) {
		// <�ݨ����D���b>
		// �ݨ����D����
		if (!StringUtils.hasText(questionnaireTitle)) {	
			return "*����J�ݨ����D*";
		// �ݨ����D�֩󤭭Ӧr
		} else if (questionnaireTitle.length() < 5) {
			return "*�ݨ����D�ܤ֭n��5�Ӧr*";
		}

		// <�ݨ����e���b>
		// �ݨ����e����
		if (!StringUtils.hasText(questionnaireBody)) {	
			return "*����J�ݨ����e*";
		// �ݨ����e�p��20�Ӧr
		} else if (questionnaireBody.length() < 20) {
			return "*�ݨ����e�ܤ֭n��20�Ӧr*";
		}

		// <�_�l�B����������b>
		// �_�l�������
		if (!StringUtils.hasText(startDate)) {
			return "*������J�}�l���*";
		// �����������
		} else if (!StringUtils.hasText(endDate)) {
			return "*������J�������*";
		}
		return "";
	}
}
