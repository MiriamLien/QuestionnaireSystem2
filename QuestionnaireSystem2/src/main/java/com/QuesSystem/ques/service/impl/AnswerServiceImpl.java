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
	 * �����d�߶�g���
	 */
	@Override
	public Page<Userinfo> getAnswersByPageList(int pageNum, int pageSize) {
		// 1.�ϥ� Sort.by()���i��Ƨ�
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.�A������
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.�۩w�q�� --> Page<> ����k(�C�X���O�`�ΰ��D��)
		Page<Userinfo> userAnswers = userInfoDao.findAll(pageable);
		// 4.�^��userAnswers
		return userAnswers;
	}

}
