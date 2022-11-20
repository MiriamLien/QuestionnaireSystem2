package com.QuesSystem.ques.enums;

public enum QuestionType {

	單選方塊(0), 複選方塊(1), 文字方塊(2);

	
	private int Code;

	QuestionType(int Code) {
		this.Code = Code;		
	}

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}
}
