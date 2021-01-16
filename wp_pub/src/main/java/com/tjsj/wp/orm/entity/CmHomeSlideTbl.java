package com.tjsj.wp.orm.entity;

import java.io.Serializable;
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

import com.avaje.ebean.annotation.Cache;
import com.avaje.ebean.annotation.Where;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the sm_home_slide_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_home_slide_tbl")
@NamedQuery(name="CmHomeSlideTbl.findAll", query="SELECT s FROM CmHomeSlideTbl s")
public class CmHomeSlideTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,CmHomeSlideTbl> find = new Find<Integer,CmHomeSlideTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	//与accessory表关联
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="accessory_id")
	private SmAccessoryTbl accessory;
	
	//用户表关联
	@ManyToOne
	@JoinColumn(name="creator")
	private SmUserTbl creator;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="insert_time")
	private Date insertTime;

	private String name;

	private Integer sequence;

	private String remark;
	
	@Column(name="remark_state")
	private Integer remarkState;
	
	private Integer type;

	@Where(clause = "state = -1")
	@Column(columnDefinition="default 0")
	private Integer state;

	private String url;
	
	@Column(name="pause_time" )
	private Integer pauseTime;//轮播图切换时间

	@Column(name="delete_status")
	private Integer deleteStatus;

	//与webset表关联
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;
	
	//get/set方法
	public CmHomeSlideTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Integer getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Integer deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public SmAccessoryTbl getAccessory() {
		return accessory;
	}

	public void setAccessory(SmAccessoryTbl accessory) {
		this.accessory = accessory;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SmUserTbl getCreator() {
		return creator;
	}

	public void setCreator(SmUserTbl creator) {
		this.creator = creator;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	public Integer getPauseTime() {
		return pauseTime;
	}

	public void setPauseTime(Integer pauseTime) {
		this.pauseTime = pauseTime;
	}
    
	public Integer getRemarkState() {
		return remarkState;
	}

	public void setRemarkState(Integer remarkState) {
		this.remarkState = remarkState;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	



}