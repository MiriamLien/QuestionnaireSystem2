package com.QuesSystem.ques.model;

import java.util.Date;
import java.util.List;

public class AnswerDetailToCSV {

	private static List<String> headerList =
							List.of("�m�W",
									"�q��",
									"�H�c",
									"�~��",
									"��g���",
									"���D�W��",
									"���D����",
									"���D�ﶵ",
									"�^���s��",
									"�^��");

	private String name;

	private String phone;

	private String email;

	private String age;

	private Date createDate;

	private String questionTitle;

	private String mustKeyin;

	private String questionChoices;

	private int answerNumber;

	private String answer;
	

	public static List<String> getHeaderList() {
		return headerList;
	}

	public static void setHeaderList(List<String> headerList) {
		AnswerDetailToCSV.headerList = headerList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getMustKeyin() {
		return mustKeyin;
	}

	public void setMustKeyin(String mustKeyin) {
		this.mustKeyin = mustKeyin;
	}

	public String getQuestionChoices() {
		return questionChoices;
	}

	public void setQuestionChoices(String questionChoices) {
		this.questionChoices = questionChoices;
	}

	public int getAnswerNumber() {
		return answerNumber;
	}

	public void setAnswerNumber(int answerNumber) {
		this.answerNumber = answerNumber;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
