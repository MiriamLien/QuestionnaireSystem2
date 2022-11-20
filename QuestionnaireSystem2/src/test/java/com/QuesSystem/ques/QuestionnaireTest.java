package com.QuesSystem.ques;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;

@SpringBootTest
public class QuestionnaireTest {

	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Test
	public void addQuestionnaire() throws ParseException {
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setQuestionnaireTitle("test1");
		questionnaire.setQuestionnaireBody("contents1");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = formatter.parse("2022-03-05");
		questionnaire.setStartDate(sDate);
		Date eDate = formatter.parse("2022-12-01");
		questionnaire.setEndDate(eDate);
		questionnaire.setQuestionnaireStates(true);
		questionnaire.setCreateDate(new Date());
		questionnaireDao.save(questionnaire);
		
		questionnaire = new Questionnaire();
		questionnaire.setQuestionnaireTitle("test2");
		questionnaire.setQuestionnaireBody("contents2");
		Date sDate2 = formatter.parse("2022-05-01");
		questionnaire.setStartDate(sDate2);
		Date eDate2 = formatter.parse("2022-10-22");
		questionnaire.setEndDate(eDate2);
		questionnaire.setQuestionnaireStates(true);
		questionnaire.setCreateDate(new Date());
		questionnaireDao.save(questionnaire);

//		Question question = new Question();
//		question.setQuestionnaireId(questionnaire);
//		question.setQuestionTitle("最喜歡的顏色是？");
//		question.setQuestionChoices("藍色;紅色;綠色;黃色;紫色;金色;黑色;白色");
//		question.setQuestionType(1);
//		question.setMustKeyin(true);
//		question.setQuestionNumber(0);
//		questionDao.save(question);
//		
//		question = new Question();
//		question.setQuestionnaireId(questionnaire);
//		question.setQuestionTitle("目前去過偏好或最想去哪一洲呢？");
//		question.setQuestionChoices("歐洲;美洲;亞洲;非洲;大洋洲;南極洲");
//		question.setQuestionType(2);
//		question.setMustKeyin(true);
//		question.setQuestionNumber(1);
//		questionDao.save(question);
	}
}
