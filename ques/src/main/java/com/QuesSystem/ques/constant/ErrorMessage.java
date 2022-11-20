package com.QuesSystem.ques.constant;

public class ErrorMessage {

	public static class QuestionnaireMsg {

		/* 問卷標題為空 */
		public static final String No_Title = "*Questionnaire title can't be null*";

		/* 問卷標題至少五個字 */
		public static final String Title_AtLastFive = "*Questionnaire title must at last 5 words*";

		/* 問卷內容為空 */
		public static final String No_Body = "*Questionnaire body can't be null*";

		/* 問卷內容小於20個字 */
		public static final String Body_AtLastTwenty = "*Questionnaire body must at last 20 words*";

		/* 起始日期為空 */
		public static final String No_StartTime = "*Questionnaire startTime can't be null*";

		/* 結束日期為空 */
		public static final String No_EndTime = "*Questionnaire endTime can't be null*";

	}

	public static class QuestionMsg {

		/* 問題標題為空 */
		public static final String No_QuesTitle = "*Question title can't be null*";

		/* 問題標題至少三個字 */
		public static final String Title_AtLastThree = "*Question title must at last 3 words*";

		/* 單選方塊需要輸入問題答案 / 複選方塊需要輸入問題答案 */
		public static final String Must_QuesAndAns = "*Must have question and answer*";

		/* 單選方塊需要輸入問題,答案至少要有6個字 */
		public static final String Radio_Must_QuesAndAnsMustSix = "*RadioButton must have question and answer must have 6 words.*";

		/* 複選方塊需要輸入問題,答案至少要有8個字 */
		public static final String Check_Must_QuesAndAnsMustEight = "*CheckBox must have question and answer must have 8 words.*";

		/* 文字方塊不需要輸入問題答案 */
		public static final String TextBox_MustQues_NotAns = "*TextBox Must have question, but not need answer*";
	}
	
	
	
}
