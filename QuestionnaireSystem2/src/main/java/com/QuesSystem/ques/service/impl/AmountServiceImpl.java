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

	// 後台統計頁
	@Override
	public List<TotalAnswerVal> getTotalAnswers(List<Question> questionList,
												List<Answers> answerList) {
		
		List<TotalAnswerVal> totalAnswerList = new ArrayList<>();

		for (Question question : questionList) {
			// 如果問題是必填項目
			if (question.isMustKeyin() == true) {
				// 又是問題類型為文字方塊(2)
				if (question.getQuestionType() == 2) {

					Map<String, Integer> totalMap = new HashMap<>();
					TotalAnswerVal totalAns = new TotalAnswerVal();

					totalAns.setTitle(question.getQuestionTitle() + "?(*必填)");
					totalMap.put("-資料不填寫-", 0);
					totalAns.setChoicesMap(totalMap);
					totalAnswerList.add(totalAns);
				// 如果問題類型為單/複選方塊(0或1)
				} else {

					TotalAnswerVal totalAnswer = new TotalAnswerVal();
					// 設定標題(從問題的問題標題取得資訊)
					totalAnswer.setTitle(question.getQuestionTitle() + "?(*必填)");

					Map<String, Integer> totalMap = new HashMap<>();
					int count = 0;
					List<String> totalString = new ArrayList<>();

					// 使用方法(切割整串回答為單個答案)
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

	// 前台統計頁
	@Override
	public List<TotalAnswerVal2> getTotals(List<Question> questionList,
										   List<Answers> answerList) {
		
		List<TotalAnswerVal2> totalAnswerList = new ArrayList<>();
		for (Question question : questionList) {

			// 如果問題類型為文字方塊(2)
			if (question.getQuestionType() == 2) {

				Set<ChoicesInfo> set = new HashSet<>();
				TotalAnswerVal2 totalAnswerVal2 = new TotalAnswerVal2();
				totalAnswerVal2.setQuestionTitle(question.getQuestionTitle());

				ChoicesInfo choicesInfo = new ChoicesInfo();
				choicesInfo.setChoices("資料不統計");
				choicesInfo.setCount(0);
				set.add(choicesInfo);

				totalAnswerVal2.setChoicesInfo(set);
				
				totalAnswerList.add(totalAnswerVal2);
				// 如果問題類型為單/複選方塊(0或1)
			} else {

				TotalAnswerVal2 totalAnswerVal2 = new TotalAnswerVal2();
				totalAnswerVal2.setQuestionTitle(question.getQuestionTitle());
				Set<ChoicesInfo> set = new HashSet<>();

				// 宣告變數count的初始預設值為0
				int count = 0;
				List<String> totalString = new ArrayList<>();

				// 使用方法(切割整串回答為單個答案)
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

	// 把回答的陣列(用;隔開)切割成單個答案
	@Override
	public void answerTrim(List<Answers> answerList,
						   int questionNumber,
						   List<String> resultList) {

		for (Answers answer : answerList) {
			
			// 如果回答內的問題編號和參數的編號相等的話
			if (answer.getQuestionNumber().equals(questionNumber + "")) {

				// 進行字串分割，把(字串)陣列內的回答藉由;分割成單個答案
				String[] ansArr = answer.getAnswer().split(";");
				for (String ansSplit : ansArr) {

					if (!ansSplit.equals("")) {

						// 使用trim()去除字串(ansSplit)頭尾的空白
						resultList.add(ansSplit.trim());
					}
				}
			}
		}
	}
}
