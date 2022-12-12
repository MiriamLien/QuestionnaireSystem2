package com.QuesSystem.ques.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QuesSystem.ques.entity.Account;
import com.QuesSystem.ques.repository.AccountDao;
import com.QuesSystem.ques.service.ifs.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;
	
	@Override
	public Account getAccount(String account, String password) {
		
		// �ǥѱb���K�X���o�b��
		Account accountInfo = accountDao.getAccountInfoByAccountAndPassword(account, password);
		
		// �p�G�b�ᤣ���ŭ�
		if (accountInfo != null) {
			
			// �ǥѱb��ID���o�b��
			Optional<Account> accountOp = accountDao.findById(accountInfo.getAccountId());
			// �^�Ǳb��
			return accountOp.get();
		}
		
		// �p�G�b��O�ŭȡA�^�Ƿs������(Account)
		return new Account();
	}

	
}
