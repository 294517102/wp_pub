package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the sm_template_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="sm_template_tbl")
@NamedQuery(name="SmTemplateTbl.findAll", query="SELECT s FROM SmTemplateTbl s")
public class SmTemplateTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="insert_time")
	private Timestamp insertTime;

	private String name;

	private String path;

	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;

	//bi-directional many-to-one association to SmResTbl
	@OneToMany(mappedBy="smTemplateTbl")
	private Set<SmResTbl> smResTbls;
	
	@OneToOne(mappedBy="templateUsed")
	private  SmWebSetTbl  webSetUsed;

	public SmTemplateTbl() {
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

	

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	public Set<SmResTbl> getSmResTbls() {
		return this.smResTbls;
	}

	public void setSmResTbls(Set<SmResTbl> smResTbls) {
		this.smResTbls = smResTbls;
	}

	public SmResTbl addSmResTbl(SmResTbl smResTbl) {
		getSmResTbls().add(smResTbl);
		smResTbl.setSmTemplateTbl(this);

		return smResTbl;
	}

	public SmWebSetTbl getWebSetUsed() {
		return webSetUsed;
	}

	public void setWebSetUsed(SmWebSetTbl webSetUsed) {
		this.webSetUsed = webSetUsed;
	}

	public SmResTbl removeSmResTbl(SmResTbl smResTbl) {
		getSmResTbls().remove(smResTbl);
		smResTbl.setSmTemplateTbl(null);

		return smResTbl;
	}

}