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
	 * 取得問卷清單
	 */
	@Override
	public List<Questionnaire> getQuestionnaireList() {
		List<Questionnaire> questionnaireList = questionnaireDao.findAll();
		// 判斷問卷清單是否為空
		if (questionnaireList.isEmpty()) {
			return new ArrayList<>();
		}
		return questionnaireList;
	}

	/*
	 * 分頁查詢(前台)
	 */
	@Override
	public Page<Questionnaire> getQuestionnaireByPageListFront(int pageNum, int pageSize) {
		// 1.使用 Sort.by()先進行排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.自定義的 --> Page<> 的方法
		Page<Questionnaire> questionnaires = questionnaireDao.findByPageableFront(pageable, true);
		// 4.回傳questionnaires
		return questionnaires;
	}

	/*
	 * (藉由問卷標題)搜尋問卷(前台)
	 */
	@Override
	public Page<Questionnaire> searchByQuestionnaireTitleFront(int pageNum, int pageSize, String questionnaireTitle) {
		// 使用 Sort.by()先進行排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.自訂義的 --> Page<> 的方法(使用問卷標題搜尋)
		Page<Questionnaire> questionnaires = questionnaireDao.findByQuestionnaireTitleFront(pageable, questionnaireTitle, true);
		// 4.回傳questionnaires
		return questionnaires;
	}

	/*
	 * 分頁查詢(後台)
	 */
	@Override
	public Page<Questionnaire> getQuestionnaireByPageList(int pageNum, int pageSize) {
		// 1.使用 Sort.by()先進行排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.自定義的 --> Page<> 的方法
		Page<Questionnaire> questionnaires = questionnaireDao.findAll(pageable);
		// 4.回傳questionnaires
		return questionnaires;
	}

	/*
	 * (藉由問卷標題)搜尋問卷(後台)
	 */
	@Override
	public Page<Questionnaire> searchByQuestionnaireTitle(int pageNum, int pageSize, String questionnaireTitle) {
		// 使用 Sort.by()先進行排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.自訂義的 --> Page<> 的方法(使用問卷標題搜尋)
		Page<Questionnaire> questionnaires = questionnaireDao.findByQuestionnaireTitle(pageable, questionnaireTitle);
		// 4.回傳questionnaires
		return questionnaires;
	}

	/*
	 * (藉由起始&結束日期)搜尋問卷
	 */
	@Override
	public Page<Questionnaire> searchQuestionnaireByAllTime(int pageNum, int pageSize, Date startDate, Date endDate) {
		// 1.使用 Sort.by()先進行排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.自定義的 --> Page<> 的方法(使用開始日期及結束日期搜尋)
		Page<Questionnaire> questionnaires = questionnaireDao.findByStartDateAndEndDate(pageable, startDate, endDate);
		// 4.回傳questionnaires
		return questionnaires;
	}
	
	/*
	 * 刪除問卷
	 * 使用questionnaireId
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
	 * 新增問卷防呆(檢查問卷送出前檢查有無錯誤)
	 */
	@Override
	public String ErrorMsg(String questionnaireTitle, String questionnaireBody, String startDate, String endDate) {
		// <問卷標題防呆>
		// 問卷標題為空
		if (!StringUtils.hasText(questionnaireTitle)) {	
			return "*未輸入問卷標題*";
		// 問卷標題少於五個字
		} else if (questionnaireTitle.length() < 5) {
			return "*問卷標題至少要有5個字*";
		}

		// <問卷內容防呆>
		// 問卷內容為空
		if (!StringUtils.hasText(questionnaireBody)) {	
			return "*未輸入問卷內容*";
		// 問卷內容小於20個字
		} else if (questionnaireBody.length() < 20) {
			return "*問卷內容至少要有20個字*";
		}

		// <起始、結束日期防呆>
		// 起始日期為空
		if (!StringUtils.hasText(startDate)) {
			return "*必須輸入開始日期*";
		// 結束日期為空
		} else if (!StringUtils.hasText(endDate)) {
			return "*必須輸入結束日期*";
		}
		return "";
	}
}
