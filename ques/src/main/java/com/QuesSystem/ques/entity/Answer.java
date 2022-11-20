package com.QuesSystem.ques.entity;

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

import lombok.Data;

@Data
@Entity
@Table(name = "answer")
@XmlRootElement
@NamedQuery(name = "answer.findall", query = "SELECT ans FROM Answer ans")
public class Answer {

	/*
	 * 回答ID
	 */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "answer_id", unique = true)
	private String answerId;

	/*
	 * 問卷ID
	 */
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "questionnaire_id")
	private Questionnaire questionnaireId;

	/*
	 * 使用者ID
	 */
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private Userinfo userId;

	/*
	 * 回答
	 */
	@Column(name = "answer")
	private String answer;

	/*
	 * 回答編號
	 */
	@Column(name = "answer_number")
	private int answerNumber;

	
	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public Questionnaire getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Questionnaire questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public Userinfo getUserId() {
		return userId;
	}

	public void setUserId(Userinfo userId) {
		this.userId = userId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getAnswerNumber() {
		return answerNumber;
	}

	public void setAnswerNumber(int answerNumber) {
		this.answerNumber = answerNumber;
	}
}
