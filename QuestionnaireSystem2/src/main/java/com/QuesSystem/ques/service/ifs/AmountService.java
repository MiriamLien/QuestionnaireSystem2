package com.QuesSystem.ques.service.ifs;

import java.util.List;

import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.model.Answers;
import com.QuesSystem.ques.model.TotalAnswerVal;
import com.QuesSystem.ques.model.TotalAnswerVal2;

public interface AmountService {

	/*
	 * 把回答的陣列(用;隔開)切割成單個答案
	 */
	public void answerTrim(List<Answers> answerList, int questionNumber, List<String> resultList);

	/*
	 * 取得使用者選擇的回答
	 */
	public List<TotalAnswerVal> getTotalAnswers(List<Question> quesList, List<Answers> ansList);

	public List<TotalAnswerVal2> getTotals(List<Question> quesList, List<Answers> ansList);
}
