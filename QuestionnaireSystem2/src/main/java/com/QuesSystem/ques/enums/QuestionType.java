package com.QuesSystem.ques.enums;

import lombok.Getter;

@Getter
public enum QuestionType {

	�����(0), �ƿ���(1), ��r���(2);

	private int Code;

	QuestionType(int Code) {
		this.Code = Code;		
	}

	public int getCode() {
		return Code;
	}	
}
