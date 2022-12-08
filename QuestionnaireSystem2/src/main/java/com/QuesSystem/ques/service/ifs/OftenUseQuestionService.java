package com.QuesSystem.ques.service.ifs;

import org.springframework.data.domain.Page;

import com.QuesSystem.ques.entity.OftenUseQuestion;

public interface OftenUseQuestionService {
	
	/*
	 * 刪除常用問題
	 */
	public void deleteOftenUseQuestion(String[] Id);
	
	/*
	 * 新增常用問題防呆
	 */
	public String ErrorMsg(String oftenuseTitle, String oftenuseChoices, int oftenuseType);

	/**
	 * 取得常用問題
	 * @param pageNum
	 * @param pageSize
	 * @return oftenuseQues
	 */
	public Page<OftenUseQuestion> getOftenUseByPageList(int pageNum, int pageSize);

	/**
	 * 搜尋常用問題標題
	 * @param pageNum 頁碼
	 * @param pageSize 筆數
	 * @param oftenuseTitle 常用問題標題
	 * @return oftenuseQues
	 */
	public Page<OftenUseQuestion> searchOftenUseByoftenuseTitle(int pageNum, int pageSize, String oftenuseTitle);
}
