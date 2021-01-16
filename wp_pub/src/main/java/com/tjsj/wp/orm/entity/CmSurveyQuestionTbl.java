package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.Table;

import com.avaje.ebean.annotation.Cache;
import com.google.common.collect.Sets;


/**
 * The persistent class for the cm_survey_question_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_survey_question_tbl")
@NamedQuery(name="CmSurveyQuestionTbl.findAll", query="SELECT c FROM CmSurveyQuestionTbl c")
public class CmSurveyQuestionTbl extends com.tjsj.m_util.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,CmSurveyQuestionTbl> find = new Find<Integer,CmSurveyQuestionTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String content;

	@Column(name="insert_time",insertable=false)
	private Timestamp insertTime;

	@Column(name="is_delete",insertable=false)
	private int isDelete;

	private int type;

	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;

	//bi-directional many-to-one association to CmSurveyOptionsTbl
	@OneToMany(mappedBy="surveyQuestion")
	private Set<CmSurveyOptionsTbl> surveyOptions=Sets.newHashSet();

	//bi-directional many-to-one association to CmSurveyInfoTbl
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="surver_info_id")
	private CmSurveyInfoTbl surveyInfo;

	public CmSurveyQuestionTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}


	public CmSurveyInfoTbl getSurveyInfo() {
		return surveyInfo;
	}

	public void setSurveyInfo(CmSurveyInfoTbl surveyInfo) {
		this.surveyInfo = surveyInfo;
	}

	public Set<CmSurveyOptionsTbl> getSurveyOptions() {
		return surveyOptions;
	}

	public void setSurveyOptions(Set<CmSurveyOptionsTbl> surveyOptions) {
		this.surveyOptions = surveyOptions;
	}


}