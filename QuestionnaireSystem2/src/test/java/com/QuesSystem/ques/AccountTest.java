package com.QuesSystem.ques;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.QuesSystem.ques.entity.Account;
import com.QuesSystem.ques.repository.AccountDao;

@SpringBootTest
public class AccountTest {

	@Autowired
	private AccountDao accountDao;
	
	@Test
	public void addAccount() {
		Account account = new Account();
		account.setAccount("admin");
		account.setPassword("12345678");
		account.setCreateDate(new Date());
		
		accountDao.save(account);
	}
	
}
