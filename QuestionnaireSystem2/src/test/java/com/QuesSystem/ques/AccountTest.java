package com.QuesSystem.ques;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.QuesSystem.ques.entity.Account;
import com.QuesSystem.ques.repository.AccountDao;

// �����@SpringBootApplication�D�t�m���O�ӱҰ�Spring Boot���ε{������
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
