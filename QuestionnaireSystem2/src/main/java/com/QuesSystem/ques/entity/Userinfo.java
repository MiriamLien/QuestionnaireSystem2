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
	 * �ϥΪ�ID
	 */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "user_id", unique = true)
	private String userId;

	/*
	 * �ݨ�ID
	 */
	// �ϥΪ̸�T(Userinfo)�O�h�A�ݨ�ID�O�@
	// ��V�h��@(��ܦb���[�ƪ����p(Cascade)�ާ@) >> �O�s�ݨ�ID���P�ɫO�s�ϥΪ̸�T
	@ManyToOne(cascade = CascadeType.PERSIST)
	// ���w�ͦ��~�䪺�W�r
	@JoinColumn(name = "questionnaire_id")
	private Questionnaire questionnaireId;

	/*
	 * �m�W
	 */
	@Column(name = "name", length = 15)
	private String name;

	/*
	 * �q�ܸ��X
	 */
	@Column(name = "phone", length = 10)
	private String phone;

	/*
	 * �H�c
	 */
	@Column(name = "email")
	private String email;

	/*
	 * �~��
	 */
	@Column(name = "age")
	private String age;

	/*
	 * �Ыؤ��(�ϥΪ̶�g�ݨ������)
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
