package com.QuesSystem.ques.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.QuesSystem.ques.entity.Answer;
import com.QuesSystem.ques.entity.Question;
import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.entity.Userinfo;
import com.QuesSystem.ques.model.AnswerDetailToCSV;
import com.QuesSystem.ques.repository.AnswerDao;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.repository.UserinfoDao;

@Controller
public class AnswerController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private UserinfoDao userinfoDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private QuestionnaireDao questionnaireDao;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/answerlisttoCSV")
	public void exportToCSV(HttpServletResponse response, HttpSession session, List<Question> queslist,
			List<Answer> anslist, List<Userinfo> userlist) throws IOException {

		// 1. 設定匯出檔案格式
		response.setContentType("text/csv");

		response.setCharacterEncoding("UTF-8");
		// 2. 設定時間格式
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		// 3. 設定時間
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		// 5. File檔命名
		String headerValue = "attachment; filename=questionnaires" + currentDateTime + ".csv";
		response.setHeader(headerKey, headerValue);

		Questionnaire changeques = (Questionnaire) session.getAttribute("changeques");

		List<AnswerDetailToCSV> answerDetailToCSVList = new ArrayList<>();

		for (var userinfo : userlist) {
			AnswerDetailToCSV answerDetailToCSV = new AnswerDetailToCSV();
			for (var answer : anslist) {
				List<Answer> eachAnsList = anslist;
				answerDetailToCSV.setAnswerNumber(answer.getAnswerNumber());
				answerDetailToCSV.setAnswer(answer.getAnswer());
			}
			for (var question : queslist) {
				List<Question> eachquesList = queslist;
				answerDetailToCSV.setQuestionTitle(question.getQuestionTitle());
				answerDetailToCSV.setQuestionChoices(question.getQuestionChoices());
				answerDetailToCSV.setMustKeyin(String.valueOf(question.isMustKeyin()));
			}

			answerDetailToCSV.setName(userinfo.getName());
			answerDetailToCSV.setAge(userinfo.getAge());
			answerDetailToCSV.setEmail(userinfo.getEmail());
			answerDetailToCSV.setPhone(userinfo.getPhone());
			answerDetailToCSV.setCreateDate(userinfo.getCreateDate());

//			List<Userinfo> list = userinfoDao.findByQuestionnaireId(changeques);
//			List<Question> queslist = questionDao.findListByQuestionnaireId(changeques);
//			List<Answer> anslist = answerDao.findByQuestionnaireId(changeques);
//			List<String> resultlist = new ArrayList<>();

//				for (Userinfo userinfo : list) {
//					String userinfoString = "";
//					userinfoString += userinfo.getName() + userinfo.getPhone() + userinfo.getEmail() + userinfo.getAge() + 
//							    userinfo.getCreateDate();
//					for (Question ques : queslist) {
//						for (int i = 0; i < ques.getQuestionChoices().split(";").length; i++) {
//							userinfoString += ques.getQuestionChoices() + (i + 1);
//							for (Answer ans : anslist) {
//								userinfoString += ans.getAnswer();
//								resultlist.add(userinfoString);
//							}
//						}
//					}
//				}

//			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
//			String[] tablestring = new String[] { "UserId", "QuestionnaireId", "Name", "Phone", "Email", "Age",
//					"CreateDate", "問卷回答", "問題必填", "問卷編號", "使用者回答" };
//			String[] muststring = new String[] { "userId", "questionnaireId", "name", "phone", "email", "age",
//					"createDate", "questionChoices", "mustKeyin", "answer" }; // ??

//			csvWriter.writeHeader(tablestring);
//
//			// 13.跑回圈
//			for (String string  : resultlist) {
//				csvWriter.write(string, muststring);
//			}
//
//			// 14.連線關閉
//			csvWriter.close();
		}
	}

	@ResponseBody
	@GetMapping(value = { "/loadAnswerInfo/{id}" })
	public String loadAnswerInfo(Model model, @PathVariable("id") String userId) {

		return userId;
	}
}
