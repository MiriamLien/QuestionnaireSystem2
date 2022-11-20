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
	 * �s�W�`�ΰ��D���b
	 */
	@Override
	public String ErrorMsg(String title, String choices, int type) {
		// <���D���D���b>
		// ���D���D����
		if (!StringUtils.hasText(title)) {
			return "*����J���D���D*";
		  // ���D�ܤ֭n���T�Ӧr
		} else if (title.length() < 3) {
			return "*���D���D�֩�3�Ӧr*";
		}

		// <���D���קY���D�������b>
		// ������ݭn��J���D����
		if (type == QuestionType.�����.getCode() && choices == null) {
			return "*��������D�M���׿�J����*";
		// ������ݭn��J���D����
		} else if (type == QuestionType.�����.getCode() && choices.length() < 6) {
			return "*��������D��J����,���צܤֻݭn��4�Ӧr*";
		}

		// �ƿ����ݭn��J���D����
		if (type == QuestionType.�ƿ���.getCode() && choices == null) {
			return "*��������D�M���׿�J����*";
		// ������ݭn��J���D����
		} else if (type == QuestionType.�ƿ���.getCode() && choices.length() < 8) {
			return "*��������D��J����,���צܤֻݭn��4�Ӧr*";
		}
		
		// ��r������ݭn��J���D����
		if (type == QuestionType.��r���.getCode() && choices != null) {
			return "*��ܤ�r������ݭn��J�^�����*";
		}
		return "";
	}

	/*
	 * ���o�`�ΰ��D
	 * @param pageNum
	 * @param pageSize
	 * @return oftenuseQues
	 */
	@Override
	public Page<OftenUseQuestion> getOftenUseByPageList(int pageNum, int pageSize) {
		// 1.�ϥ� Sort.by()���i��Ƨ�
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.�A������
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.�۩w�q�� --> Page<> ����k(�C�X���O�`�ΰ��D��)
		Page<OftenUseQuestion> oftenuseQues = oftenUseQuestionDao.findAll(pageable);
		// 4.�^��oftenuseQues
		return oftenuseQues;
	}

	/*
	 * (�ǥѼ��D)�j�M�`�ΰ��D
	 * @param pageNum
	 * @param pageSize
	 * @param oftenuseTitle �`�ΰ��D���D
	 * @return oftenuseQues
	 */
	@Override
	public Page<OftenUseQuestion> searchOftenUseByoftenuseTitle(int pageNum, int pageSize, String title) {
		// �ϥ� Sort.by()���i��Ƨ�
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.�A������
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.�۩w�q�� --> Page<> ����k(�ϥα`�ΰ��D���D�j�M)
		Page<OftenUseQuestion> oftenuseQues = oftenUseQuestionDao.findByOftenuseTitle(pageable, title);
		// 4.�^��oftenuseQues
		return oftenuseQues;
	}

	/*
	 * �R���`�ΰ��D
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
