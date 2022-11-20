package com.QuesSystem.ques.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
