package com.tjsj.wp.orm.entity;


import java.io.Serializable;

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
 * The persistent class for the sm_listener_browse_ext_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="sm_listener_browse_ext_tbl")
@NamedQuery(name="SmListenerBrowseExtTbl.findAll", query="SELECT s FROM SmListenerBrowseExtTbl s")
public class SmListenerBrowseExtTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,SmListenerBrowseExtTbl> find = new Find<Integer,SmListenerBrowseExtTbl>(){};
	@Id
	private int id;

	@Column(name="article_id")
	private int articleId;

	private String ip;

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

	public SmListenerBrowseExtTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArticleId() {
		return this.articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public SmWebSetTbl getWebSetId() {
		return webSetId;
	}

	public void setWebSetId(SmWebSetTbl webSetId) {
		this.webSetId = webSetId;
	}

}