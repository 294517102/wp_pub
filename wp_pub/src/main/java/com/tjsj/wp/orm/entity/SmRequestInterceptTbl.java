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
 * The persistent class for the sm_request_intercept_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="sm_request_intercept_tbl")
@NamedQuery(name="SmRequestInterceptTbl.findAll", query="SELECT s FROM SmRequestInterceptTbl s")
public class SmRequestInterceptTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,SmRequestInterceptTbl> find = new Find<Integer,SmRequestInterceptTbl>(){};
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="i_type")
	private int type;

	@Column(name="insert_time",insertable=false,updatable=false)
	private Timestamp insertTime;

	@Column(name="is_delete",insertable=false)
	private int isDelete;
	
	private Integer state;

	private String url;

	@JoinColumn(name="webset_id")
	//private int websetId;
	@ManyToOne
	private SmWebSetTbl webSet;

	public SmRequestInterceptTbl() {
		isDelete = -1;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	@Override
	public String toString() {
		return "SmRequestInterceptTbl [id=" + id + ", type=" + type + ", insertTime=" + insertTime + ", isDelete="
				+ isDelete + ", state=" + state + ", url=" + url + "]";
	}

	

}