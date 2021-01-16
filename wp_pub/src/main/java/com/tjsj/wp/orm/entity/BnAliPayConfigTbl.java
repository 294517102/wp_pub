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

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;



/**
 * The persistent class for the bn_ali_pay_config_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="bn_ali_pay_config_tbl")
@NamedQuery(name="BnAliPayConfigTbl.findAll", query="SELECT b FROM BnAliPayConfigTbl b")
public class BnAliPayConfigTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Find<Integer,BnAliPayConfigTbl> find = new Find<Integer,BnAliPayConfigTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;

	@Column(name="app_id")
	private String appId;

	@Column(name="dead_time")
	private Date deadTime;

	@Column(name="input_charset")
	private String inputCharset;

	@Column(name="is_valid")
	private int isValid;

	@Column(name="notify_url")
	private String notifyUrl;

	private String partner;

	@Column(name="payee_type_logoin_id")
	private String payeeTypeLogoinId;

	@Column(name="payment_type")
	private int paymentType;

	@Column(name="public_key")
	private String publicKey;

	@Column(name="public_key_md5")
	private String publicKeyMd5;

	@Column(name="rsa_private")
	private String rsaPrivate;

	@Column(name="rsa2_private")
	private String rsa2Private;

	private String service;

	@Column(name="sign_type")
	private String signType;

	@Column(name="to_refund_url")
	private String toRefundUrl;

	@Column(name="withdrawals_url")
	private String withdrawalsUrl;

	@OneToOne(mappedBy="aliConfig")
	private SmWebSetTbl webSet;
	
	public BnAliPayConfigTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Date getDeadTime() {
		return deadTime;
	}

	public void setDeadTime(Date deadTime) {
		this.deadTime = deadTime;
	}

	public String getInputCharset() {
		return this.inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public int getIsValid() {
		return this.isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public String getNotifyUrl() {
		return this.notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getPartner() {
		return this.partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPayeeTypeLogoinId() {
		return this.payeeTypeLogoinId;
	}

	public void setPayeeTypeLogoinId(String payeeTypeLogoinId) {
		this.payeeTypeLogoinId = payeeTypeLogoinId;
	}

	public int getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public String getPublicKey() {
		return this.publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPublicKeyMd5() {
		return this.publicKeyMd5;
	}

	public void setPublicKeyMd5(String publicKeyMd5) {
		this.publicKeyMd5 = publicKeyMd5;
	}

	public String getRsaPrivate() {
		return this.rsaPrivate;
	}

	public void setRsaPrivate(String rsaPrivate) {
		this.rsaPrivate = rsaPrivate;
	}

	public String getRsa2Private() {
		return this.rsa2Private;
	}

	public void setRsa2Private(String rsa2Private) {
		this.rsa2Private = rsa2Private;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSignType() {
		return this.signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getToRefundUrl() {
		return this.toRefundUrl;
	}

	public void setToRefundUrl(String toRefundUrl) {
		this.toRefundUrl = toRefundUrl;
	}
	
	public String getWithdrawalsUrl() {
		return this.withdrawalsUrl;
	}

	public void setWithdrawalsUrl(String withdrawalsUrl) {
		this.withdrawalsUrl = withdrawalsUrl;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}


}