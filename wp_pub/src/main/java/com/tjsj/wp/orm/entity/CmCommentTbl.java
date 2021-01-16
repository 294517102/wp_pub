package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the cm_comment_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_comment_tbl")
@NamedQuery(name="CmCommentTbl.findAll", query="SELECT c FROM CmCommentTbl c")
public class CmCommentTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,CmCommentTbl> find = new Find<Integer,CmCommentTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	/*@Column(name="comment_time")
	private Timestamp commentTime;*/
    
	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="comment_time")
	@OrderBy("commentTime desc")
	private Date commentTime;
	
	@ManyToOne
	@JoinColumn(name="creator")
	private SmUserTbl creator;
	@Column(name="content")
	private String content;

    
	@Column(name="name")
	private String name;
	
	@Column(name="phone")
	private String phone;
    
	@Column(name="title")
	private String title;
	
	@Column(name="email")
	private String email;
	
	@Column(name="company")
	private String company;
	
	@Column(name="address")
	private String address;
	
	@Column(name="audit",insertable=false)
	private int audit;
	
	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;
	
	@Column(name="customer_info")
	private String customerInfo;
	
	@Column(name="is_delete",insertable=false)
	private int isDelete;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="solve_time")
	private Date solveTime;
	
	private String sex;
	
	private Integer type;//1、在线留言；2、在线报名
	
	@OneToOne
	@JoinColumn(name="comment_ext_id")
	private CmCommentExtTbl commentExt;
	
	public Date getSolveTime() {
		return solveTime;
	}

	public void setSolveTime(Date solveTime) {
		this.solveTime = solveTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Column(name="state",insertable=false)
	private int state;
	
	public String getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(String customerInfo) {
		this.customerInfo = customerInfo;
	}

	public CmCommentTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getAudit() {
		return audit;
	}

	public void setAudit(int audit) {
		this.audit = audit;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public CmCommentExtTbl getCommentExt() {
		return commentExt;
	}

	public void setCommentExt(CmCommentExtTbl commentExt) {
		this.commentExt = commentExt;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


}