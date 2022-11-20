package com.QuesSystem.ques.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "userinfo")
@XmlRootElement
@NamedQuery(name = "userinfo.findAll", query = "SELECT user FROM Userinfo user")
public class Userinfo {

	/*
	 * 使用者ID
	 */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "user_id", unique = true)
	private String userId;

	/*
	 * 問卷ID
	 */
	// 使用者資訊(Userinfo)是多，問卷ID是一
	// 單向多對一(表示在持久化的級聯(Cascade)操作) >> 保存問卷ID的同時保存使用者資訊
	@ManyToOne(cascade = CascadeType.PERSIST)
	// 指定生成外鍵的名字
	@JoinColumn(name = "questionnaire_id")
	private Questionnaire questionnaireId;

	/*
	 * 姓名
	 */
	@Column(name = "name", length = 15)
	private String name;

	/*
	 * 電話號碼
	 */
	@Column(name = "phone", length = 10)
	private String phone;

	/*
	 * 信箱
	 */
	@Column(name = "email")
	private String email;

	/*
	 * 年齡
	 */
	@Column(name = "age")
	private String age;

	/*
	 * 創建日期(使用者填寫問卷的日期)
	 */
	@Column(name = "create_date")
	private Date createDate = new Date();

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Questionnaire getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Questionnaire questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
