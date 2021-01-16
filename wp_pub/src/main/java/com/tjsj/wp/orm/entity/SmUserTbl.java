package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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

import com.alibaba.fastjson.annotation.JSONField;
import com.avaje.ebean.annotation.Cache;
import com.avaje.ebean.annotation.Where;
import com.google.common.collect.Sets;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the sm_user_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="sm_user_tbl")
@NamedQuery(name="SmUserTbl.findAll", query="SELECT s FROM SmUserTbl s")
public class SmUserTbl  extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	
	public static final Find<Integer,SmUserTbl> find = new Find<Integer,SmUserTbl>(){};
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String email;
	
//	1 普通用户 2会员 3系统用户 4超级管理员
	private Integer type;

	@Column(name="is_locked")
	private int isLocked;

	@Column(name="last_login")
	private Date lastLogin;

	@JSONField(serialize=false)
	private String password;

	private String last_ip_address;
	
	private String phone;
	
	@Column(name="dev_id")
	private String devId;
	
	@ManyToOne
	@JoinColumn(name="thumbnail")
	private SmAccessoryTbl thumbnail;
	
	@Column(name="is_delete")
	//@JSONField(serialize=false)
	private int isDelete;
	
	@Column(name="real_name")
	private String realName;
	
	@Column(name="is_audit")
	private Integer isAudit;
	
	private String token;

	private String remark;
	
	
	
	@JoinColumn(name="web_set",updatable=false)
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REMOVE)
	private SmWebSetTbl webSet;
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)	
	private SmUserTbl parent;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="parent",cascade=CascadeType.ALL)
	@Where(clause="isDelete = 0")
	private Set<SmUserTbl> children = Sets.newHashSet();

	private String username;
	
	@Column(name="insert_time",insertable=false,updatable=false)
	private Timestamp insertTime;

	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch=FetchType.LAZY)
    @JoinTable(name = "sm_user_role_tbl", joinColumns = { @JoinColumn(name ="user_id" )}, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	@Where(clause="${ta}.state=1")
	private Set<SmRoleTbl>  roles = Sets.newHashSet();
	
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY)
	private Set<SmRoleTbl> createdRoles = Sets.newHashSet();

	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY)
	private Set<CmColumnTbl> createdColumns = Sets.newHashSet();
	
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY)
	private Set<SmAccessoryTbl> createdAccessoris = Sets.newHashSet();
	
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY)
	private Set<CmArticleTbl> createdArticles= Sets.newHashSet();
	
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY)
	private Set<CmSmallContentTbl> smallContents= Sets.newHashSet();
	
	//用户与webset表关联
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY)
	private  Set<SmWebSetTbl> createdWebSets=Sets.newHashSet();
	
	//用户与home_slide表关联
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY)
	private  Set<CmHomeSlideTbl> createdHomeSlides=Sets.newHashSet();
	
	//用户与comment表关联
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY)
	private Set<CmCommentTbl> createdComment=Sets.newHashSet();
	
	@OneToMany(mappedBy="oPerator")
	private Set<CmSysLogTbl> sysLogs;
	
	//get/set方法
	public Set<SmWebSetTbl> getCreatedWebSets() {
		return createdWebSets;
	}

	public void setCreatedWebSets(Set<SmWebSetTbl> createdWebSets) {
		this.createdWebSets = createdWebSets;
	}

	public Set<CmHomeSlideTbl> getCreatedHomeSlides() {
		return createdHomeSlides;
	}

	public Set<CmSysLogTbl> getSysLogs() {
		return sysLogs;
	}

	public void setSysLogs(Set<CmSysLogTbl> sysLogs) {
		this.sysLogs = sysLogs;
	}

	public void setCreatedHomeSlides(Set<CmHomeSlideTbl> createdHomeSlides) {
		this.createdHomeSlides = createdHomeSlides;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Set<CmCommentTbl> getCreatedComment() {
		return createdComment;
	}

	public void setCreatedComment(Set<CmCommentTbl> createdComment) {
		this.createdComment = createdComment;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	public SmUserTbl() {
		isDelete = -1;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<SmRoleTbl> getRoles() {
		return roles;
	}

	public Set<CmArticleTbl> getCreatedArticles() {
		return createdArticles;
	}

	public void setCreatedArticles(Set<CmArticleTbl> createdArticles) {
		this.createdArticles = createdArticles;
	}

	public Set<SmRoleTbl> getCreatedRoles() {
		return createdRoles;
	}

	public void setCreatedRoles(Set<SmRoleTbl> createdRoles) {
		this.createdRoles = createdRoles;
	}

	public void setRoles(Set<SmRoleTbl> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsLocked() {
		return this.isLocked;
	}

	public String getLast_ip_address() {
		return last_ip_address;
	}

	public Set<SmAccessoryTbl> getCreatedAccessoris() {
		return createdAccessoris;
	}

	public void setCreatedAccessoris(Set<SmAccessoryTbl> createdAccessoris) {
		this.createdAccessoris = createdAccessoris;
	}

	public Set<CmColumnTbl> getCreatedColumns() {
		return createdColumns;
	}

	public void setCreatedColumns(Set<CmColumnTbl> createdColumns) {
		this.createdColumns = createdColumns;
	}

	public void setLast_ip_address(String last_ip_address) {
		this.last_ip_address = last_ip_address;
	}

	public void setIsLocked(int isLocked) {
		this.isLocked = isLocked;
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public SmUserTbl getParent() {
		return parent;
	}

	public void setParent(SmUserTbl parent) {
		this.parent = parent;
	}

	public Set<SmUserTbl> getChildren() {
		return children;
	}

	public void setChildren(Set<SmUserTbl> children) {
		this.children = children;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public SmAccessoryTbl getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(SmAccessoryTbl thumbnail) {
		this.thumbnail = thumbnail;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}


	

	@Override
	public String toString() {
		return "SmUserTbl [id=" + id + ", email=" + email + ", isLocked=" + isLocked + ", lastLogin=" + lastLogin
				+ ", password=" + password + ", last_ip_address=" + last_ip_address + ", phone=" + phone + ", devId="
				+ devId + ", thumbnail=" + thumbnail + ", isDelete=" + isDelete + ", realName=" + realName + ", token="
				+ token + ", remark=" + remark + ", webSet=" + webSet + ", parent=" + parent
				+ ", children=" + children + ", username=" + username + ", roles=" + roles + ", createdRoles="
				+ createdRoles + ", createdColumns=" + createdColumns + ", createdAccessoris=" + createdAccessoris
				+ ", createdArticles=" + createdArticles + ", createdWebSets=" + createdWebSets + ", createdHomeSlides="
				+ createdHomeSlides + ", createdComment=" + createdComment + "]";
	}

	public SmUserTbl(String email, String phone, String realName) {
		this.email = email;
		this.phone = phone;
		this.realName = realName;
	}

	public Set<CmSmallContentTbl> getSmallContents() {
		return smallContents;
	}

	public void setSmallContents(Set<CmSmallContentTbl> smallContents) {
		this.smallContents = smallContents;
	}
	public Timestamp getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}


}