package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the sm_app_version_list_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="sm_version_tbl")
@NamedQuery(name="SmVersionTbl.findAll", query="SELECT s FROM SmVersionTbl s")
public class SmVersionTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final Find<Integer,SmVersionTbl> find = new Find<Integer,SmVersionTbl>(){};

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String contents;

	@Column(name="down_url")
	private String downUrl;

	@Column(name="major_update")
	private String majorUpdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="version_id")
	private String versionId;
	
	@Column(name="release_flag")
	private Integer releaseFlag;
	
	@Column(name="real_version")
	private Integer realVersion;
	
	private Integer type;

	public SmVersionTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContents() {
		return this.contents;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getDownUrl() {
		return this.downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public String getMajorUpdate() {
		return this.majorUpdate;
	}

	public void setMajorUpdate(String majorUpdate) {
		this.majorUpdate = majorUpdate;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getVersionId() {
		return this.versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public Integer getReleaseFlag() {
		return releaseFlag;
	}

	public void setReleaseFlag(Integer releaseFlag) {
		this.releaseFlag = releaseFlag;
	}

	public Integer getRealVersion() {
		return realVersion;
	}

	public void setRealVersion(Integer realVersion) {
		this.realVersion = realVersion;
	}
	@Override
	public String toString() {
		return "SmAppVersionListTbl [id=" + id + ", contents=" + contents + ", downUrl=" + downUrl + ", majorUpdate="
				+ majorUpdate + ", updateTime=" + updateTime + ", versionId=" + versionId + "]";
	}
	

}