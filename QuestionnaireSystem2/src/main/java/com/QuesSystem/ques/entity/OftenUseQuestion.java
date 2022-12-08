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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "oftenuse")
@XmlRootElement
@NamedQuery(name = "oftenuse.findAll", query = "SELECT often FROM OftenUseQuestion often")
public class OftenUseQuestion {

	/*
	 * 常用問題ID
	 */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "oftenuse_id", unique = true)
	private String oftenuseId;
	
	/*
	 * 常用問題標題
	 */
	@Column(name = "oftenuse_title", length = 15)
	private String oftenuseTitle;

	/*
	 * 常用問題回答
	 */
	@Column(name = "oftenuse_choices")
	private String oftenuseChoices;
	
	/*
	 * 常用問題類型
	 */
	@Column(name = "oftenuse_type")
	private int oftenuseType;

	/*
	 * 是否為必填項目
	 */
	@Column(name = "must_keyin")
	private boolean mustKeyin;
	
	/*
	 * 創建日期
	 */
	@Column(name="create_date")
	private Date createDate = new Date();

	
	public String getOftenuseId() {
		return oftenuseId;
	}

	public void setOftenuseId(String oftenuseId) {
		this.oftenuseId = oftenuseId;
	}

	public String getOftenuseTitle() {
		return oftenuseTitle;
	}

	public void setOftenuseTitle(String oftenuseTitle) {
		this.oftenuseTitle = oftenuseTitle;
	}

	public String getOftenuseChoices() {
		return oftenuseChoices;
	}

	public void setOftenuseChoices(String oftenuseChoices) {
		this.oftenuseChoices = oftenuseChoices;
	}

	public int getOftenuseType() {
		return oftenuseType;
	}

	public void setOftenuseType(int oftenuseType) {
		this.oftenuseType = oftenuseType;
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