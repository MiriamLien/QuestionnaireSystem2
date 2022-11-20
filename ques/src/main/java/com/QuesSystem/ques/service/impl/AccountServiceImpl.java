package com.QuesSystem.ques.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.QuesSystem.ques.entity.Account;
import com.QuesSystem.ques.repository.AccountDao;
import com.QuesSystem.ques.service.ifs.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;

	@Override
	public Account getAccountInfo(String account, String password) {
		Account accountInfo = accountDao.getAccountInfoByAccountAndPassword(account, password);
		if (accountInfo != null) {
			Optional<Account> accountOp = accountDao.findById(accountInfo.getAccountId());
			return accountOp.get();
		}
		return new Account();
	}

}
