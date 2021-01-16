package com.tjsj.wp.orm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the if_wechat_user_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="if_wechat_user_tbl")
@NamedQuery(name="IfWechatUserTbl.findAll", query="SELECT i FROM IfWechatUserTbl i")
public class IfWechatUserTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final Find<Integer,IfWechatUserTbl> find = new Find<Integer,IfWechatUserTbl>(){};

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;

	private String city;

	private String country;

	private String headimgurl;

	private String nickname;

	private String openid;

	private String province;

	private String remark;

	private int sex;

	private int subscribe;

	@Column(name="subscribe_time")
	private String subscribe_time;

	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;

	public IfWechatUserTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return this.headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSex() {
		return this.sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getSubscribe() {
		return this.subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public String getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(String subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

}