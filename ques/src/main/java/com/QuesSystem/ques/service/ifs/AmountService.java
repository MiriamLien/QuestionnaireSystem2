package com.QuesSystem.ques.service.ifs;

import java.util.List;

import com.QuesSystem.ques.entity.Answer;
import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.model.TotalAnswerValue;

public interface AmountService {

	public void answerTrim(List<Answer> answerList,int questionNumber, List<String> resultList);

	public List<TotalAnswerValue> getTotalAnswers(List<Question> quesList, List<Answer> ansList);
}
