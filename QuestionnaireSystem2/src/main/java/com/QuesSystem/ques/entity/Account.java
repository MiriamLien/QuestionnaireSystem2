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
// ���Ʈw������(name���W�ٸ��Ʈw������ƪ�W�٤@�P)
@Table(name = "account")
//
@XmlRootElement
//
@NamedQuery(name = "account.findAll", query = "SELECT acc FROM Account acc")
public class Account {

	/*
	 * �b��ID
	 */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "account_id", unique = true)
	private String accountId;
	
	/*
	 * �b��
	 */
	@Column(name = "account")
	private String account;
	
	/*
	 * �K�X
	 */
	@Column(name = "password")
	private String password;
	
	/*
	 * �Ыؤ��
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
