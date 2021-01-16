package com.tjsj.wp.orm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the cm_comment_ext_tbl database table.
 * 
 */
@Entity
@Table(name="cm_comment_ext_tbl")
@NamedQuery(name="CmCommentExtTbl.findAll", query="SELECT c FROM CmCommentExtTbl c")
public class CmCommentExtTbl extends com.tjsj.m_util.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,CmCommentExtTbl> find = new Find<Integer,CmCommentExtTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String contents; //学生特长

	private String describes;//获奖情况

	private String idcard;

	@Column(name="mother_name")
	private String motherName;//母亲姓名

	@Column(name="parent_name")
	private String parentName;//父亲姓名

	private String phones;//父亲电话

	private String result;//学生成绩

	private String tel;//母亲电话

	@Column(name="work_na")
	private String workNa;//母亲工作

	@Column(name="work_name")
	private String workName;//父亲工作

	public CmCommentExtTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getDescribes() {
		return this.describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMotherName() {
		return this.motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}


	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWorkNa() {
		return this.workNa;
	}

	public void setWorkNa(String workNa) {
		this.workNa = workNa;
	}

	public String getWorkName() {
		return this.workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

}