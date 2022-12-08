package com.QuesSystem.ques.service.impl;

import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.QuesSystem.ques.constant.AlertMessage;
import com.QuesSystem.ques.entity.Userinfo;

import com.QuesSystem.ques.repository.UserinfoDao;
import com.QuesSystem.ques.service.ifs.UserinfoService;

@Service
public class UserinfoServiceImpl implements UserinfoService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserinfoDao userinfoDao;

//	@Override
//	public void deleteUserinfo(String[] questionnaireId) {		
//		try {
//			for (String string : questionnaireId) {
//				List<Userinfo> userlist = userinfoDao.findListByQuestionnaireId(string);
//				for (Userinfo userinfo : userlist) {
//					userinfoDao.delete(userinfo);
//				}		
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		}			
//	}

	// 取得使用者資訊
	@SuppressWarnings("unchecked")
	@Override
	public List<Userinfo> getuserInfoList(HttpSession session) throws Exception {

		try {

			// 從Session(名為useranswers)中取得儲存的內容
			List<Userinfo> userInfoList = (List<Userinfo>) session.getAttribute("useranswers");
			// 若userInfoList是空值或為空，則回傳null
			if (userInfoList == null || userInfoList.isEmpty()) {
				return null;
			}

			// 若userInfoList有值，則進行排序(以創建日期進行正序，最後再進行逆序)
			userInfoList.sort(Comparator.comparing(Userinfo::getCreateDate).reversed());
			
			// 回傳userInfoList
			return userInfoList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	// 使用者填寫資訊的防呆
	@Override
	public String ErrorMsg(String name, String phone, String email, String age) {

		// <檢查姓名>
		// 姓名不能為空
		if (!StringUtils.hasText(name)) {
			
			return AlertMessage.UserInfoMsg.Name_Can_Not_Empty;
		// 姓名不能少於兩個字
		} else if (name.length() < 2) {
			
			return AlertMessage.UserInfoMsg.Name_Can_Not_Less_Two;
		}

		// <檢查年齡>
		// 年齡不能為空
		if (!StringUtils.hasText(age)) {
			
			return AlertMessage.UserInfoMsg.Age_Can_Not_Empty;
		}

		// <電話檢查>
		// 電話不能為空
		if (!StringUtils.hasText(phone)) {
			
			return AlertMessage.UserInfoMsg.Phone_Can_Not_Empty;
		// 電話不能少於10~11位數字
		} else if (phone.length() < 9 || phone.length() < 10) {
			
			return AlertMessage.UserInfoMsg.Phone_Can_Not_Less_Ten_Eleven;
		}

		// <信箱檢查>
		// 信箱不能為空
		if (!StringUtils.hasText(email)) {
			
			return AlertMessage.UserInfoMsg.Email_Can_Not_Empty;
		// 信箱必須包含@
		} else if (!email.contains("@")) {
			
			return AlertMessage.UserInfoMsg.Email_Must_Have_At;
		}

		return "";
	}
}
