package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Entity
@Cache(enableQueryCache=true)
@Table(name="bn_order_tbl")
@NamedQuery(name="BnOrderTbl.findAll", query="SELECT c FROM BnOrderTbl c")
public class BnOrderTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,BnOrderTbl> find = new Find<Integer,BnOrderTbl>(){};
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String number;//订单编号
	
	private String name;//订单名称
	
	private String phone;//联系电话
	
	private BigDecimal price;//订单总额
	
	@Column(insertable=false)
	private int state;//订单状态；1-待处理；2-已处理
	
	@Column(name="apply_way")
	private int applyWay;//支付方式：1  支付宝   2  微信   3  银行卡   4  到店
	
	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="insert_time",insertable=false,updatable=false)
	private Date insertTime;//插入时间	
	
	@Column(name="apply_state")
	private int applyState;//支付状态；1-未支付；5-已支付；11-已退款
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private SmUserTbl user;
	
	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;//所属站点
	
	@Column(name="is_delete")
	private int isDelete;//文章是否删除，1，表示未删除，-1已删除

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
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

	public int getApplyWay() {
		return applyWay;
	}

	public void setApplyWay(int applyWay) {
		this.applyWay = applyWay;
	}

	public int getApplyState() {
		return applyState;
	}

	public void setApplyState(int applyState) {
		this.applyState = applyState;
	}

	public SmUserTbl getUser() {
		return user;
	}

	public void setUser(SmUserTbl user) {
		this.user = user;
	}
	
}
