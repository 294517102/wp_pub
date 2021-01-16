package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the cm_small_content_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_small_content_tbl")
@NamedQuery(name="CmSmallContentTbl.findAll", query="SELECT s FROM CmSmallContentTbl s")
public class CmSmallContentTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,CmSmallContentTbl> find = new Find<Integer,CmSmallContentTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name="creator_id")
	private SmUserTbl creator;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="insert_time",insertable=false)
	private Date insertTime;
	
	@Column(name="describes")
	private String describes;
	
	private String url;
	
	private String keyword;

	private String title;

	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;
	
	@Column(name="is_delete",insertable=false)
	private int isDelete;
	
	//bi-directional many-to-one association to SmAccessoryTbl
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="accessory_id")
	private SmAccessoryTbl accessory;
	
	
	public CmSmallContentTbl(){
		isDelete=-1;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SmUserTbl getCreator() {
		return creator;
	}

	public void setCreator(SmUserTbl creator) {
		this.creator = creator;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public SmAccessoryTbl getAccessory() {
		return accessory;
	}

	public void setAccessory(SmAccessoryTbl accessory) {
		this.accessory = accessory;
	}

	@Override
	public String toString() {
		return "CmSmallContentTbl [id=" + id + ", creator=" + creator + ", insertTime=" + insertTime + ", describes="
				+ describes + ", keyword=" + keyword + ", title=" + title + ", webSet=" + webSet + ", isDelete="
				+ isDelete + ", accessory=" + accessory + "]";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SmAccessoryTbl getAcc() {
		return getAccessory();
	}
	
	public String getDesc(){
		return this.getDescribes();
	}

	



	
	
	

}