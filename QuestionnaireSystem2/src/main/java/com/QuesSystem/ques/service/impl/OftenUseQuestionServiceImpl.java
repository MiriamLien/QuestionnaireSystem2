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

import com.QuesSystem.ques.entity.OftenUseQuestion;
import com.QuesSystem.ques.enums.QuestionType;
import com.QuesSystem.ques.repository.OftenUseQuestionDao;
import com.QuesSystem.ques.service.ifs.OftenUseQuestionService;

@Service
public class OftenUseQuestionServiceImpl implements OftenUseQuestionService {

	@Autowired
	private OftenUseQuestionDao oftenUseQuestionDao;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * 新增常用問題防呆
	 */
	@Override
	public String ErrorMsg(String title, String choices, int type) {
		// <問題標題防呆>
		// 問題標題為空
		if (!StringUtils.hasText(title)) {
			return "*未輸入問題標題*";
		  // 問題至少要有三個字
		} else if (title.length() < 3) {
			return "*問題標題少於3個字*";
		}

		// <問題答案即問題種類防呆>
		// 單選方塊需要輸入問題答案
		if (type == QuestionType.單選方塊.getCode() && choices == null) {
			return "*必須把問題和答案輸入完整*";
		// 單選方塊需要輸入問題答案
		} else if (type == QuestionType.單選方塊.getCode() && choices.length() < 6) {
			return "*必須把問題輸入完整,答案至少需要有4個字*";
		}

		// 複選方塊需要輸入問題答案
		if (type == QuestionType.複選方塊.getCode() && choices == null) {
			return "*必須把問題和答案輸入完整*";
		// 單選方塊需要輸入問題答案
		} else if (type == QuestionType.複選方塊.getCode() && choices.length() < 8) {
			return "*必須把問題輸入完整,答案至少需要有4個字*";
		}
		
		// 文字方塊不需要輸入問題答案
		if (type == QuestionType.文字方塊.getCode() && choices != null) {
			return "*選擇文字方塊不需要輸入回答欄位*";
		}
		return "";
	}

	/*
	 * 取得常用問題
	 * @param pageNum
	 * @param pageSize
	 * @return oftenuseQues
	 */
	@Override
	public Page<OftenUseQuestion> getOftenUseByPageList(int pageNum, int pageSize) {
		// 1.使用 Sort.by()先進行排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.自定義的 --> Page<> 的方法(列出不是常用問題的)
		Page<OftenUseQuestion> oftenuseQues = oftenUseQuestionDao.findAll(pageable);
		// 4.回傳oftenuseQues
		return oftenuseQues;
	}

	/*
	 * (藉由標題)搜尋常用問題
	 * @param pageNum
	 * @param pageSize
	 * @param oftenuseTitle 常用問題標題
	 * @return oftenuseQues
	 */
	@Override
	public Page<OftenUseQuestion> searchOftenUseByoftenuseTitle(int pageNum, int pageSize, String title) {
		// 使用 Sort.by()先進行排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.自定義的 --> Page<> 的方法(使用常用問題標題搜尋)
		Page<OftenUseQuestion> oftenuseQues = oftenUseQuestionDao.findByOftenuseTitle(pageable, title);
		// 4.回傳oftenuseQues
		return oftenuseQues;
	}

	/*
	 * 刪除常用問題
	 */
	@Override
	public void deleteOftenUseQuestion(String[] oftenuse) {
		try {
			for (String oftenuseId : oftenuse) {
				oftenUseQuestionDao.deleteById(oftenuseId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
