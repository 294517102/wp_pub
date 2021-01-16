package com.tjsj.wp.orm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the bn_pic_web_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="bn_pic_web_tbl")
@NamedQuery(name="BnPicWebTbl.findAll", query="SELECT b FROM BnPicWebTbl b")
public class BnPicWebTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,BnPicWebTbl> find = new Find<Integer,BnPicWebTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="insert_time",insertable=false,updatable=false)
	private String insertTime;
	@Column(name="is_delete")
	private Integer isDelete;
	
	private Integer state;

	private String name;

	private String url;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private SmUserTbl user;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pic_id")
	private SmAccessoryTbl pic;
	
	

	public BnPicWebTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
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
	public SmUserTbl getUser() {
		return user;
	}
	public void setUser(SmUserTbl user) {
		this.user = user;
	}

	public SmAccessoryTbl getPic() {
		return pic;
	}
	public void setPic(SmAccessoryTbl pic) {
		this.pic = pic;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}


}