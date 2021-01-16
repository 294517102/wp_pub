package com.tjsj.wp.mvc.controller.bn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.avaje.ebean.PagedList;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.CmSurveyAnswerTbl;
import com.tjsj.wp.orm.entity.CmSurveyOptionsTbl;
import com.tjsj.wp.orm.entity.CmSurveyQuestionTbl;

@Controller
public class SurveyMaintenanceController extends WpBaseController{
	@RequestMapping("add_survey_question")
	public void add_survey_question(HttpServletRequest request,HttpServletResponse response,String readios,CmSurveyQuestionTbl question,String remark){
		ResultJson rj=new ResultJson();
		System.err.println("既然怒:"+readios);
		try{
			if(question==null){
				rj.setResult(false);
				rj.setResultMsg("参数异常");
				outJson(response, rj);
				return ;
			}
			question.setWebSet(getWebSet(request));
			if(question.getId()==0){//新增问题
				question.save();
				if(StringUtils.isNotBlank(readios)){
					String[] optins=StringUtils.split(readios,",");
					for (String opt : optins) {
						CmSurveyOptionsTbl soption=new CmSurveyOptionsTbl();
						soption.setContent(opt);
						soption.setSurveyQuestion(question);
						soption.setRemark(remark);
						soption.setWebSet(getWebSet(request));
						soption.save();
					}
				}
			}else{//修改问题
				question.update();
				List<CmSurveyOptionsTbl> sopt=CmSurveyOptionsTbl.find.where().eq("surveyQuestion", question).findList();
				for (CmSurveyOptionsTbl s : sopt) {
					s.delete();
				}
				if(StringUtils.isNotBlank(readios)){
					String[] optins=StringUtils.split(readios,",");
					for (String opt : optins) {
						CmSurveyOptionsTbl soption=new CmSurveyOptionsTbl();
						soption.setContent(opt);
						soption.setSurveyQuestion(question);
						soption.setRemark(remark);
						soption.setWebSet(getWebSet(request));
						soption.save();
					}
				}
			}
			
			rj.setResult(true);
			rj.setResultMsg("添加成功");
			outJson(response, rj);
			return ;
		}catch(Exception e){
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg("添加异常");
			outJson(response, rj);
			return ;
		}
	}
	@RequestMapping("obtain_question_by_id")
	public ModelAndView obtain_question_by_id(HttpServletRequest request,HttpServletResponse response,String url,Integer id){
		ModelAndView mv=createBgView(url, request, response);
			if(id==null||id==0){
				addResult(mv, false, "参数错误");
			}
			CmSurveyQuestionTbl sq=CmSurveyQuestionTbl.find.byId(id);
			mv.addObject("data", sq);
		return mv;
	}
	@RequestMapping("obtain_question_by_keyword")
	public ModelAndView obtain_question_by_keyword(HttpServletRequest request,HttpServletResponse response,String url,String keyword,PageParameter page){
		ModelAndView mv=createBgView(url, request, response);
			if(StringUtils.isBlank(keyword)){
				addResult(mv, false, "参数错误");
			}
			PagedList<CmSurveyQuestionTbl> sqs = CmSurveyQuestionTbl.find.where().like("content", "%"+keyword+"%").eq("webSet", getWebSet(request)).eq("isDelete", -1).orderBy("insertTime desc").
					setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request, mv, sqs);
			mv.addObject("keyword", keyword);
		return mv;
	}
	
	@RequestMapping("obtain_question_list")
	public ModelAndView obtain_question_list(HttpServletRequest request,HttpServletResponse response,String url,PageParameter page){
		ModelAndView mv=createBgView(url, request, response);
			try{
				PagedList<CmSurveyQuestionTbl> sqs = CmSurveyQuestionTbl.find.where().eq("webSet", getWebSet(request)).eq("isDelete", -1).orderBy("insertTime desc").
						setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
				pagePrint(request, mv, sqs);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		return mv;
	}
	
	@RequestMapping("obtain_answer_by_keyword")
	public ModelAndView obtain_answer_by_keyword(HttpServletRequest request,HttpServletResponse response,String url,String keyword,PageParameter page){
		ModelAndView mv=createBgView(url, request, response);
			if(StringUtils.isBlank(keyword)){
				addResult(mv, false, "参数错误");
			}
			PagedList<CmSurveyAnswerTbl> sqs = CmSurveyAnswerTbl.find.where().like("surveyOptions.surveyQuestion.content", "%"+keyword+"%").eq("webSet", getWebSet(request)).eq("isDelete", -1).orderBy("insertTime desc").
					setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request, mv,"answer" ,sqs);
			mv.addObject("keyword", keyword);
		return mv;
	}
	
	@RequestMapping("delete_survey_question")
	public void delete_survey_question(HttpServletRequest request,HttpServletResponse response,Integer id){
		ResultJson rj=new ResultJson();
		try{
			if(id==null||id==0){
				rj.setResult(false);
				rj.setResultMsg("参数错误");
				outJson(response, rj);
				return ;
			}
			CmSurveyQuestionTbl sq=CmSurveyQuestionTbl.find.byId(id);
			sq.setIsDelete(1);
			sq.update();
			rj.setResult(true);
			rj.setResultMsg("删除成功");
			outJson(response, rj);
			return ;
			
		}catch(Exception e){
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg("删除异常");
			outJson(response, rj);
			return ;
		}
	}
	
	@RequestMapping("obtain_question_list_view")
	public ModelAndView obtain_question_list_view(HttpServletRequest request,HttpServletResponse response,String url){
		ModelAndView mv=createView(url, request, response);
			try{
				List<CmSurveyQuestionTbl> sqlist=CmSurveyQuestionTbl.find.where().eq("isDelete", -1).eq("webSet", getWebSet(request)).findList();
				mv.addObject("resultList", sqlist);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		return mv;
	}
	
	@RequestMapping("save_answer_view")
	public void save_answer_view(HttpServletRequest request,HttpServletResponse response,String aids){
		ResultJson rj=new ResultJson();
		try{
			if(StringUtils.isBlank(aids)){
				rj.setResult(false);
				rj.setResultMsg("参数错误");
				outJson(response, rj);
				return ;
			}
			String [] aid=aids.split(",");
			if(aid.length<=0){
				rj.setResult(false);
				rj.setResultMsg("数据错误");
				outJson(response, rj);
				return ;
			}
			for (String id : aid) {
				CmSurveyAnswerTbl sa=new CmSurveyAnswerTbl();
				sa.setSurveyOptions(CmSurveyOptionsTbl.find.byId(Integer.valueOf(id)));
				sa.setIsCheck(1);
				sa.setWebSet(getWebSet(request));
				sa.save();
			}
			rj.setResult(true);
			rj.setResultMsg("提交成功,感谢您对我们工作的支持");
			outJson(response, rj);
			return ;
		}catch(Exception e){
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg("提交异常");
			outJson(response, rj);
			return ;
		}
	}
}
