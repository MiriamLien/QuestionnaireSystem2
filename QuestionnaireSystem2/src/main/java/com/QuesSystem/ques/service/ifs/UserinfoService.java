package com.QuesSystem.ques.service.ifs;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.QuesSystem.ques.entity.Userinfo;

public interface UserinfoService {

//	public void deleteUserinfo(String[] questionnaire);
	
	/*
	 * 取得使用者資料的清單
	 */
	public List<Userinfo> getuserInfoList (HttpSession session) throws Exception;
	
	/*
	 * 使用者資料防呆
	 */
	public String ErrorMsg(String name, String phone, String email, String age); 
}
