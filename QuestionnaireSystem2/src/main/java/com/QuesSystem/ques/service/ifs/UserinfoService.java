package com.QuesSystem.ques.service.ifs;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.QuesSystem.ques.entity.Userinfo;

public interface UserinfoService {

//	public void deleteUserinfo(String[] questionnaire);
	
	/*
	 * ���o�ϥΪ̸�ƪ��M��
	 */
	public List<Userinfo> getuserInfoList (HttpSession session) throws Exception;
	
	/*
	 * �ϥΪ̸�ƨ��b
	 */
	public String ErrorMsg(String name, String phone, String email, String age); 
}
