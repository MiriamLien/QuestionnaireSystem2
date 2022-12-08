package com.QuesSystem.ques.constant;

public class AlertMessage {

	/*
	 * 問卷的提示訊息
	 */
	public static class QuestionnaireMsg {

		public static final String Enter_Nothing = "*未輸入任何內容*";

		public static final String Keyword_Above_Two_Words = "*關鍵字不能少於兩個字*";

		public static final String Search_Success = "*問卷查詢成功*";

		public static final String Not_Has_Questionnaire = "*沒有搜尋到這份問卷*";
		
		public static final String Click_Delete_Questionnaire = "*請勾選欲刪除的問卷*";

		public static final String Questionnaire_Has_Deleted = "*問卷刪除成功*";

		public static final String Check_Enter_Questionnaire = "*請確認是否有先編輯問卷*";

		public static final String Save_Questionnaire_Success = "*問卷新增成功*";

		public static final String No_Title = "*問卷標題為空*";

		public static final String Title_AtLeastFive = "*問卷標題至少要有五個字*";

		public static final String No_Body = "*問卷內容為空*";

		public static final String Body_AtLeastTwenty = "*問卷內容不可少於二十個字*";

		public static final String No_StartDate = "*開始日期為空*";

		public static final String No_EndDate = "*結束日期為空*";
		
		public static final String QuestionnaireId_Is_Null = "*問卷ID不得為空值*";
	}

	/*
	 * 問題及常用問題的提示訊息
	 */
	public static class QuestionMsg {
		
		public static final String Enter_Nothing = "*未輸入任何內容*";

		public static final String No_QuesTitle = "*未填寫問題標題*";

		public static final String Title_AtLeastThree = "*問題標題至少要有三個字*";

		public static final String Not_Enter_QuestionTitleAndAns = "*未輸入問題標題以及問題回答*";

		public static final String Save_OftenUse_Success = "*常用問題新增成功*";
		
		public static final String Save_EditedOftenUse_Success = "*常用問題編輯成功*";

		public static final String Not_Has_OftenUse_Question = "*尚未有這個常用問題*";
		
		public static final String Click_Delete_OftenUse_Question = "*請勾選欲刪除的常用問題*";

		public static final String OftenUse_Question_Has_Deleted = "*常用問題刪除成功*";

		public static final String Keyword_Above_Two_Words = "*關鍵字不可少於兩個字*";

		public static final String Must_QuesAndAns = "*必須輸入問題和答案*";

		public static final String Check_Must_QuesAndAnsMustEight = "*單選方塊必須輸入問題和答案, 答案至少要有八個字*";

		public static final String Radio_Must_QuesAndAnsMustSix = "*複選方塊必須輸入問題和答案, 答案至少要有六個字*";

		public static final String TextBox_MustQues_NoAns = "*文字方塊必須輸入問題, 但不需要輸入答案*";
		
		public static final String Judge_TextBox_MustQues_NoAns = "**";

		public static final String Have_One_Must_Question = "*至少要有一個必填項目*";
	}
	
	/*
	 * 使用者相關的提示訊息
	 */
	public static class UserInfoMsg {
		
		public static final String Name_Can_Not_Empty = "*未輸入姓名*";
		
		public static final String Name_Can_Not_Less_Two = "*姓名至少要有兩個字*";
		
		public static final String Age_Can_Not_Empty = "*未輸入年齡*";
		
		public static final String Age_Can_Not_Less_Ten = "*年齡不能小於十歲*";
		
		public static final String Phone_Can_Not_Empty = "*未輸入電話*";
		
		public static final String Phone_Can_Not_Less_Ten_Eleven = "*電話號碼至少要有十碼或十一碼*";
		
		public static final String Email_Can_Not_Empty = "*未輸入信箱*";

		public static final String Email_Must_Have_At = "*信箱必須包含@*";
	}
	
	/*
	 * (前台)確認頁面的提示訊息
	 */
	public static class ConfirmPageMsg {
		
		public static final String Check_Must_Keyin = "請檢查必填後再送出";
		
		public static final String Answer_Finish = "作答成功!! 感謝您的填寫";
	}
}
