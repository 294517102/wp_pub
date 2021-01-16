package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;

@Entity
@Cache(enableQueryCache=true)
@Table(name="bn_jlinfo_tbl")
@NamedQuery(name="BnJlinfoTbl.findAll", query="SELECT c FROM BnJlinfoTbl c")
public class BnJlinfoTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,BnJlinfoTbl> find = new Find<Integer,BnJlinfoTbl>(){};
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String number;//
	
	private String name;//
	@Column(name="str_sex")
	private String strSex;
	
	private int sex;
	
	private String record;//学历
	private String major;//专业
	
	@Column(name="id_card")
	private String idCard;
	
	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="insert_time",insertable=false,updatable=false)
	private Date insertTime;//插入时间	
	
	@Column(name="train_time")
	private Date trainTime;
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="ent_time")
	private Date entTime;
	
	@Column(name="current_job")
	private String currentJob; //现单位
	
	@Column(name="former_unit")
	private String formerUnit;
	
	
	
	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;//所属站点
	
	@Column(name="is_delete")
	private int isDelete;//文章是否删除，1，表示未删除，-1已删除

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getTrainTime() {
		return trainTime;
	}

	public void setTrainTime(Date trainTime) {
		this.trainTime = trainTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getEntTime() {
		return entTime;
	}

	public void setEntTime(Date entTime) {
		this.entTime = entTime;
	}

	public String getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
	}

	public String getFormerUnit() {
		return formerUnit;
	}

	public void setFormerUnit(String formerUnit) {
		this.formerUnit = formerUnit;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getStrSex() {
		return strSex;
	}

	public void setStrSex(String strSex) {
		this.strSex = strSex;
	}

	@Override
	public String toString() {
		return "BnJlinfoTbl [id=" + id + ", number=" + number + ", name=" + name + ", strSex=" + strSex + ", sex=" + sex
				+ ", record=" + record + ", major=" + major + ", idCard=" + idCard + ", insertTime=" + insertTime
				+ ", trainTime=" + trainTime + ", updateTime=" + updateTime + ", entTime=" + entTime + ", currentJob="
				+ currentJob + ", formerUnit=" + formerUnit + ", webSet=" + webSet + ", isDelete=" + isDelete + "]";
	}
	
}
