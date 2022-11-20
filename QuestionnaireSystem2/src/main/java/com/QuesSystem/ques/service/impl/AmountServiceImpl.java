package com.QuesSystem.ques.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.QuesSystem.ques.entity.Answer;
import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.model.TotalAnswerValue;
import com.QuesSystem.ques.service.ifs.AmountService;

@Service
public class AmountServiceImpl implements AmountService {

	@Override
	public void answerTrim(List<Answer> answerList, int questionNumber, List<String> resultList) {
		for (Answer answer : answerList) {
			if (answer.getAnswerNumber() == (questionNumber)) {
				String[] ansArr = answer.getAnswer().split(";");
				for (String AnsSplit : ansArr) {
					if (!AnsSplit.equals("")) {
						resultList.add(AnsSplit.trim());
					}
				}
			}
		}
	}

	@Override
	public List<TotalAnswerValue> getTotalAnswers(List<Question> quesList, List<Answer> ansList) {
		List<TotalAnswerValue> totalAnswerList = new ArrayList<>();
		for (Question question : quesList) {
			if (question.getQuestionType() == 2) {
				Map<String, Integer> totalMap = new HashMap<>();
				TotalAnswerValue totalAns = new TotalAnswerValue();
				totalAns.setTitle(question.getQuestionChoices());
				totalMap.put("-¸ê®Æ¤£¶ñ¼g-", 0);
				totalAns.setChoicesMap(totalMap);
				totalAnswerList.add(totalAns);
			} else {
				TotalAnswerValue totalAnswer = new TotalAnswerValue();
				totalAnswer.setTitle(question.getQuestionChoices());
				Map<String, Integer> totalMap = new HashMap<>();
				int count = 0;
				List<String> totalString = new ArrayList<>();
				answerTrim(ansList, question.getQuestionNumber(), totalString);
				for (String string : totalString) {
					count = Collections.frequency(totalString, string);
					totalMap.put(string, count);
				}
				totalAnswer.setChoicesMap(totalMap);
				totalAnswerList.add(totalAnswer);
			}
		}
		return totalAnswerList;
	}
}
