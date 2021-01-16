package com.tjsj.wp.orm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;



/**
 * The persistent class for the cm_mail_config_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_mail_config_tbl")
@NamedQuery(name="CmMailConfigTbl.findAll", query="SELECT c FROM CmMailConfigTbl c")
public class CmMailConfigTbl extends com.tjsj.m_util.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,CmMailConfigTbl> find = new Find<Integer,CmMailConfigTbl>(){};
	
	@Id
	private int id;

	@Column(name="mail_server_host")
	private String mailServerHost;

	@Column(name="mail_server_port")
	private String mailServerPort;

	@Column(name="supplier_name")
	private String supplierName;

	public CmMailConfigTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMailServerHost() {
		return this.mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return this.mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}