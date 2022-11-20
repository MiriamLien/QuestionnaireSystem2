package com.QuesSystem.ques.constant;

public class ErrorMessage {

	public static class QuestionnaireMsg {

		/* �ݨ����D���� */
		public static final String No_Title = "*Questionnaire title can't be null*";

		/* �ݨ����D�ܤ֤��Ӧr */
		public static final String Title_AtLastFive = "*Questionnaire title must at last 5 words*";

		/* �ݨ����e���� */
		public static final String No_Body = "*Questionnaire body can't be null*";

		/* �ݨ����e�p��20�Ӧr */
		public static final String Body_AtLastTwenty = "*Questionnaire body must at last 20 words*";

		/* �_�l������� */
		public static final String No_StartTime = "*Questionnaire startTime can't be null*";

		/* ����������� */
		public static final String No_EndTime = "*Questionnaire endTime can't be null*";

	}

	public static class QuestionMsg {

		/* ���D���D���� */
		public static final String No_QuesTitle = "*Question title can't be null*";

		/* ���D���D�ܤ֤T�Ӧr */
		public static final String Title_AtLastThree = "*Question title must at last 3 words*";

		/* ������ݭn��J���D���� / �ƿ����ݭn��J���D���� */
		public static final String Must_QuesAndAns = "*Must have question and answer*";

		/* ������ݭn��J���D,���צܤ֭n��6�Ӧr */
		public static final String Radio_Must_QuesAndAnsMustSix = "*RadioButton must have question and answer must have 6 words.*";

		/* �ƿ����ݭn��J���D,���צܤ֭n��8�Ӧr */
		public static final String Check_Must_QuesAndAnsMustEight = "*CheckBox must have question and answer must have 8 words.*";

		/* ��r������ݭn��J���D���� */
		public static final String TextBox_MustQues_NotAns = "*TextBox Must have question, but not need answer*";
	}
	
	
	
}
