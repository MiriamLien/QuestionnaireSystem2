package com.QuesSystem.ques.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.entity.Userinfo;
import com.QuesSystem.ques.model.Answers;
import com.QuesSystem.ques.model.QuestionnaireInfo;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.UserinfoDao;
import com.QuesSystem.ques.service.ifs.AnswerService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private UserinfoDao userInfoDao;

	@Autowired
	private QuestionDao questionDao;

	// 藉由分頁查詢使用者填寫的資料
	@Override
	public Page<Userinfo> getAnswersByPageList(int pageNum, int pageSize) {
		// 1.使用 Sort.by()，根據創建日期進行逆序排序
		Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
		// 2.再做分頁
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(order));
		// 3.自定義的 --> Page<> 的方法(列出不是常用問題的)
		Page<Userinfo> userAnswers = userInfoDao.findAll(pageable);
		// 4.回傳userAnswers
		return userAnswers;
	}

	// 建立問卷資訊(使用者及回答資訊)
	@Override
	public QuestionnaireInfo createQuestionnaireInfo(String userId) {

		Gson gson = new Gson();
		QuestionnaireInfo questionnaireResult = new QuestionnaireInfo();
		List<Answers> UserAnsList = new ArrayList<Answers>();
		Questionnaire questionnaireId;
		Answers[] ans;

		// 使用使用者ID取得使用者資訊(放入userinfoOp)
		Optional<Userinfo> userinfoOp = userInfoDao.findById(userId);

		// 如果userinfoOp不為空的話
		if (!userinfoOp.isEmpty()) {
			// 取得使用者資訊
			Userinfo userinfo = userinfoOp.get();
			// 取得問卷ID
			questionnaireId = userinfo.getQuestionnaireId();
			// 取得問題清單
			List<Question> quesList = questionDao.findListByQuestionnaireId(questionnaireId);
			// 設定問題
			questionnaireResult.setQuestions(quesList);

			ans = gson.fromJson(userinfo.getAnswer(), Answers[].class);
			for (Answers a : ans) {
				
				UserAnsList.add(a);
			}
			
			// 設定回答
			questionnaireResult.setAnswers(UserAnsList);
		}

		// 回傳questionnaireResult
		return questionnaireResult;
	}

	// 從回答陣列中把答案切割成單一個單一個答案
	//@JsonInclude(JsonInclude.Include.NON_NULL)
	@Override
	public List<Answers> seperateAnswer(Questionnaire questionnaireId) {
		
		Gson gson = new Gson();
		List<Answers> userAnsList = new ArrayList<Answers>();
		Answers[] answerList;
		
		// 藉由問卷ID(questionnaireId)取得所有使用者資料(清單)
		List<Userinfo> userInfoList = userInfoDao.findByQuestionnaireId(questionnaireId);
		for(Userinfo userInfo : userInfoList) {
			
			answerList = gson.fromJson(userInfo.getAnswer(), Answers[].class);
			for(Answers answers : answerList) {
				
			   userAnsList.add(answers);
			}
		}
		
		return userAnsList;		
	}
}