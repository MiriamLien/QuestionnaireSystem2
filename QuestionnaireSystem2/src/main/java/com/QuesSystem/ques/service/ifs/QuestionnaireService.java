package com.QuesSystem.ques.service.ifs;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import com.QuesSystem.ques.entity.Questionnaire;

public interface QuestionnaireService {

	/*
	 * ���o�ݨ��C��
	 */
	public List<Questionnaire> getQuestionnaireList();
	
	/**
	 * �R���ݨ�
	 * @param questionnaireId
	 */
	public void deleteQuestionnaire(String[] questionnaireId);
	
	/**
	 * �s�W�ݨ����b
	 * @param questionnaireTitle
	 * @param questionnaireBody
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String ErrorMsg(String questionnaireTitle,String questionnaireBody, String startDate, String endDate);
	
	/**
	 * �Q�Τ������o�ݨ��M��(�e�x)
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Questionnaire> getQuestionnaireByPageListFront(int pageNum, int pageSize);
	
	/**
	 * �ǥѰݨ����D�i��j�M(�e�x)
	 * @param pageNum
	 * @param pageSize
	 * @param questionnaireTitle �ݨ����D
	 * @return questionnaires
	 */
	public Page<Questionnaire> searchQuestionnaireByQuesTitleFront(int pageNum, int pageSize, String questionnaireTitle);

	/**
	 * �Q�Τ������o�ݨ��M��(��x)
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Questionnaire> getQuestionnaireByPageList(int pageNum,int pageSize);

	/**
	 * �ǥѰݨ����D�i��j�M
	 * @param pageNum
	 * @param pageSize
	 * @param questionnaireTitle
	 * @return
	 */
	public Page<Questionnaire> searchQuestionnaireByQuesTitle(int pageNum,int pageSize, String questionnaireTitle);
	
	/**
	 * �ǥѰ_�l&��������i��j�M
	 * @param pageNum
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Page<Questionnaire> searchQuestionnaireByAllTime(int pageNum,int pageSize, Date startDate, Date endDate);
}
