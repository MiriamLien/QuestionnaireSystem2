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
@Table(name = "oftenuse")
@XmlRootElement
@NamedQuery(name = "oftenuse.findAll", query = "SELECT often FROM OftenUseQuestion often")
public class OftenUseQuestion {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "oftenuse_id", unique = true)
	private String oftenuseId;

	@Column(name = "oftenuse_title", length = 15)
	private String title;

	@Column(name = "oftenuse_choices")
	private String choices;

	@Column(name = "oftenuse_type")
	private int type;

	@Column(name = "must_keyin")
	private boolean mustKeyin;

	@Column(name = "create_date")
	private Date createDate = new Date();


	public String getOftenUseId() {
		return oftenuseId;
	}

	public void setOftenUseId(String oftenuseId) {
		this.oftenuseId = oftenuseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChoices() {
		return choices;
	}

	public void setChoices(String choices) {
		this.choices = choices;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isMustKeyin() {
		return mustKeyin;
	}

	public void setMustKeyin(boolean mustKeyin) {
		this.mustKeyin = mustKeyin;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
