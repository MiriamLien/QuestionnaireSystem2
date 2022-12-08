package com.QuesSystem.ques.service.ifs;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;

import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.entity.Userinfo;
import com.QuesSystem.ques.model.Answers;
import com.QuesSystem.ques.model.QuestionnaireInfo;

public interface AnswerService {

	/*
	 * (後台)利用分頁取得填寫資料
	 */
	public Page<Userinfo> getAnswersByPageList(int pageNum, int pageSize);

	/*
	 * 建立問卷資訊(所有問題及答案)
	 */
	public QuestionnaireInfo createQuestionnaireInfo(String userId);

	/*
	 * 從回答陣列中把答案切割成單一個單一個答案
	 */
	public List<Answers> seperateAnswer(Questionnaire questionnaireId);
}
