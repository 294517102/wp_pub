package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.avaje.ebean.annotation.Where;
import com.google.common.collect.Sets;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the cm_web_set_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="sm_web_set_tbl")
@NamedQuery(name="SmWebSetTbl.findAll", query="SELECT c FROM SmWebSetTbl c")
public class SmWebSetTbl  extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,SmWebSetTbl> find = new Find<Integer,SmWebSetTbl>(){};
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="com_address")
	private String comAddress;

	@Column(name="com_email")
	private String comEmail;

	@Column(name="com_name")
	private String comName;

	private String copyright;
	
	private String protocol;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="parent")
	private SmWebSetTbl  parent;
	
	@OneToMany(mappedBy="parent",fetch=FetchType.EAGER)
	private Set<SmWebSetTbl> chilren = Sets.newHashSet();

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ali_config_id")
	private BnAliPayConfigTbl aliConfig;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="we_chart_config_id")
	private BnWechatConfigTbl weChatConfig;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="message_id")
	private CmSmsConfigTbl messageConfig;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="we_chat_id")
	private IfWechatTbl weChat;
	
	@OneToOne(mappedBy="webset",fetch=FetchType.EAGER)
	private CmMailTbl mail;
	
	//与用户关联
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="creator")
	private SmUserTbl creator;

	private String describes;

	private String keyword;

	private String linker;

	@Column(name="logo_url")
	private String logoUrl;

	private String record;

	private String tel;
	
	private String domain;

	private String title;

	@Column(name="title_additional")
	private String titleAdditional;
	@Column(name="zip_code")
	private String zipCode;

	@Column(name="nav_icon")
	private String navIcon;//导航栏图标
	
	private int state;//网站状态
	
	private Integer type;
	
	private String cname;
	
	
	private String language;
	
	@Column(name="record_url")
	private String recordUrl;//备案号链接
	
	@Column(name="insert_time")
	
	private Date  insertTime;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="template_id")
	private SmTemplateTbl templateUsed;
	
	@OneToMany(mappedBy="webSet",fetch=FetchType.EAGER)
	private List<SmRequestInterceptTbl>  requestIntercepts;
	
	

	@Column(name="site_id")
	private String siteId;//导航栏图标
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="webSet")
	@Where(clause="is_delete=-1")
	private Set<CmColumnTbl> columns = Sets.newHashSet();
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="webSet")
	private Set<CmArticleTbl> articles = Sets.newHashSet();
	//与comment表关联
	@OneToMany(fetch=FetchType.EAGER,mappedBy="webSet")
	private Set<CmCommentTbl> comments=Sets.newHashSet();
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="webSet")
	private Set<CmHomeSlideTbl> homeSlides = Sets.newHashSet();
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="webSet")
	private Set<SmUserTbl> users = Sets.newHashSet();
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="webSet")
	private Set<CmSmallContentTbl> smallContents = Sets.newHashSet();
	
	@OneToMany(mappedBy="webSet",fetch=FetchType.EAGER)	
	private Set<SmRoleTbl>  roles = Sets.newHashSet();
	
	@OneToMany(mappedBy="webSet",fetch=FetchType.EAGER)
	private Set<SmTemplateTbl> templates =  Sets.newHashSet();
	
	public SmWebSetTbl() {
	}

	//扩展函数	
	public CmColumnTbl getColumnByKeyword(String keyword){
		if(columns.size()<=0){
			return null;
		}
		for (CmColumnTbl c : columns) {
			if(c.getKeyword().equalsIgnoreCase(keyword)&&c.getIsDelete()==-1){
				return c;
			}
		}
		return null;
	}
	
	public String getComAddress() {
		return this.comAddress;
	}

	public String getLanguage() {
		return language;
	}

	public SmWebSetTbl getParent() {
		return parent;
	}

	public void setParent(SmWebSetTbl parent) {
		this.parent = parent;
	}

	public Set<SmWebSetTbl> getChilren() {
		return chilren;
	}

	public void setChilren(Set<SmWebSetTbl> chilren) {
		this.chilren = chilren;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public SmTemplateTbl getTemplateUsed() {
		return templateUsed;
	}

	public void setTemplateUsed(SmTemplateTbl templateUsed) {
		this.templateUsed = templateUsed;
	}

	public Set<SmTemplateTbl> getTemplates() {
		return templates;
	}

	public void setTemplates(Set<SmTemplateTbl> templates) {
		this.templates = templates;
	}

	public Set<SmUserTbl> getUsers() {
		return users;
	}
	
	

	public List<SmRequestInterceptTbl> getRequestIntercepts() {
		return requestIntercepts;
	}

	public void setRequestIntercepts(List<SmRequestInterceptTbl> requestIntercepts) {
		this.requestIntercepts = requestIntercepts;
	}

	public void setUsers(Set<SmUserTbl> users) {
		this.users = users;
	}

	public Set<SmRoleTbl> getRoles() {
		return roles;
	}

	public void setRoles(Set<SmRoleTbl> roles) {
		this.roles = roles;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setComAddress(String comAddress) {
		this.comAddress = comAddress;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getComEmail() {
		return this.comEmail;
	}

	public void setCreator(SmUserTbl creator) {
		this.creator = creator;
	}


	public void setComEmail(String comEmail) {
		this.comEmail = comEmail;
	}

	public String getComName() {
		return this.comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getCopyright() {
		return this.copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	

	public Set<CmColumnTbl> getColumns() {
		return columns;
	}


	public void setColumns(Set<CmColumnTbl> columns) {
		this.columns = columns;
	}


	public Set<CmArticleTbl> getArticles() {
		return articles;
	}


	public void setArticles(Set<CmArticleTbl> articles) {
		this.articles = articles;
	}

	
	public Set<CmCommentTbl> getComments() {
		return comments;
	}


	public void setComments(Set<CmCommentTbl> comments) {
		this.comments = comments;
	}


	public Set<CmHomeSlideTbl> getHomeSlides() {
		return homeSlides;
	}


	public void setHomeSlides(Set<CmHomeSlideTbl> homeSlides) {
		this.homeSlides = homeSlides;
	}


	public SmUserTbl getCreator() {
		return creator;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}



	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLinker() {
		return this.linker;
	}

	public void setLinker(String linker) {
		this.linker = linker;
	}

	public String getLogoUrl() {
		return this.logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getRecord() {
		return this.record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleAdditional() {
		return this.titleAdditional;
	}

	public void setTitleAdditional(String titleAdditional) {
		this.titleAdditional = titleAdditional;
	}



	public String getRecordUrl() {
		return recordUrl;
	}

	public void setRecordUrl(String recordUrl) {
		this.recordUrl = recordUrl;
	}

	public String getNavIcon() {
		return navIcon;
	}

	public void setNavIcon(String navIcon) {
		this.navIcon = navIcon;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public BnAliPayConfigTbl getAliConfig() {
		return aliConfig;
	}

	public void setAliConfig(BnAliPayConfigTbl aliConfig) {
		this.aliConfig = aliConfig;
	}

	public BnWechatConfigTbl getWeChatConfig() {
		return weChatConfig;
	}

	public void setWeChatConfig(BnWechatConfigTbl weChatConfig) {
		this.weChatConfig = weChatConfig;
	}

	public CmSmsConfigTbl getMessageConfig() {
		return messageConfig;
	}

	public void setMessageConfig(CmSmsConfigTbl messageConfig) {
		this.messageConfig = messageConfig;
	}

	public IfWechatTbl getWeChat() {
		return weChat;
	}

	public void setWeChat(IfWechatTbl weChat) {
		this.weChat = weChat;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String toString() {
		return "CmWebSetTbl [id=" + id + ", comAddress=" + comAddress + ", comEmail=" + comEmail + ", comName="
				+ comName + ", copyright=" + copyright + ", creator="
				+ ", describes=" + describes + ", keyword=" + keyword + ", linker=" + linker + ", logoUrl="
				+ logoUrl + ", record=" + record + ", tel=" + tel + ", domain=" + domain + ", title=" + title
				+ ", titleAdditional=" + titleAdditional + ", language=" + language
				+ ", recordUrl=" + recordUrl + "]";
	}

	public Set<CmSmallContentTbl> getSmallContents() {
		return smallContents;
	}

	public void setSmallContents(Set<CmSmallContentTbl> smallContents) {
		this.smallContents = smallContents;
	}

	public CmMailTbl getMail() {
		return mail;
	}

	public void setMail(CmMailTbl mail) {
		this.mail = mail;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}