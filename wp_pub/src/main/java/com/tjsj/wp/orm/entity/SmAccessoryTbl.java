package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.avaje.ebean.annotation.Cache;
import com.google.common.collect.Sets;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the sm_accessory_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="sm_accessory_tbl")
@NamedQuery(name="SmAccessoryTbl.findAll", query="SELECT s FROM SmAccessoryTbl s")
public class SmAccessoryTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,SmAccessoryTbl> find = new Find<Integer,SmAccessoryTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date insert_time;

	@ManyToOne
	@JoinColumn(name="creator")
	private SmUserTbl creator;

	@Column(name="delete_status")
	private int deleteStatus;

	private String ext;

	private int height;

	private String info;

	private String name;

	private String path;

	private int size;

	private String url;
	
	@Column(name="original_url")
	private String originalUrl;
	
	@Column(name="original_name")
	private String originalName;

	private int width;
	//与cmArticleTbl关联
	@OneToMany(mappedBy="thumbnail",fetch=FetchType.LAZY)
	private Set<CmArticleTbl> article= Sets.newHashSet();
	
	//与cmColumnTbl关联
	@OneToMany(mappedBy="thumbnail",fetch=FetchType.LAZY)
	private Set<CmColumnTbl> column=Sets.newHashSet();
	
	//与SmHomeSlide表关联
	@OneToMany(fetch=FetchType.LAZY,mappedBy="accessory")
	private Set<CmHomeSlideTbl> homeSlides= Sets.newHashSet();
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="article_id")
	private CmArticleTbl art;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch=FetchType.EAGER)
    @JoinTable(name = "cm_column_file_tbl", joinColumns = { @JoinColumn(name ="file_id" )}, inverseJoinColumns = { @JoinColumn(name = "column_id") })
	private Set<CmColumnTbl> columnList = Sets.newHashSet();

	@Column(name="download_times")
	private Integer downloadTimes;
	//get/set方法
	public SmAccessoryTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}

	public SmUserTbl getCreator() {
		return creator;
	}

	public void setCreator(SmUserTbl creator) {
		this.creator = creator;
	}

	public int getDeleteStatus() {
		return this.deleteStatus;
	}

	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getExt() {
		return this.ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	
	
	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Set<CmArticleTbl> getArticle() {
		return article;
	}

	public void setArticle(Set<CmArticleTbl> article) {
		this.article = article;
	}

	public Set<CmColumnTbl> getColumn() {
		return column;
	}

	public void setColumn(Set<CmColumnTbl> column) {
		this.column = column;
	}

	public Set<CmHomeSlideTbl> getHomeSlides() {
		return homeSlides;
	}

	public void setHomeSlides(Set<CmHomeSlideTbl> homeSlides) {
		this.homeSlides = homeSlides;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CmArticleTbl getArt() {
		return art;
	}

	public void setArt(CmArticleTbl art) {
		this.art = art;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	@Override
	public String toString() {
		return "SmAccessoryTbl [id=" + id + ", insert_time=" + insert_time + ", deleteStatus=" + deleteStatus + ", ext="
				+ ext + ", height=" + height + ", info=" + info + ", name=" + name + ", path=" + path + ", size=" + size
				+ ", url=" + url + ", originalUrl=" + originalUrl + ", originalName=" + originalName + ", width="
				+ width + ", article=" + article + ", column=" + column + "]";
	}
	
	public Set<CmColumnTbl> getColumnList() {
		return columnList;
	}

	public void setColumnList(Set<CmColumnTbl> columnList) {
		this.columnList = columnList;
	}

	public Integer getDownloadTimes() {
		return downloadTimes;
	}

	public void setDownloadTimes(Integer downloadTimes) {
		this.downloadTimes = downloadTimes;
	}

	public String getOurl(){
		return getOriginalUrl();
	}
}