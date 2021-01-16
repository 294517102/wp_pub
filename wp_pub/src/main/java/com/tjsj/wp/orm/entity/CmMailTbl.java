package com.tjsj.wp.orm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;


/**
 * The persistent class for the cm_mail_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_mail_tbl")
@NamedQuery(name="CmMailTbl.findAll", query="SELECT c FROM CmMailTbl c")
public class CmMailTbl extends com.tjsj.m_util.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,CmMailTbl> find = new Find<Integer,CmMailTbl>(){};
	@Id
	private int id;

	private String address;

	@Column(name="is_delete")
	private int isDelete;

	private String password;

	private String username;

	@OneToOne
	@JoinColumn(name="webset_id")
	private SmWebSetTbl webset;
	
	@ManyToOne
	@JoinColumn(name="mail_config_id")
	private CmMailConfigTbl mailConfig;
	
	public CmMailTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public SmWebSetTbl getWebset() {
		return webset;
	}

	public void setWebset(SmWebSetTbl webset) {
		this.webset = webset;
	}

	public CmMailConfigTbl getMailConfig() {
		return mailConfig;
	}

	public void setMailConfig(CmMailConfigTbl mailConfig) {
		this.mailConfig = mailConfig;
	}


}