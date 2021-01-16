package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the bn_wechat_config_tbl database table.
 * 
 */  
@Entity
@Cache(enableQueryCache=true)
@Table(name="bn_wechat_config_tbl")
@NamedQuery(name="BnWechatConfigTbl.findAll", query="SELECT b FROM BnWechatConfigTbl b")
public class BnWechatConfigTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,BnWechatConfigTbl> find = new Find<Integer,BnWechatConfigTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;

	private String appid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dead_time")
	private Date deadTime;

	@Column(name="is_valid")
	private int isValid;

	@Column(name="key_str")
	private String keyStr;

	@Column(name="mch_id")
	private String mchId;

	@Column(name="order_request_url")
	private String orderRequestUrl;

	@Column(name="refund_url")
	private String refundUrl;

	@OneToOne(mappedBy="weChatConfig")
	private SmWebSetTbl webSet;
	
	public BnWechatConfigTbl() {
	}
	
	public SmWebSetTbl getWebSet() {
		return webSet;
	}


	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppid() {
		return this.appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public Date getDeadTime() {
		return this.deadTime;
	}

	public void setDeadTime(Date deadTime) {
		this.deadTime = deadTime;
	}

	public int getIsValid() {
		return this.isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public String getKeyStr() {
		return this.keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	public String getMchId() {
		return this.mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getOrderRequestUrl() {
		return this.orderRequestUrl;
	}

	public void setOrderRequestUrl(String orderRequestUrl) {
		this.orderRequestUrl = orderRequestUrl;
	}

	public String getRefundUrl() {
		return this.refundUrl;
	}

	public void setRefundUrl(String refundUrl) {
		this.refundUrl = refundUrl;
	}

}