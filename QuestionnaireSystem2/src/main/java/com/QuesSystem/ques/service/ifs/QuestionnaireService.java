package com.QuesSystem.ques.service.ifs;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import com.QuesSystem.ques.entity.Questionnaire;

public interface QuestionnaireService {

	/*
	 * 取得問卷列表
	 */
	public List<Questionnaire> getQuestionnaireList();
	
	/**
	 * 刪除問卷
	 * @param questionnaireId
	 */
	public void deleteQuestionnaire(String[] questionnaireId);
	
	/**
	 * 新增問卷防呆
	 * @param questionnaireTitle
	 * @param questionnaireBody
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String ErrorMsg(String questionnaireTitle,String questionnaireBody, String startDate, String endDate);
	
	/**
	 * 利用分頁取得問卷清單(前台)
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Questionnaire> getQuestionnaireByPageListFront(int pageNum, int pageSize);
	
	/**
	 * 藉由問卷標題進行搜尋(前台)
	 * @param pageNum
	 * @param pageSize
	 * @param questionnaireTitle 問卷標題
	 * @return questionnaires
	 */
	public Page<Questionnaire> searchQuestionnaireByQuesTitleFront(int pageNum, int pageSize, String questionnaireTitle);

	/**
	 * 利用分頁取得問卷清單(後台)
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Questionnaire> getQuestionnaireByPageList(int pageNum,int pageSize);

	/**
	 * 藉由問卷標題進行搜尋
	 * @param pageNum
	 * @param pageSize
	 * @param questionnaireTitle
	 * @return
	 */
	public Page<Questionnaire> searchQuestionnaireByQuesTitle(int pageNum,int pageSize, String questionnaireTitle);
	
	/**
	 * 藉由起始&結束日期進行搜尋
	 * @param pageNum
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Page<Questionnaire> searchQuestionnaireByAllTime(int pageNum,int pageSize, Date startDate, Date endDate);
}
