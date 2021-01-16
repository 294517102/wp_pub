package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.avaje.ebean.annotation.Where;
import com.google.common.collect.Sets;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the cm_friendly_link_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_friendly_link_tbl")
@NamedQuery(name="CmFriendlyLinkTbl.findAll", query="SELECT c FROM CmFriendlyLinkTbl c")
public class CmFriendlyLinkTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,CmFriendlyLinkTbl> find = new Find<Integer,CmFriendlyLinkTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="is_delete",insertable=false)
	private int isDelete;

	private String name;

	private String url;
	
	private Integer  sequence;

	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;
	
	@ManyToOne
	@JoinColumn(name="logo_id")
	private SmAccessoryTbl logo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="parent_id")
	private CmFriendlyLinkTbl parent;
	
	@OneToMany(mappedBy="parent")
	@Where(clause="${ta}.is_delete=-1")
	private Set<CmFriendlyLinkTbl> children =  Sets.newHashSet();

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	public CmFriendlyLinkTbl() {
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

	public int getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getName() {
		return this.name;
	}
	
	

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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

	public SmAccessoryTbl getLogo() {
		return logo;
	}

	public void setLogo(SmAccessoryTbl logo) {
		this.logo = logo;
	}

	public CmFriendlyLinkTbl getParent() {
		return parent;
	}

	public void setParent(CmFriendlyLinkTbl parent) {
		this.parent = parent;
	}

	public Set<CmFriendlyLinkTbl> getChildren() {
		return children;
	}

	public void setChildren(Set<CmFriendlyLinkTbl> children) {
		this.children = children;
	}
}