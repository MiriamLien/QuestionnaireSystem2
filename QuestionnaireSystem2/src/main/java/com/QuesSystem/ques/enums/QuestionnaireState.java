package com.QuesSystem.ques.enums;

import lombok.Getter;

@Getter
public enum QuestionnaireState {

	�|���벼(0), �벼��(1), �w����(2);
	
	private int quesNum;
	
	private QuestionnaireState(int quesNum) {
		this.quesNum = quesNum;
	}

	public int getQuesNum() {
		return quesNum;
	}

	public void setQuesNum(int quesNum) {
		this.quesNum = quesNum;
	}
}
