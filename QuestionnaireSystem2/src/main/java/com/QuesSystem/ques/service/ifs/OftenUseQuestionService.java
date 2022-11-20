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
	 * @param oftenUseTitle �`�ΰ��D���D
	 * @return oftenuseQuestion
	 */
	public Page<OftenUseQuestion> searchOftenUseByoftenuseTitle(int pageNum, int pageSize, String Title);
	
	/*
	 * �R���`�ΰ��D
	 */
	public void deleteOftenUseQuestion(String[] Id);
	
	/*
	 * �s�W�`�ΰ��D���b(�bQuestionController�̪�61��Q�ϥ�)
	 */
	public String ErrorMsg(String Title, String Choices, int Type);
}
