package com.QuesSystem.ques.model;

import java.util.Set;

public class TotalAnswerVal2 {

	/*
	 * ���D���D
	 */
	private String questionTitle;
	
	/*
	 * �^����T
	 */
	private Set<ChoicesInfo> choicesInfo;

	
	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public Set<ChoicesInfo> getChoicesInfo() {
		return choicesInfo;
	}

	public void setChoicesInfo(Set<ChoicesInfo> choicesInfo) {
		this.choicesInfo = choicesInfo;
	}
}
