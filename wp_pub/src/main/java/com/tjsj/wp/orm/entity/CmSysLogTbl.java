package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * The persistent class for the cm_sys_log_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_sys_log_tbl")
@NamedQuery(name="CmSysLogTbl.findAll", query="SELECT c FROM CmSysLogTbl c")
public class CmSysLogTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,CmSysLogTbl> find = new Find<Integer,CmSysLogTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;

	@Column(name="insert_time")
	private Timestamp insertTime;

	private String remark;

	@Column(name="table_name")
	private String tableName;

	private int type;

	@ManyToOne
	@JoinColumn(name="user_id")
	private SmUserTbl oPerator;
	
	@ManyToOne
	@JoinColumn(name="webset_id")
	private SmWebSetTbl webset;

	public CmSysLogTbl() {
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public SmUserTbl getoPerator() {
		return oPerator;
	}

	public void setoPerator(SmUserTbl oPerator) {
		this.oPerator = oPerator;
	}

	public SmWebSetTbl getWebset() {
		return webset;
	}

	public void setWebset(SmWebSetTbl webset) {
		this.webset = webset;
	}

	public CmSysLogTbl(String remark, String tableName, int type, SmUserTbl oPerator, SmWebSetTbl webset) {
		super();
		this.remark = remark;
		this.tableName = tableName;
		this.type = type;
		this.oPerator = oPerator;
		this.webset = webset;
	}

}