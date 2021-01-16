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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.avaje.ebean.annotation.Cache;
import com.avaje.ebean.annotation.Where;
import com.google.common.collect.Sets;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the sm_column_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_column_tbl")
@NamedQuery(name="CmColumnTbl.findAll", query="SELECT s FROM CmColumnTbl s")
public class CmColumnTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,CmColumnTbl> find = new Find<Integer,CmColumnTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name="creator")
	private SmUserTbl creator;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datatime;
	
	@Column(name="describes")
	private String describes;

	private String keyword;

	@ManyToOne
	@JoinColumn(name="model_id")
	private SmModelTbl model;
	/*@Column(name="column_rank")
	private int columnRank;*/

	@ManyToOne
	@JoinColumn(name="icon",updatable=true)
	private SmAccessoryTbl icon;
	
	
	@Column(name="sequence")
	@OrderBy("sequence asc")
	private Integer sequence;

	private String title;

	private Integer type;

	private String url;
	
	private int state;
	@Column(name="is_delete")
	private int isDelete;

	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;

	//bi-directional many-to-one association to SmArticleTbl
	@OneToMany(mappedBy="column",fetch=FetchType.EAGER,cascade={CascadeType.ALL})
	@Where(clause="${ta}.is_delete=-1")
	private Set<CmArticleTbl> articles;
	
	//bi-directional many-to-one association to SmAccessoryTbl
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="thumbnail")
	private SmAccessoryTbl thumbnail;

	//bi-directional many-to-one association to SmColumnTbl
//	@ManyToOne(fetch=FetchType.EAGER)
	@ManyToOne()
	private CmColumnTbl parent;

	//bi-directional many-to-one association to SmColumnTbl
	
	@OneToMany(mappedBy="parent",fetch=FetchType.EAGER)
	@Where(clause="${ta}.is_delete=-1")
	private Set<CmColumnTbl> children;
	
	@ManyToOne
	@JoinColumn(name="template_id")
	private CmArticleTemplateTbl template;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch=FetchType.EAGER)
    @JoinTable(name = "cm_column_file_tbl", joinColumns = { @JoinColumn(name ="column_id" )}, inverseJoinColumns = { @JoinColumn(name = "file_id") })
	private Set<SmAccessoryTbl> fileList = Sets.newHashSet();
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch=FetchType.EAGER)
    @JoinTable(name = "sm_role_column_tbl", joinColumns = { @JoinColumn(name ="column_id" )}, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<SmRoleTbl> roleList = Sets.newHashSet();
	
	public SmAccessoryTbl getIcon() {
		return icon;
	}
	public void setIcon(SmAccessoryTbl icon) {
		this.icon = icon;
	}
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

	public CmColumnTbl() {
		isDelete = -1;
	}

	public int getId() {
		return this.id;
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

	public SmAccessoryTbl getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(SmAccessoryTbl thumbnail) {
		this.thumbnail = thumbnail;
	}

	public CmColumnTbl getParent() {
		return parent;
	}

	public void setParent(CmColumnTbl parent) {
		this.parent = parent;
	}

	public Date getDatatime() {
		return this.datatime;
	}

	public void setDatatime(Date datatime) {
		this.datatime = datatime;
	}


	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}



	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	

	public SmModelTbl getModel() {
		return model;
	}

	public void setModel(SmModelTbl model) {
		this.model = model;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	

	public Set<CmArticleTbl> getArticles() {
		return articles;
	}

	public void setArticles(Set<CmArticleTbl> articles) {
		this.articles = articles;
	}

	public Set<CmColumnTbl> getChildren() {
		return children;
	}

	public void setChildren(Set<CmColumnTbl> children) {
		this.children = children;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public CmArticleTemplateTbl getTemplate() {
		return template;
	}
	public void setTemplate(CmArticleTemplateTbl template) {
		this.template = template;
	}
	@Override
	public String toString() {
		return "CmColumnTbl [id=" + id + ", creator=" + creator + ", datatime=" + datatime + ", describes=" + describes
				+ ", keyword=" + keyword + ", model=" + model + ", icon=" + icon + ", sequence=" + sequence + ", title="
				+ title + ", type=" + type + ", url=" + url + ", state=" + state + ", isDelete=" + isDelete
				+ ", webSet=" + webSet + ", articles=" + articles + ", thumbnail=" + thumbnail + ", parent=" + parent
				+ ", children=" + children + ", template=" + template + "]";
	}

	public  Set<CmArticleTbl> getAts(){
		return getArticles();
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Set<SmAccessoryTbl> getFileList() {
		return fileList;
	}
	public void setFileList(Set<SmAccessoryTbl> fileList) {
		this.fileList = fileList;
	}
	public Set<SmRoleTbl> getRoleList() {
		return roleList;
	}
	public void setRoleList(Set<SmRoleTbl> roleList) {
		this.roleList = roleList;
	}

}