package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.google.common.collect.Sets;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the sm_icon_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="sm_icon_tbl")
@NamedQuery(name="SmIconTbl.findAll", query="SELECT s FROM SmIconTbl s")
public class SmIconTbl  extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,SmIconTbl> find = new Find<Integer,SmIconTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String describes;
	
	private String url;
	
	private Integer type;
	
	private String subscript;
	
	@OneToMany(mappedBy="icon",fetch=FetchType.LAZY)
	private Set<CmColumnTbl> columns = Sets.newHashSet();
	
	@OneToMany(mappedBy="icon",fetch=FetchType.LAZY)
	private Set<SmModelTbl> models = Sets.newHashSet();
	
	public Set<CmColumnTbl> getColumns() {
		return columns;
	}

	public void setColumns(Set<CmColumnTbl> columns) {
		this.columns = columns;
	}

	public Set<SmModelTbl> getModels() {
		return models;
	}

	public String getSubscript() {
		return subscript;
	}

	public void setSubscript(String subscript) {
		this.subscript = subscript;
	}

	public void setModels(Set<SmModelTbl> models) {
		this.models = models;
	}

	public SmIconTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}