package com.QuesSystem.ques.service.ifs;

import org.springframework.data.domain.Page;

import com.QuesSystem.ques.entity.Userinfo;

public interface AnswerService {

	/*
	 * �Q�Τ������o��g���(��x)
	 */
	 public Page<Userinfo> getAnswersByPageList(int pageNum, int pageSize);
}
