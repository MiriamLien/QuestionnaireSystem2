package com.QuesSystem.ques.enums;

public enum QuestionnaireState {

	尚未投票(0), 投票中(1), 已結束(2);
	
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
