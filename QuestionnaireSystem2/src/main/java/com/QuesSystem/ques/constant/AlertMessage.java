package com.QuesSystem.ques.constant;

public class AlertMessage {

	/*
	 * �ݨ������ܰT��
	 */
	public static class QuestionnaireMsg {

		public static final String Enter_Nothing = "*����J���󤺮e*";

		public static final String Keyword_Above_Two_Words = "*����r����֩��Ӧr*";

		public static final String Search_Success = "*�ݨ��d�ߦ��\*";

		public static final String Not_Has_Questionnaire = "*�S���j�M��o���ݨ�*";
		
		public static final String Click_Delete_Questionnaire = "*�ФĿ���R�����ݨ�*";

		public static final String Questionnaire_Has_Deleted = "*�ݨ��R�����\*";

		public static final String Check_Enter_Questionnaire = "*�нT�{�O�_�����s��ݨ�*";

		public static final String Save_Questionnaire_Success = "*�ݨ��s�W���\*";

		public static final String No_Title = "*�ݨ����D����*";

		public static final String Title_AtLeastFive = "*�ݨ����D�ܤ֭n�����Ӧr*";

		public static final String No_Body = "*�ݨ����e����*";

		public static final String Body_AtLeastTwenty = "*�ݨ����e���i�֩�G�Q�Ӧr*";

		public static final String No_StartDate = "*�}�l�������*";

		public static final String No_EndDate = "*�����������*";
		
		public static final String QuestionnaireId_Is_Null = "*�ݨ�ID���o���ŭ�*";
	}

	/*
	 * ���D�α`�ΰ��D�����ܰT��
	 */
	public static class QuestionMsg {
		
		public static final String Enter_Nothing = "*����J���󤺮e*";

		public static final String No_QuesTitle = "*����g���D���D*";

		public static final String Title_AtLeastThree = "*���D���D�ܤ֭n���T�Ӧr*";

		public static final String Not_Enter_QuestionTitleAndAns = "*����J���D���D�H�ΰ��D�^��*";

		public static final String Save_OftenUse_Success = "*�`�ΰ��D�s�W���\*";
		
		public static final String Save_EditedOftenUse_Success = "*�`�ΰ��D�s�覨�\*";

		public static final String Not_Has_OftenUse_Question = "*�|�����o�ӱ`�ΰ��D*";
		
		public static final String Click_Delete_OftenUse_Question = "*�ФĿ���R�����`�ΰ��D*";

		public static final String OftenUse_Question_Has_Deleted = "*�`�ΰ��D�R�����\*";

		public static final String Keyword_Above_Two_Words = "*����r���i�֩��Ӧr*";

		public static final String Must_QuesAndAns = "*������J���D�M����*";

		public static final String Check_Must_QuesAndAnsMustEight = "*�����������J���D�M����, ���צܤ֭n���K�Ӧr*";

		public static final String Radio_Must_QuesAndAnsMustSix = "*�ƿ���������J���D�M����, ���צܤ֭n�����Ӧr*";

		public static final String TextBox_MustQues_NoAns = "*��r���������J���D, �����ݭn��J����*";
		
		public static final String Judge_TextBox_MustQues_NoAns = "**";

		public static final String Have_One_Must_Question = "*�ܤ֭n���@�ӥ��񶵥�*";
	}
	
	/*
	 * �ϥΪ̬��������ܰT��
	 */
	public static class UserInfoMsg {
		
		public static final String Name_Can_Not_Empty = "*����J�m�W*";
		
		public static final String Name_Can_Not_Less_Two = "*�m�W�ܤ֭n����Ӧr*";
		
		public static final String Age_Can_Not_Empty = "*����J�~��*";
		
		public static final String Age_Can_Not_Less_Ten = "*�~�֤���p��Q��*";
		
		public static final String Phone_Can_Not_Empty = "*����J�q��*";
		
		public static final String Phone_Can_Not_Less_Ten_Eleven = "*�q�ܸ��X�ܤ֭n���Q�X�ΤQ�@�X*";
		
		public static final String Email_Can_Not_Empty = "*����J�H�c*";

		public static final String Email_Must_Have_At = "*�H�c�����]�t@*";
	}
	
	/*
	 * (�e�x)�T�{���������ܰT��
	 */
	public static class ConfirmPageMsg {
		
		public static final String Check_Must_Keyin = "���ˬd�����A�e�X";
		
		public static final String Answer_Finish = "�@�����\!! �P�±z����g";
	}
}
