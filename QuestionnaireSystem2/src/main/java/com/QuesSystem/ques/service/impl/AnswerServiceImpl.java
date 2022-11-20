package com.QuesSystem.ques.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.QuesSystem.ques.entity.Userinfo;
import com.QuesSystem.ques.repository.UserinfoDao;
import com.QuesSystem.ques.service.ifs.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private UserinfoDao userInfoDao;

	/*
	 * 分頁查詢填寫資料
	 */
	@Override
	public Page<Userinfo> getAnswersByPageList(int pageNum, int pageSize) {
		// 1.使用 Sort.by()先進行排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.自定義的 --> Page<> 的方法(列出不是常用問題的)
		Page<Userinfo> userAnswers = userInfoDao.findAll(pageable);
		// 4.回傳userAnswers
		return userAnswers;
	}

}
