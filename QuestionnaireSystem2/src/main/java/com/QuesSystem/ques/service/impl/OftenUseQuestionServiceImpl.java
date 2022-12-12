package com.QuesSystem.ques.service.impl;

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
import com.QuesSystem.ques.entity.OftenUseQuestion;
import com.QuesSystem.ques.enums.QuestionType;
import com.QuesSystem.ques.repository.OftenUseQuestionDao;
import com.QuesSystem.ques.service.ifs.OftenUseQuestionService;

@Service
public class OftenUseQuestionImpl implements OftenUseQuestionService {

	@Autowired
	private OftenUseQuestionDao oftenUseQuestionDao;

	private Logger logger = LoggerFactory.getLogger(getClass());

	// <新增常用問題防呆>
	@Override
	public String ErrorMsg(String oftenuseTitle, String oftenuseChoices, int oftenuseType) {
		// <常用問題標題防呆>
		// 若問題標題為空
		if (!StringUtils.hasText(oftenuseTitle)) {
			// 回傳提示訊息
			return AlertMessage.QuestionMsg.No_QuesTitle;
			// 若問題標題少於三個字
		} else if (oftenuseTitle.length() < 3) {
			// 回傳提示訊息
			return AlertMessage.QuestionMsg.Title_AtLeastThree;
		}

		// <問題答案及問題種類防呆>
		// 單選方塊必須輸入問題及答案
		if (oftenuseType == QuestionType.單選方塊.getCode() && oftenuseChoices == null) {

			return AlertMessage.QuestionMsg.Must_QuesAndAns;
			// 或是單選方塊必須輸入問題外, 答案也至少要有6個字
		} else if (oftenuseType == QuestionType.單選方塊.getCode() && oftenuseChoices.length() < 6) {

			return AlertMessage.QuestionMsg.Radio_Must_QuesAndAnsMustSix;
		}

		// 複選方塊必須輸入問題及答案
		if (oftenuseType == QuestionType.複選方塊.getCode() && oftenuseChoices == null) {

			return AlertMessage.QuestionMsg.Must_QuesAndAns;
			// 或是複選方塊必須輸入問題外, 答案也至少要有8個字
		} else if (oftenuseType == QuestionType.複選方塊.getCode() && oftenuseChoices.length() < 8) {

			return AlertMessage.QuestionMsg.Check_Must_QuesAndAnsMustEight;
		}

		// <判斷文字方塊: 文字方塊必須輸入問題，但不需要輸入答案>
		if (oftenuseType == QuestionType.文字方塊.getCode() && oftenuseChoices != null) {

			return AlertMessage.QuestionMsg.TextBox_MustQues_NoAns;
		}

		return "";
	}

	/**
	 * @param pageNum		頁碼
	 * @param pageSize      筆數
	 * @return oftenuseQues
	 */
	@Override
	public Page<OftenUseQuestion> getOftenUseByPageList(int pageNum, int pageSize) {
		// 1. 使用 Sort.by()，根據創建日期進行(逆序)排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2. 再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3. 自定義的 --> Page<> 的方法(列出不是常用問題的)
		Page<OftenUseQuestion> oftenuseQues = oftenUseQuestionDao.findAll(pageable);
		// 4. 回傳oftenuseQues
		return oftenuseQues;
	}

	/**
	 * @param pageNum       頁碼
	 * @param pageSize      筆數
	 * @param oftenuseTitle 常用問題標題
	 * @return oftenuseQues
	 */
	@Override
	public Page<OftenUseQuestion> searchOftenUseByoftenuseTitle(int pageNum, int pageSize, String oftenuseTitle) {
		// 1. 使用 Sort.by()，根據創建日期進行(逆序)排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2. 再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3. 自定義的 --> Page<> 的方法(使用常用問題標題進行搜尋)
		Page<OftenUseQuestion> oftenuseQues = oftenUseQuestionDao.findByOftenuseTitle(pageable, oftenuseTitle);
		// 4. 回傳oftenuseQues
		return oftenuseQues;
	}

	@Override
	public void deleteOftenUseQuestion(String[] oftenuseIds) {
		
		try {

			for (String oftenuseId : oftenuseIds) {
				
				oftenUseQuestionDao.deleteById(oftenuseId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
