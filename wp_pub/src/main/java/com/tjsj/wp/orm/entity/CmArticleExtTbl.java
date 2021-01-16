package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.tjsj.m_util.entity.BaseEntity;


/**
 * The persistent class for the cm_article_ext_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_article_ext_tbl")
@NamedQuery(name="CmArticleExtTbl.findAll", query="SELECT c FROM CmArticleExtTbl c")
public class CmArticleExtTbl extends  BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,CmArticleExtTbl> find = new Find<Integer,CmArticleExtTbl>(){};

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(fetch=FetchType.LAZY,cascade={CascadeType.ALL})
	@JoinColumn(name="article_id")
	private CmArticleTbl article;

	
	private String content;

	@Column(name="insert_time")
	private Timestamp insertTime;
	
	//get/set方法
	public CmArticleExtTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public CmArticleTbl getArticle() {
		return article;
	}

	public void setArticle(CmArticleTbl article) {
		this.article = article;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}


}