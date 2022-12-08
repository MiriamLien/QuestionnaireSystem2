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

	// 取得所有問卷
	@Override
	public List<Questionnaire> getQuestionnaireList() {
		// 取得所有問卷
		List<Questionnaire> questionnaireList = questionnaireDao.findAll();

		// 如果questionnaireList為空，則回傳新的陣列
		if (questionnaireList.isEmpty()) {
			return new ArrayList<>();
		}

		// questionnaireList有值的話，則進行回傳
		return questionnaireList;
	}

	// 刪除問卷(使用questionnaireId)
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

	// <新增問卷防呆>
	@Override
	public String ErrorMsg(String questionnaireTitle, String questionnaireBody, String startDate, String endDate) {

		// 若問卷標題為空
		if (!StringUtils.hasText(questionnaireTitle)) {

			return AlertMessage.QuestionnaireMsg.No_Title;
		// 若問卷標題少於5個字
		} else if (questionnaireTitle.length() < 5) {

			return AlertMessage.QuestionnaireMsg.Title_AtLeastFive;
		}

		// 若問卷內容為空
		if (!StringUtils.hasText(questionnaireBody)) {

			return AlertMessage.QuestionnaireMsg.No_Body;
		// 若問卷內容少於20個字
		} else if (questionnaireBody.length() < 20) {

			return AlertMessage.QuestionnaireMsg.Body_AtLeastTwenty;
		}

		// 若開始日期為空
		if (!StringUtils.hasText(startDate)) {

			return AlertMessage.QuestionnaireMsg.No_StartDate;
		// 若結束日期為空
		} else if (!StringUtils.hasText(endDate)) {

			return AlertMessage.QuestionnaireMsg.No_EndDate;
		}

		return "";
	}

	// (前台)使用分頁進行查詢
	@Override
	public Page<Questionnaire> getQuestionnaireByPageListFront(int pageNum, int pageSize) {
		// 1. 使用 Sort.by()，根據創建日期進行(逆序)排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2. 再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3. 自定義的 --> Page<> 的方法(列出不是常用問題的)
		Page<Questionnaire> questionnaires = questionnaireDao.findByPageableFront(pageable, true);
		// 4. 回傳questionnaires
		return questionnaires;
	}

	// (前台)使用分頁進行查詢
	@Override
	public Page<Questionnaire> searchQuestionnaireByQuesTitleFront(int pageNum, int pageSize,
			String questionnaireTitle) {
		// 1. 使用 Sort.by()，根據創建日期進行(逆序)排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2. 再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3. 自定義的 --> Page<> 的方法(使用問卷標題進行搜尋)
		Page<Questionnaire> questionnaires = questionnaireDao.findByQuesTitleFront(pageable, questionnaireTitle, true);
		// 4. 回傳questionnaires
		return questionnaires;
	}

	// (後台)使用分頁進行查詢
	@Override
	public Page<Questionnaire> getQuestionnaireByPageList(int pageNum, int pageSize) {
		// 1. 使用 Sort.by()，根據創建日期進行(逆序)排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2. 再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3. 自定義的 --> Page<> 的方法(列出不是常用問題的)
		Page<Questionnaire> questionnaires = questionnaireDao.findAll(pageable);
		// 4. 回傳questionnaires
		return questionnaires;
	}

	// (後台)使用分頁進行查詢
	@Override
	public Page<Questionnaire> searchQuestionnaireByAllTime(int pageNum, int pageSize, Date startDate, Date endDate) {
		// 1. 使用 Sort.by()，根據創建日期進行(逆序)排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2. 再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3. 自定義的 --> Page<> 的方法(使用開始及結束日期進行搜尋)
		Page<Questionnaire> questionnaires = questionnaireDao.findByStartDateAndEndDate(pageable, startDate, endDate);
		// 4. 回傳questionnaires
		return questionnaires;
	}

	// (後台)使用分頁進行查詢
	@Override
	public Page<Questionnaire> searchQuestionnaireByQuesTitle(int pageNum, int pageSize, String questionnaireTitle) {
		// 1. 使用 Sort.by()，根據創建日期進行(逆序)排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2. 再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3. 自定義的 --> Page<> 的方法(使用問卷標題進行搜尋)
		Page<Questionnaire> questionnaires = questionnaireDao.findByQuesTitle(pageable, questionnaireTitle);
		// 4. 回傳questionnaires
		return questionnaires;
	}
}
