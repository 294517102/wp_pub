package com.tjsj.wp.orm.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the cm_code_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_code_tbl")
@NamedQuery(name="CmCodeTbl.findAll", query="SELECT c FROM CmCodeTbl c")
public class CmCodeTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,CmCodeTbl> find = new Find<Integer,CmCodeTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String colname;

	private String descname;

	private String remark;

	private String tablename;

	private int value;

	public CmCodeTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColname() {
		return this.colname;
	} 

	public void setColname(String colname) {
		this.colname = colname;
	}

	public String getDescname() {
		return this.descname;
	}

	public void setDescname(String descname) {
		this.descname = descname;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}