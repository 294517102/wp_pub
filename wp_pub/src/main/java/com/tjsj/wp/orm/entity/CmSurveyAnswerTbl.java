package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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

import com.avaje.ebean.annotation.Cache;


/**
 * The persistent class for the cm_survey_answer_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_survey_answer_tbl")
@NamedQuery(name="CmSurveyAnswerTbl.findAll", query="SELECT c FROM CmSurveyAnswerTbl c")
public class CmSurveyAnswerTbl extends com.tjsj.m_util.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,CmSurveyAnswerTbl> find = new Find<Integer,CmSurveyAnswerTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="insert_time",insertable=false)
	private Timestamp insertTime;

	@Column(name="is_check")
	private int isCheck;

	@Column(name="is_delete",insertable=false)
	private int isDelete;

	private String remark;
	
	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;

	//bi-directional many-to-one association to CmSurveyInfoTbl
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="survey_info_id")
	private CmSurveyInfoTbl surveyInfo;

	//bi-directional many-to-one association to CmSurveyOptionsTbl
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="survey_options_id")
	private CmSurveyOptionsTbl surveyOptions;

	public CmSurveyAnswerTbl() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public CmSurveyOptionsTbl getSurveyOptions() {
		return surveyOptions;
	}

	public void setSurveyOptions(CmSurveyOptionsTbl surveyOptions) {
		this.surveyOptions = surveyOptions;
	}

	

	
}