package com.tjsj.wp.orm.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * The persistent class for the cm_survey_info_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_survey_info_tbl")
@NamedQuery(name="CmSurveyInfoTbl.findAll", query="SELECT c FROM CmSurveyInfoTbl c")
public class CmSurveyInfoTbl extends com.tjsj.m_util.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,CmSurveyInfoTbl> find = new Find<Integer,CmSurveyInfoTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String address;

	@Column(name="insert_time",insertable=false)
	private Timestamp insertTime;

	@Column(name="survey_user")
	private String surveyUser;

	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;

	//bi-directional many-to-one association to CmSurveyAnswerTbl
	@OneToMany(mappedBy="surveyInfo")
	private Set<CmSurveyAnswerTbl> surveyAnswers=Sets.newHashSet();

	//bi-directional many-to-one association to CmSurveyQuestionTbl
	@OneToMany(mappedBy="surveyInfo")
	private Set<CmSurveyQuestionTbl> surveyQuestions=Sets.newHashSet();

	public CmSurveyInfoTbl() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public String getSurveyUser() {
		return this.surveyUser;
	}

	public void setSurveyUser(String surveyUser) {
		this.surveyUser = surveyUser;
	}

	public SmWebSetTbl getWebSet() {
		return webSet;
	}

	public void setWebSet(SmWebSetTbl webSet) {
		this.webSet = webSet;
	}

	public Set<CmSurveyAnswerTbl> getSurveyAnswers() {
		return surveyAnswers;
	}

	public void setSurveyAnswers(Set<CmSurveyAnswerTbl> surveyAnswers) {
		this.surveyAnswers = surveyAnswers;
	}

	public Set<CmSurveyQuestionTbl> getSurveyQuestions() {
		return surveyQuestions;
	}

	public void setSurveyQuestions(Set<CmSurveyQuestionTbl> surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}

	

}