package com.QuesSystem.ques.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@Entity
// 跟資料庫做互動(name的名稱跟資料庫內的資料表名稱一致)
@Table(name = "account")
//
@XmlRootElement
//
@NamedQuery(name = "account.findAll", query = "SELECT acc FROM Account acc")
public class Account {

	/*
	 * 帳戶ID
	 */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "account_id", unique = true)
	private String accountId;
	
	/*
	 * 帳號
	 */
	@Column(name = "account")
	private String account;
	
	/*
	 * 密碼
	 */
	@Column(name = "password")
	private String password;
	
	/*
	 * 創建日期
	 */
	@Column(name="create_date")
	private Date createDate = new Date();

	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
