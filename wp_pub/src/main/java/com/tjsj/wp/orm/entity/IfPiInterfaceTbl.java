package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

import org.springframework.format.annotation.DateTimeFormat;

import com.avaje.ebean.annotation.Cache;
import com.google.common.collect.Lists;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the if_pi_interface_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="if_pi_interface_tbl") 
@NamedQuery(name="IfPiInterfaceTbl.findAll", query="SELECT i FROM IfPiInterfaceTbl i")
public class IfPiInterfaceTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,IfPiInterfaceTbl> find = new Find<Integer,IfPiInterfaceTbl>(){};
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="domain_scope",updatable=false)
	private int domainScope;

	private String iid;

	@Column(name="insert_time",updatable=false,insertable=false)
	private Timestamp insertTime;

	private String name;

	private int status;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date deadline;

	@JoinColumn(name="user_id")
	@ManyToOne
	private SmUserTbl user;

	@JoinColumn(name="webset_id")
	@ManyToOne
	private SmWebSetTbl webset;

	@Column(name="is_delete")
	private int isDelete;
	//bi-directional many-to-one association to IfPiInterface_serviceTbl
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch=FetchType.EAGER)
    @JoinTable(name = "if_pi_interface_service_tbl", joinColumns = { @JoinColumn(name ="interface_id" )}, inverseJoinColumns = { @JoinColumn(name = "service_id") })
	private List<IfPiServiceTbl> services = Lists.newArrayList();

	public IfPiInterfaceTbl() {
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDomainScope() {
		return this.domainScope;
	}

	public void setDomainScope(int domainScope) {
		this.domainScope = domainScope;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getIid() {
		return this.iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
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

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public SmUserTbl getUser() {
		return user;
	}

	public void setUser(SmUserTbl user) {
		this.user = user;
	}

	public SmWebSetTbl getWebset() {
		return webset;
	}

	public List<IfPiServiceTbl> getServices() {
		return services;
	}

	public void setServices(List<IfPiServiceTbl> services) {
		this.services = services;
	}

	public void setWebset(SmWebSetTbl webset) {
		this.webset = webset;
	}

}