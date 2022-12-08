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

	// ���o�ϥΪ̸�T
	@SuppressWarnings("unchecked")
	@Override
	public List<Userinfo> getuserInfoList(HttpSession session) throws Exception {

		try {

			// �qSession(�W��useranswers)�����o�x�s�����e
			List<Userinfo> userInfoList = (List<Userinfo>) session.getAttribute("useranswers");
			// �YuserInfoList�O�ŭȩά��šA�h�^��null
			if (userInfoList == null || userInfoList.isEmpty()) {
				return null;
			}

			// �YuserInfoList���ȡA�h�i��Ƨ�(�H�Ыؤ���i�楿�ǡA�̫�A�i��f��)
			userInfoList.sort(Comparator.comparing(Userinfo::getCreateDate).reversed());
			
			// �^��userInfoList
			return userInfoList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	// �ϥΪ̶�g��T�����b
	@Override
	public String ErrorMsg(String name, String phone, String email, String age) {

		// <�ˬd�m�W>
		// �m�W���ର��
		if (!StringUtils.hasText(name)) {
			
			return AlertMessage.UserInfoMsg.Name_Can_Not_Empty;
		// �m�W����֩��Ӧr
		} else if (name.length() < 2) {
			
			return AlertMessage.UserInfoMsg.Name_Can_Not_Less_Two;
		}

		// <�ˬd�~��>
		// �~�֤��ର��
		if (!StringUtils.hasText(age)) {
			
			return AlertMessage.UserInfoMsg.Age_Can_Not_Empty;
		}

		// <�q���ˬd>
		// �q�ܤ��ର��
		if (!StringUtils.hasText(phone)) {
			
			return AlertMessage.UserInfoMsg.Phone_Can_Not_Empty;
		// �q�ܤ���֩�10~11��Ʀr
		} else if (phone.length() < 9 || phone.length() < 10) {
			
			return AlertMessage.UserInfoMsg.Phone_Can_Not_Less_Ten_Eleven;
		}

		// <�H�c�ˬd>
		// �H�c���ର��
		if (!StringUtils.hasText(email)) {
			
			return AlertMessage.UserInfoMsg.Email_Can_Not_Empty;
		// �H�c�����]�t@
		} else if (!email.contains("@")) {
			
			return AlertMessage.UserInfoMsg.Email_Must_Have_At;
		}

		return "";
	}
}
