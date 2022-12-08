package com.QuesSystem.ques.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.QuesSystem.ques.entity.Questionnaire;
import com.QuesSystem.ques.entity.Userinfo;
import com.QuesSystem.ques.model.QuestionnaireInfo;
import com.QuesSystem.ques.repository.QuestionDao;
import com.QuesSystem.ques.repository.QuestionnaireDao;
import com.QuesSystem.ques.repository.UserinfoDao;
import com.QuesSystem.ques.service.ifs.AnswerService;
import com.QuesSystem.ques.service.ifs.QuestionService;
import com.QuesSystem.ques.service.ifs.UserinfoService;
import com.google.gson.Gson;

@Controller
public class UserAndAnswerInfoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserinfoDao userInfoDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private QuestionnaireDao questionnaireDao;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private UserinfoService userinfoService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	// ��x���� - ��ƶ�g����(�ץX���s)
	@GetMapping("/answerListToCSV")
	public void exportToCSV(HttpSession session,
							HttpServletResponse response) throws IOException {

		// �]�w���e�榡
		response.setContentType("text/csv");
		// �]�w�s�X(�Ϋ��w���s�X���h�л\response��H���q�{���s�X��)
		response.setCharacterEncoding("UTF-8");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=questionnaire_" + currentDateTime + ".csv";
		response.setHeader(headerKey, headerValue);

		Questionnaire changeQues = (Questionnaire) session.getAttribute("changeques");
		List<Userinfo> userinfoList = userInfoDao.findByQuestionnaireId(changeQues);

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] titles = new String[] { "UserId", "Age", "CreateDate", "Email", "Name", "Phone", "QuestionnaireId",
				"Answer" };
		String[] propertys = new String[] { "userId", "age", "createDate", "email", "name", "phone", "questionnaireId",
				"answer" };

		csvWriter.writeHeader(titles);

		for (Userinfo userinfo : userinfoList) {
			csvWriter.write(userinfo, propertys);
		}
		
		csvWriter.close();
		response.setContentType("text/csv");
	}

	// �N�ݨ���TŪ���X��
	@ResponseBody
	@GetMapping(value = { "/loadAnswerInfo/{userId}" })
	public String loadAnswerInfo(Model model, @PathVariable("userId") String userId) {

		// �إ�Gson����
		Gson gson = new Gson();
		// �ǥѨϥΪ�ID(userId)�إ߰ݨ���T(�ϥΪ̤Φ^����T)
		QuestionnaireInfo questionnaireInfo = answerService.createQuestionnaireInfo(userId);

		// �NquestionnaireInfo�নJson�A�öi��^��
		return gson.toJson(questionnaireInfo);
	}
}
