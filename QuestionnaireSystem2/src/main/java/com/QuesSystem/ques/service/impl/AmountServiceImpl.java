package com.QuesSystem.ques.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.model.Answers;
import com.QuesSystem.ques.model.ChoicesInfo;
import com.QuesSystem.ques.model.TotalAnswerVal;
import com.QuesSystem.ques.model.TotalAnswerVal2;
import com.QuesSystem.ques.service.ifs.AmountService;

@Service
public class AmountServiceImpl implements AmountService {

	// ��x�έp��
	@Override
	public List<TotalAnswerVal> getTotalAnswers(List<Question> questionList,
												List<Answers> answerList) {
		
		List<TotalAnswerVal> totalAnswerList = new ArrayList<>();

		for (Question question : questionList) {
			// �p�G���D�O���񶵥�
			if (question.isMustKeyin() == true) {
				// �S�O���D��������r���(2)
				if (question.getQuestionType() == 2) {

					Map<String, Integer> totalMap = new HashMap<>();
					TotalAnswerVal totalAns = new TotalAnswerVal();

					totalAns.setTitle(question.getQuestionTitle() + "?(*����)");
					totalMap.put("-��Ƥ���g-", 0);
					totalAns.setChoicesMap(totalMap);
					totalAnswerList.add(totalAns);
				// �p�G���D��������/�ƿ���(0��1)
				} else {

					TotalAnswerVal totalAnswer = new TotalAnswerVal();
					// �]�w���D(�q���D�����D���D���o��T)
					totalAnswer.setTitle(question.getQuestionTitle() + "?(*����)");

					Map<String, Integer> totalMap = new HashMap<>();
					int count = 0;
					List<String> totalString = new ArrayList<>();

					// �ϥΤ�k(���ξ��^������ӵ���)
					answerTrim(answerList, question.getQuestionNumber(), totalString);

					for (String string : totalString) {

						count = Collections.frequency(totalString, string);
						totalMap.put(string, count);
					}

					totalAnswer.setChoicesMap(totalMap);
					totalAnswerList.add(totalAnswer);
				}
			}
		}

		return totalAnswerList;
	}

	// �e�x�έp��
	@Override
	public List<TotalAnswerVal2> getTotals(List<Question> questionList,
										   List<Answers> answerList) {
		
		List<TotalAnswerVal2> totalAnswerList = new ArrayList<>();
		for (Question question : questionList) {

			// �p�G���D��������r���(2)
			if (question.getQuestionType() == 2) {

				Set<ChoicesInfo> set = new HashSet<>();
				TotalAnswerVal2 totalAnswerVal2 = new TotalAnswerVal2();
				totalAnswerVal2.setQuestionTitle(question.getQuestionTitle());

				ChoicesInfo choicesInfo = new ChoicesInfo();
				choicesInfo.setChoices("��Ƥ��έp");
				choicesInfo.setCount(0);
				set.add(choicesInfo);

				totalAnswerVal2.setChoicesInfo(set);
				
				totalAnswerList.add(totalAnswerVal2);
				// �p�G���D��������/�ƿ���(0��1)
			} else {

				TotalAnswerVal2 totalAnswerVal2 = new TotalAnswerVal2();
				totalAnswerVal2.setQuestionTitle(question.getQuestionTitle());
				Set<ChoicesInfo> set = new HashSet<>();

				// �ŧi�ܼ�count����l�w�]�Ȭ�0
				int count = 0;
				List<String> totalString = new ArrayList<>();

				// �ϥΤ�k(���ξ��^������ӵ���)
				answerTrim(answerList, question.getQuestionNumber(), totalString);
				Set<String> newWords = new HashSet<String>(totalString);

				for (String word : newWords) {

					count = Collections.frequency(totalString, word);
					ChoicesInfo choicesInfo = new ChoicesInfo();
					choicesInfo.setChoices(word);
					choicesInfo.setCount(count);
					set.add(choicesInfo);
				}

				totalAnswerVal2.setChoicesInfo(set);
				totalAnswerList.add(totalAnswerVal2);
			}
		}

		return totalAnswerList;
	}

	// ��^�����}�C(��;�j�})���Φ���ӵ���
	@Override
	public void answerTrim(List<Answers> answerList,
						   int questionNumber,
						   List<String> resultList) {

		for (Answers answer : answerList) {
			
			// �p�G�^���������D�s���M�Ѽƪ��s���۵�����
			if (answer.getQuestionNumber().equals(questionNumber + "")) {

				// �i��r����ΡA��(�r��)�}�C�����^���ǥ�;���Φ���ӵ���
				String[] ansArr = answer.getAnswer().split(";");
				for (String ansSplit : ansArr) {

					if (!ansSplit.equals("")) {

						// �ϥ�trim()�h���r��(ansSplit)�Y�����ť�
						resultList.add(ansSplit.trim());
					}
				}
			}
		}
	}
}
