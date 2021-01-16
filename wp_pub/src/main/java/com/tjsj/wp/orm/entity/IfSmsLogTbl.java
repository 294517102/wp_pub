package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the if_sms_log_tbl database table.
 * 
 */
@Entity
@Table(name="if_sms_log_tbl")
@NamedQuery(name="IfSmsLogTbl.findAll", query="SELECT i FROM IfSmsLogTbl i")
public class IfSmsLogTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,IfSmsLogTbl> find = new Find<Integer,IfSmsLogTbl>(){};
	@Id
	private int id;

	private String content;

	@Column(name="insert_time")
	private Timestamp insertTime;

	private String mobile;

	@Column(name="result_desc")
	private String resultDesc;

	private String return_;

	@Column(name="send_time")
	private Date sendTime;

	@Column(name="task_id")
	private int taskId;

	public IfSmsLogTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getResultDesc() {
		return this.resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public String getReturn_() {
		return this.return_;
	}

	public void setReturn_(String return_) {
		this.return_ = return_;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public int getTaskId() {
		return this.taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

}