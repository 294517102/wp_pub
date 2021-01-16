package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.avaje.ebean.annotation.Cache;
import com.avaje.ebean.annotation.Where;
import com.google.common.collect.Sets;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the sm_article_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_article_tbl")
@NamedQuery(name="CmArticleTbl.findAll", query="SELECT s FROM CmArticleTbl s")
public class CmArticleTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,CmArticleTbl> find = new Find<Integer,CmArticleTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String author;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="creator")
	private SmUserTbl creator;
	
	private String describes;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="insert_time",insertable=false,updatable=false)
	private Date insertTime;

	//2017-11-24  zp
	@ManyToOne(fetch=FetchType.LAZY)
	@Column(name="parent_id")
	private CmArticleTbl parent;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date issueTime;

	private String keyword;
	
	private Integer sequence;
    
	@Column(name="source")
	private String source;

	private String title;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	private String url;
	private int state;
	@Column(name="is_delete")
	private int isDelete;
	
	@Column(name="is_home")
	private Integer isHome;
	
	@Column(name="view_times")
	private  int viewTimes;

	//与cmarticleexttbl关联
	@OneToOne(mappedBy="article",fetch=FetchType.EAGER ,cascade={CascadeType.ALL})
	private CmArticleExtTbl articleExt;
	
	@OneToMany(mappedBy="parent")
	@Where(clause="${ta}.is_delete=-1")
	private Set<CmArticleTbl> children =  Sets.newHashSet();
	
	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;

	
	//bi-directional many-to-one association to SmAccessoryTbl
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="thumbnail")
	private SmAccessoryTbl thumbnail;

	//bi-directional many-to-one association to SmColumnTbl
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="column_id")
	private CmColumnTbl column;
	
	
	@ManyToOne
	@JoinColumn(name="template_id")
	private CmArticleTemplateTbl atemplate;

	
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch=FetchType.EAGER)
    @JoinTable(name = "sm_article_accessory_tbl", joinColumns = { @JoinColumn(name ="article_id" )}, inverseJoinColumns = { @JoinColumn(name = "accessory_id") })
	private List<SmAccessoryTbl> pic=new ArrayList<>();

	
	public int getViewTimes() {
		return viewTimes;
	}

	public void setViewTimes(int viewTimes) {
		this.viewTimes = viewTimes;
	}

	//get/set方法
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	
	
	
	public CmArticleExtTbl getArticleExt() {
		return articleExt;
	}

	public void setArticleExt(CmArticleExtTbl articleExt) {
		this.articleExt = articleExt;
	}

	public CmArticleTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}




	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getIssueTime() {
		return this.issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SmUserTbl getCreator() {
		return creator;
	}

	public void setCreator(SmUserTbl creator) {
		this.creator = creator;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	public SmAccessoryTbl getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(SmAccessoryTbl thumbnail) {
		this.thumbnail = thumbnail;
	}

	public CmColumnTbl getColumn() {
		return column;
	}



	public void setColumn(CmColumnTbl column) {
		this.column = column;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}


	public CmArticleTemplateTbl getAtemplate() {
		return atemplate;
	}

	public void setAtemplate(CmArticleTemplateTbl atemplate) {
		this.atemplate = atemplate;
	}
	/*@Override
	public String toString() {
		return "CmArticleTbl [id=" + id + ", author=" + author + ", creator=" + creator + ", describes=" + describes
				+ ", insertTime=" + insertTime + ", issueTime=" + issueTime + ", keyword=" + keyword + ", orderby="
				+ orderby + ", source=" + source + ", title=" + title + ", updateTime=" + updateTime + ", url=" + url
				+ ", state=" + state + ", isDelete=" + isDelete + ", articleExt=" + articleExt + ", webSet=" + webSet
				+ ", thumbnail=" + thumbnail + ", column=" + column + ", pic=" + pic + "]";
	}*/

	public CmArticleExtTbl getExt(){
		return getArticleExt();
	}
	
	public String getDesc(){
		return getDescribes();
	}
	public SmAccessoryTbl getTh(){
		return getThumbnail();
	}

	public CmArticleTbl getParent() {
		return parent;
	}

	public void setParent(CmArticleTbl parent) {
		this.parent = parent;
	}

	public Set<CmArticleTbl> getChildren() {
		return children;
	}

	public void setChildren(Set<CmArticleTbl> children) {
		this.children = children;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public List<SmAccessoryTbl> getPic() {
		return pic;
	}

	public void setPic(List<SmAccessoryTbl> pic) {
		this.pic = pic;
	}

	public Integer getIsHome() {
		return isHome;
	}

	public void setIsHome(Integer isHome) {
		this.isHome = isHome;
	}
	
}