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
		
		// 藉由帳號密碼取得帳戶
		Account accountInfo = accountDao.getAccountInfoByAccountAndPassword(account, password);
		
		// 如果帳戶不為空值
		if (accountInfo != null) {
			
			// 藉由帳戶ID取得帳戶
			Optional<Account> accountOp = accountDao.findById(accountInfo.getAccountId());
			// 回傳帳戶
			return accountOp.get();
		}
		
		// 如果帳戶是空值，回傳新的物件(Account)
		return new Account();
	}

	
}
