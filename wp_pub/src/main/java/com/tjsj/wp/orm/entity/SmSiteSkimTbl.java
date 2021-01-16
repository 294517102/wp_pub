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
import com.tjsj.m_util.entity.BaseEntity;



/**
 * The persistent class for the cm_web_set_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="sm_site_skim_tbl")
@NamedQuery(name="SmSiteSkimTbl.findAll", query="SELECT s FROM SmSiteSkimTbl s")
public class SmSiteSkimTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,SmSiteSkimTbl> find = new Find<Integer,SmSiteSkimTbl>(){};
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="pv_skim")
	private String pvSkim;
	
	@Column(name="uv_skim")
	private String uvSkim;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="inserttime")
	private Date insertTime;
	
	//与webset表关联
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPvSkim() {
		return pvSkim;
	}

	public void setPvSkim(String pvSkim) {
		this.pvSkim = pvSkim;
	}

	public String getUvSkim() {
		return uvSkim;
	}

	public void setUvSkim(String uvSkim) {
		this.uvSkim = uvSkim;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	@Override
	public String toString() {
		return "SmSiteSkimTbl [id=" + id + ", pvSkim=" + pvSkim + ", uvSkim=" + uvSkim + ", insertTime=" + insertTime
				+ ", webSet=" + webSet + "]";
	}
	public SmSiteSkimTbl() {
		super();
	}
	

	

}