package com.QuesSystem.ques.service.ifs;

import java.util.List;

import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.model.Answers;
import com.QuesSystem.ques.model.TotalAnswerVal;
import com.QuesSystem.ques.model.TotalAnswerVal2;

public interface AmountService {

	/*
	 * ��^�����}�C(��;�j�})���Φ���ӵ���
	 */
	public void answerTrim(List<Answers> answerList, int questionNumber, List<String> resultList);

	/*
	 * ���o�ϥΪ̿�ܪ��^��
	 */
	public List<TotalAnswerVal> getTotalAnswers(List<Question> quesList, List<Answers> ansList);

	public List<TotalAnswerVal2> getTotals(List<Question> quesList, List<Answers> ansList);
}
