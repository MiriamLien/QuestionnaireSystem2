package com.QuesSystem.ques.enums;

public enum QuestionType {

	�����(0), �ƿ���(1), ��r���(2);

	
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
