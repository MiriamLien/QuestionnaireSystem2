package com.QuesSystem.ques;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.QuesSystem.ques.entity.Account;
import com.QuesSystem.ques.repository.AccountDao;

// 為找到@SpringBootApplication主配置類別來啟動Spring Boot應用程式環境
@SpringBootTest(classes = QuesApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountTest {

	@Autowired
	private AccountDao accountDao;
	
	@Test
	public void saveTest() {
		Account account = new Account();
		account.setAccount("admin");
		account.setPassword("12345678");
		
		accountDao.save(account);
	}
}
