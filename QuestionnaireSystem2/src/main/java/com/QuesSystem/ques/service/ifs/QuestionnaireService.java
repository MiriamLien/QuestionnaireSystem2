package com.QuesSystem.ques.service.ifs;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.QuesSystem.ques.entity.Questionnaire;

public interface QuestionnaireService {

	/*
	 * ���o�ݨ��C��
	 */
	public List<Questionnaire> getQuestionnaireList();

	/*
	 * �R���ݨ�
	 */
	public void deleteQuestionnaire(String[] questionnaireId);

	/*
	 * �Q�Τ������o�ݨ��M��(�e�x)
	 */
	public Page<Questionnaire> getQuestionnaireByPageListFront(int pageNum, int pageSize);

	/**
	 * �ǥѰݨ����D�i��j�M(�e�x)
	 * @param pageNum
	 * @param pageSize
	 * @param questionnaireTitle �ݨ����D
	 * @return questionnaires
	 */
	public Page<Questionnaire> searchByQuestionnaireTitleFront(int pageNum, int pageSize, String questionnaireTitle);
	
	/**
	 * �ǥѰݨ����D�i��j�M(��x)
	 * @param pageNum
	 * @param pageSize
	 * @param questionnaireTitle
	 * @return
	 */
	public Page<Questionnaire> searchByQuestionnaireTitle(int pageNum, int pageSize, String questionnaireTitle);

	/*
	 * �Q�Τ������o�ݨ��M��(��x)
	 */
	public Page<Questionnaire> getQuestionnaireByPageList(int pageNum, int pageSize);

	/*
	 * �ǥѰ_�l&��������i��j�M
	 */
	public Page<Questionnaire> searchQuestionnaireByAllTime(int pageNum, int pageSize, Date startDate, Date endDate);

	/*
	 * �s�W�ݨ����b
	 */
	public String ErrorMsg(String questionnaireTitle, String questionnaireBody, String startDate, String endDate);
}
