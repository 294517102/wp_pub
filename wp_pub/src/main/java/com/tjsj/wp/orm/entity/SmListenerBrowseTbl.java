package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the sm_listener_browse_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="sm_listener_browse_tbl")
@NamedQuery(name="SmListenerBrowseTbl.findAll", query="SELECT s FROM SmListenerBrowseTbl s")
public class SmListenerBrowseTbl extends BaseEntity implements Serializable  {
	
	public static final Find<Integer,SmListenerBrowseTbl> find = new Find<Integer,SmListenerBrowseTbl>(){};
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSetId;

	private int frequency;
	
	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public SmListenerBrowseTbl() {
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

	public SmWebSetTbl getWebSetId() {
		return webSetId;
	}

	public void setWebSetId(SmWebSetTbl webSetId) {
		this.webSetId = webSetId;
	}

	
}