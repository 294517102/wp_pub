package com.tjsj.wp.orm.entity;

import java.io.Serializable;
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
 * The persistent class for the cm_survey_options_tbl database table.
 * 
 */
@Entity
@Cache(enableQueryCache=true)
@Table(name="cm_survey_options_tbl")
@NamedQuery(name="CmSurveyOptionsTbl.findAll", query="SELECT c FROM CmSurveyOptionsTbl c")
public class CmSurveyOptionsTbl extends com.tjsj.m_util.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Find<Integer,CmSurveyOptionsTbl> find = new Find<Integer,CmSurveyOptionsTbl>(){};
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String content;

	@Column(name="is_delete",insertable=false)
	private int isDelete;

	private String remark;
	
	@ManyToOne
	@JoinColumn(name="web_set_id")
	private SmWebSetTbl webSet;

	//bi-directional many-to-one association to CmSurveyAnswerTbl
	@OneToMany(mappedBy="surveyOptions")
	private Set<CmSurveyAnswerTbl> surveyAnswers=Sets.newHashSet();

	//bi-directional many-to-one association to CmSurveyQuestionTbl
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="survey_question_id")
	private CmSurveyQuestionTbl surveyQuestion;

	public CmSurveyOptionsTbl() {
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

	public int getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getRemark() {
		return this.remark;
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

	public Set<CmSurveyAnswerTbl> getSurveyAnswers() {
		return surveyAnswers;
	}

	public void setSurveyAnswers(Set<CmSurveyAnswerTbl> surveyAnswers) {
		this.surveyAnswers = surveyAnswers;
	}

	public CmSurveyQuestionTbl getSurveyQuestion() {
		return surveyQuestion;
	}

	public void setSurveyQuestion(CmSurveyQuestionTbl surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}

	

}