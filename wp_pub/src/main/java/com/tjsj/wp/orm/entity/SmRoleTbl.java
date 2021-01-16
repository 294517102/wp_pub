package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.avaje.ebean.annotation.Where;
import com.google.common.collect.Sets;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the sm_role_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="sm_role_tbl")
@NamedQuery(name="SmRoleTbl.findAll", query="SELECT s FROM SmRoleTbl s")
public class SmRoleTbl extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final Find<Integer,SmRoleTbl> find = new Find<Integer,SmRoleTbl>(){};

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
 
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creator")
	private SmUserTbl creator;

	@Column(name="insert_time",updatable=false,insertable=false)
	private Timestamp insertTime;

	private String name;
	
	private String keyword;
	
	private Integer state;
	
	@JoinColumn(name="webSet_id")
	@ManyToOne
	private SmWebSetTbl webSet;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "roles", fetch = FetchType.LAZY)
	private Set<SmUserTbl> users = Sets.newHashSet();
	
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch=FetchType.EAGER)
    @JoinTable(name = "sm_role_model_tbl", joinColumns = { @JoinColumn(name ="role_id" )}, inverseJoinColumns = { @JoinColumn(name = "model_id") })
	private Set<SmModelTbl> models = Sets.newHashSet();
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch=FetchType.EAGER)
    @JoinTable(name = "sm_role_column_tbl", joinColumns = { @JoinColumn(name ="role_id" )}, inverseJoinColumns = { @JoinColumn(name = "column_id") })
	@Where(clause="${ta}.is_delete=-1")
	private Set<CmColumnTbl> column = Sets.newHashSet();
	
	
	
	public Set<CmColumnTbl> getColumn() {
		return column;
	}

	public void setColumn(Set<CmColumnTbl> column) {
		this.column = column;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	//get/set方法
	public SmRoleTbl() {
	}

	public int getId() {
		return this.id;
	}

	public Set<SmUserTbl> getUsers() {
		return users;
	}

	public void setUsers(Set<SmUserTbl> users) {
		this.users = users;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Set<SmModelTbl> getModels() {
		return models;
	}

	public void setModels(Set<SmModelTbl> models) {
		this.models = models;
	}

	public SmUserTbl getCreator() {
		return creator;
	}

	public void setCreator(SmUserTbl creator) {
		this.creator = creator;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
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

	@Override
	public String toString() {
		return "SmRoleTbl [id=" + id  + ", insertTime=" + insertTime + ", name=" + name
				+ ", keyword=" + keyword  + "]";
	}

	 
}