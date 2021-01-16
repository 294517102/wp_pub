package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the cm_recruitment_tbl database table.
 * 职位招聘表
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_recruitment_tbl")
@NamedQuery(name="CmRecruitmentTbl.findAll", query="SELECT c FROM CmRecruitmentTbl c")
public class CmRecruitmentTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,CmRecruitmentTbl> find = new Find<Integer,CmRecruitmentTbl>(){};
	@Id
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="accessory_id")
	private SmAccessoryTbl accessory;

	@Column(name="insert_time",insertable=false)
	private Timestamp insertTime;

	@Column(name="is_delete")
	private int isDelete;

	private String name;

	private String phone;

	private String position;

	private Integer sex;
	
	@Column(insertable=false)
	private int state;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;
	
	@Column(name="solve_time")
	private Date SolveTime;

	private String nation;
	
	private String birthday;
	
	
	@Column(name="political_status")
	private int politicalStatus;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name="learn_expr")
	private String learnExpr;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name="work_expr")
	private String workExpr;
	
	public CmRecruitmentTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public int getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public SmAccessoryTbl getAccessory() {
		return accessory;
	}

	public void setAccessory(SmAccessoryTbl accessory) {
		this.accessory = accessory;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSex() {
		return sex;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getSolveTime() {
		return SolveTime;
	}

	public void setSolveTime(Date solveTime) {
		SolveTime = solveTime;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(int politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	public String getLearnExpr() {
		return learnExpr;
	}

	public void setLearnExpr(String learnExpr) {
		this.learnExpr = learnExpr;
	}

	public String getWorkExpr() {
		return workExpr;
	}

	public void setWorkExpr(String workExpr) {
		this.workExpr = workExpr;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}
	
	

}