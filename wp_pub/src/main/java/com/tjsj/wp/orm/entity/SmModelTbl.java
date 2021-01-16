package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.google.common.collect.Sets;
import com.tjsj.m_util.entity.BaseEntity;



/**
 * The persistent class for the sm_model_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="sm_model_tbl")
@NamedQuery(name="SmModelTbl.findAll", query="SELECT s FROM SmModelTbl s")
public class SmModelTbl extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,SmModelTbl> find = new Find<Integer,SmModelTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Integer code;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creator")
	private SmUserTbl creator;
	
	@ManyToOne
	@JoinColumn(name="icon")
	private SmIconTbl icon;

	private Integer ismenu;

	private String name;
	
	private Integer sequence;

	@ManyToOne
	@JoinColumn(name="parent_id")
	private SmModelTbl parent;
	
	@OneToMany(mappedBy="parent")
	@OrderBy("sequence asc,id asc")
	private Set<SmModelTbl> children=Sets.newHashSet();
	
	@OneToMany(mappedBy="model",fetch=FetchType.LAZY)
	private Set<CmColumnTbl> columns = Sets.newHashSet();

	private String url;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "models", fetch = FetchType.LAZY)
	private Set<SmRoleTbl> roles = Sets.newHashSet();
	
	//get/set方法
	public SmModelTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}



	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Set<CmColumnTbl> getColumns() {
		return columns;
	}

	public void setColumns(Set<CmColumnTbl> columns) {
		this.columns = columns;
	}

	public Set<SmRoleTbl> getRoles() {
		return roles;
	}

	public void setRoles(Set<SmRoleTbl> roles) {
		this.roles = roles;
	}

	public SmUserTbl getCreator() {
		return creator;
	}

	public void setCreator(SmUserTbl creator) {
		this.creator = creator;
	}

	public SmModelTbl getParent() {
		return parent;
	}

	public void setParent(SmModelTbl parent) {
		this.parent = parent;
	}

	public Set<SmModelTbl> getChildren() {
		return children;
	}

	public void setChildren(Set<SmModelTbl> children) {
		this.children = children;
	}

	

	public SmIconTbl getIcon() {
		return icon;
	}

	public void setIcon(SmIconTbl icon) {
		this.icon = icon;
	}

	public int getIsmenu() {
		return this.ismenu;
	}

	public void setIsmenu(Integer ismenu) {
		this.ismenu = ismenu;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "SmModelTbl [id=" + id + ", code=" + code + ", creator=" + creator + ", icon=" + icon + ", ismenu="
				+ ismenu + ", name=" + name + ", parent=" + parent + ", children=" + children + ", url=" + url
				+ ", roles=" + roles + "]";
	}



}