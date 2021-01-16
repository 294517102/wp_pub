package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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

@Entity
@Cache(enableQueryCache=true)
@Table(name="bn_video_tbl")
@NamedQuery(name="BnVideoTbl.findAll", query="SELECT v FROM BnVideoTbl v")
public class BnVideoTbl extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,BnVideoTbl> find = new Find<Integer,BnVideoTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String title;
	private String name;
	
	private String info;
	
	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="insert_time",insertable=false,updatable=false)
	private Date insertTime;
	
	@Column(name="is_delete")
	private int isDelete;//文章是否删除，1，表示未删除，-1已删除
	@Column(name="video_id")
	private String videoId;//文章是否删除，1，表示未删除，-1已删除

	private int type;

	
	@Column(name="play_url")
	private String playUrl;
	
	@JoinColumn(name="web_set_id",updatable=false)
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REMOVE)
	private SmWebSetTbl webSet;
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
}