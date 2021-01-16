package com.tjsj.wp.orm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the cm_message_config_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_sms_config_tbl")
@NamedQuery(name="CmMessageConfigTbl.findAll", query="SELECT c FROM CmSmsConfigTbl c")
public class CmSmsConfigTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,CmSmsConfigTbl> find = new Find<Integer,CmSmsConfigTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;

	private String password;

	@Column(name="to_count")
	private int toCount;

	private int total;
	
	private String userid;

	private String account;
	
	@OneToOne(mappedBy="messageConfig")
	private SmWebSetTbl webSet;
	
	private String signature;

	public CmSmsConfigTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getToCount() {
		return this.toCount;
	}

	public void setToCount(int toCount) {
		this.toCount = toCount;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "CmSmsConfigTbl [id=" + id + ", password=" + password + ", toCount=" + toCount + ", total=" + total
				+ ", userid=" + userid + ", account=" + account + ", webSet=" + webSet + ", signature=" + signature
				+ "]";
	}

	
}