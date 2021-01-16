package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the sm_res_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="sm_res_tbl")
@NamedQuery(name="SmResTbl.findAll", query="SELECT s FROM SmResTbl s")
public class SmResTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="desc_")
	private String desc;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="insert_time")
	private Timestamp insertTime;

	private String name;

	private String path;

	private String url;

	//bi-directional many-to-one association to SmTemplateTbl
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="template_id")
	private SmTemplateTbl smTemplateTbl;

	public SmResTbl() {
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SmTemplateTbl getSmTemplateTbl() {
		return this.smTemplateTbl;
	}

	public void setSmTemplateTbl(SmTemplateTbl smTemplateTbl) {
		this.smTemplateTbl = smTemplateTbl;
	}

}