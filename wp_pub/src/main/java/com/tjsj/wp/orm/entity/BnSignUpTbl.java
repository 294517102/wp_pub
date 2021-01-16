package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the bn_wechat_config_tbl database table.
 * 
 */  
@Entity
@Cache(enableQueryCache=true)
@Table(name="bn_sign_up_tbl")
@NamedQuery(name="BnSignUpTbl.findAll", query="SELECT b FROM BnSignUpTbl b")
public class BnSignUpTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,BnSignUpTbl> find = new Find<Integer,BnSignUpTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;
	
	private String name;
	
	private Integer sex;
	
	@Column(name="id_card")
	private String idCard;
	
	private String grade;
	
	@Column(name="now_school")
	private String nowSchool;
	
	private String job;
	
	private String teacher;
	
	@Column(name="father_name")
	private String fatherName;
	
	@Column(name="f_work")
	private String fWork;
	
	@Column(name="f_phone")
	private String fPhone;

	@Column(name="mother_name")
	private String motherName;
	
	@Column(name="m_work")
	private String mWork;

	@Column(name="m_phone")
	private String mPhone;
	
	private String prize;
	
	private String specialty;
	
	private String achievement;
	
	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;
	
	@Column(name="insert_time")
	private Date insertTime;
	
	@Column(name="is_delete")
	private int isDelete;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getNowSchool() {
		return nowSchool;
	}

	public void setNowSchool(String nowSchool) {
		this.nowSchool = nowSchool;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getfWork() {
		return fWork;
	}

	public void setfWork(String fWork) {
		this.fWork = fWork;
	}

	public String getfPhone() {
		return fPhone;
	}

	public void setfPhone(String fPhone) {
		this.fPhone = fPhone;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getmWork() {
		return mWork;
	}

	public void setmWork(String mWork) {
		this.mWork = mWork;
	}


	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getmPhone() {
		return mPhone;
	}

	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	
	
}