package com.QuesSystem.ques.enums;

public enum QuestionnaireState {

	�|���벼(0), �벼��(1), �w����(2);
	
	private int stateNum;
	
	private QuestionnaireState(int stateNum) {
		this.stateNum = stateNum;
	}

	
	public int getStateNum() {
		return stateNum;
	}

	public void setStateNum(int stateNum) {
		this.stateNum = stateNum;
	}
}
