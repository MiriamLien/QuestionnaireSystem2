package com.QuesSystem.ques.model;

import java.util.Set;

public class TotalAnswerVal2 {

	/*
	 * 問題標題
	 */
	private String questionTitle;
	
	/*
	 * 回答資訊
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
