package com.QuesSystem.ques.service.ifs;

import org.springframework.data.domain.Page;

import com.QuesSystem.ques.entity.OftenUseQuestion;

public interface OftenUseQuestionService {

	/**
	 * @param pageNum
	 * @param pageSize
	 * @return oftenUseQuestion
	 */
	public Page<OftenUseQuestion> getOftenUseByPageList(int pageNum, int pageSize);

	/**
	 * @param pageNum
	 * @param pageSize
	 * @param oftenUseTitle 常用問題標題
	 * @return oftenuseQuestion
	 */
	public Page<OftenUseQuestion> searchOftenUseByoftenuseTitle(int pageNum, int pageSize, String Title);
	
	/*
	 * 刪除常用問題
	 */
	public void deleteOftenUseQuestion(String[] Id);
	
	/*
	 * 新增常用問題防呆(在QuestionController裡的61行被使用)
	 */
	public String ErrorMsg(String Title, String Choices, int Type);
}
