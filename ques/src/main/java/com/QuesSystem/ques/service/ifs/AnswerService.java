package com.QuesSystem.ques.service.ifs;

import org.springframework.data.domain.Page;

import com.QuesSystem.ques.entity.Userinfo;

public interface AnswerService {

	/*
	 * 利用分頁取得填寫資料(後台)
	 */
	 public Page<Userinfo> getAnswersByPageList(int pageNum, int pageSize);
}
