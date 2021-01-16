package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.google.common.collect.Lists;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the if_pi_service_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="if_pi_service_tbl")
@NamedQuery(name="IfPiServiceTbl.findAll", query="SELECT i FROM IfPiServiceTbl i")
public class IfPiServiceTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,IfPiServiceTbl> find = new Find<Integer,IfPiServiceTbl>(){};
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;

	@Column(name="insert_time",insertable=false)
	private Timestamp insertTime;

	private int otype;

	private String parameter;
	
	@Column(name="entity_name")
	private String entityName;;

	@Column(name="result_name")
	private String resultName;

	@Column(name="sql_")
	private String sql;
	
	@Column(name="cascade_")
	private String cascade;

	private int stype;
	
	@Column(name="is_delete")
	private int isDelete;

	//@Column(name="user_id")
	//private int userId;
	
	@JoinColumn(name="user_id")
	@ManyToOne
	private SmUserTbl user;

	@JoinColumn(name="webset_id")
	@ManyToOne
	private SmWebSetTbl  webSet;
	//@Column(name="webset_id")
	//private int websetId;
	@ManyToMany(mappedBy="services",cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch=FetchType.LAZY)
	private List<IfPiInterfaceTbl> interfaces = Lists.newArrayList();
	
	public IfPiServiceTbl() {
	}

	public Integer getId() {
		return this.id;
	}

	public List<IfPiInterfaceTbl> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<IfPiInterfaceTbl> interfaces) {
		this.interfaces = interfaces;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public String getCascade() {
		return cascade;
	}

	public void setCascade(String cascade) {
		this.cascade = cascade;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public int getOtype() {
		return this.otype;
	}

	public void setOtype(int otype) {
		this.otype = otype;
	}

	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getResultName() {
		return this.resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	public String getSql() {
		return this.sql;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getStype() {
		return this.stype;
	}

	public void setStype(int stype) {
		this.stype = stype;
	}

	public SmUserTbl getUser() {
		return user;
	}

	public void setUser(SmUserTbl user) {
		this.user = user;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	@Override
	public String toString() {
		return "IfPiServiceTbl [id=" + id + ", name=" + name + ", insertTime=" + insertTime + ", otype=" + otype
				+ ", parameter=" + parameter + ", entityName=" + entityName + ", resultName=" + resultName + ", sql="
				+ sql + ", cascade=" + cascade + ", stype=" + stype + ", isDelete=" + isDelete
				+ ", interfaces=" + interfaces + "]";
	}

	
}