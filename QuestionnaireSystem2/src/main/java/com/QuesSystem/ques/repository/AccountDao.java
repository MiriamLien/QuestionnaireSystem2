package com.QuesSystem.ques.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.QuesSystem.ques.entity.Account;

@Repository
@Transactional
public interface AccountDao extends JpaRepository<Account, String> {

	public Account getAccountInfoByAccountAndPassword(String account, String password);
}
