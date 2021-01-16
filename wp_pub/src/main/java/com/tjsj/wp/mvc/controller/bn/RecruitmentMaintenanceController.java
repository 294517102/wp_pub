package com.tjsj.wp.mvc.controller.bn;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.entity.BaseEntity;
import com.tjsj.m_util.exception.ParameterException;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.BnOrderTbl;
import com.tjsj.wp.orm.entity.CmCommentTbl;
import com.tjsj.wp.orm.entity.CmRecruitmentTbl;
import com.tjsj.wp.orm.entity.CmSysLogTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;

@Controller
public class RecruitmentMaintenanceController extends WpBaseController{
	@Autowired
	private EbeanServer ebeanServer;

	
	
	/**
	 * 查询当前网站的招聘信息
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("obtain_recritment_list")
	public ModelAndView obtain_recritment_list(HttpServletRequest request,HttpServletResponse response,Integer pageIndex,String url,PageParameter page){
		ModelAndView mv = createBgView(url, request, response);
	try{
		/*根据当前登录用户获取吗留言信息*/
		if(pageIndex==null) pageIndex=0;
		PagedList<CmRecruitmentTbl> recritment = CmRecruitmentTbl.find.where().eq("webSet", getWebSet(request)).eq("isDelete", -1).orderBy("insertTime desc").
				setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
		pagePrint(request, mv, recritment);
	}catch(Exception e){
		e.printStackTrace();
	}
		return mv;
	}
	
	/**
	 * 查询订单列表
	 * @param request
	 * @param response
	 * @param order
	 * @param page
	 * @param url
	 * @return
	 */
	@RequestMapping("obtain_recruit_list_like")
	public ModelAndView obtain_order_list(HttpServletRequest request, 
										  HttpServletResponse response,
										  CmRecruitmentTbl recruitment,
										  PageParameter page,
										  String url){
		ModelAndView mv = null;
		if(StringUtils.isNotBlank(url)){
			mv = createBgView(url, request, response);
		}else{
			mv = createBgView("business/recruitment/recruitment_list", request, response);
		}
		try {
			PagedList<BaseEntity> pageList = recruitment.getLikeOrExpressionList().eq("webSet", getWebSet(request)).eq("isDelete", -1).setFirstRow(page.getFirstRow())
											.setMaxRows(page.getMaxRows()).orderBy("id desc").findPagedList();
			pagePrint(request, mv, pageList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * 对订单的修改和删除
	 * @param request
	 * @param response
	 * @param order 订单对象
	 */
	@RequestMapping("updata_state_recruitment")
	public void updata_state_order(HttpServletRequest request,HttpServletResponse response,CmRecruitmentTbl  recruitment){
		ResultJson rj=new ResultJson();
		try{
			recruitment.update();
			rj.setResult(true);
			rj.setResultMsg("操作成功");
			outJson(response, rj);
			return ;
		}catch(Exception e){
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg("处理异常");
			outJson(response, rj);
			return ;
		}
	}
	
	
	/**
	 * 查询当前网站的留言板内容.模糊查询
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/recruitment_search")
	public ModelAndView commentSearch(HttpServletRequest request,HttpServletResponse response,Integer pageIndex,String keyword,String StartTime,String EndTime,String state,PageParameter page){
		ModelAndView mv = createBgView("cms/comment_list", request, response);
		ExpressionList<CmRecruitmentTbl> v = CmRecruitmentTbl.find.where().eq("webSet", getWebSet(request));
		if(StringUtils.isNoneBlank(keyword)){
			v.like("name", "%"+keyword+"%");
		}
		if(StringUtils.isNoneBlank(StartTime)&&StringUtils.isNoneBlank(EndTime)){
			v.between("insert_time", StartTime, EndTime);
		}
		if(StringUtils.isNotBlank(state)){
			v.eq("state", state);
		}
		if(pageIndex==null) pageIndex=0;
		PagedList<CmRecruitmentTbl> recruit = v.orderBy("insertTime desc").setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
		pagePrint(request, mv, recruit);
		return mv;
	}
	/**
	 * 批量操作留言状态
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/recruitment_operation_all")
	public ModelAndView commentOperation(HttpServletRequest request,HttpServletResponse response,String spCodesTemp,int judge){
		ModelAndView mv = createView("", request, response);
		try{
			if(StringUtils.isBlank(spCodesTemp)){
				addResult(mv, false, "请选择数据!");
				return mv;
			}
			String[] spCodesTemps = spCodesTemp.split(",");
			ebeanServer.beginTransaction();
			for (String string : spCodesTemps) {
				CmRecruitmentTbl recruitment = CmRecruitmentTbl.find.byId(Integer.parseInt(string));
				if(judge==1){
					recruitment.setIsDelete(-1);
					//记录日志
					CmSysLogTbl log=new CmSysLogTbl();
					log.setoPerator(getLoginUser());
					log.setType(1);
					log.setTableName("cm_recruitment_tbl");
					log.setRemark("删除招聘信息"+recruitment.getId());
					log.save();
					addResult(mv, true, "删除成功!");
					
				}else{
					recruitment.setState(-1);
					DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time=format.format(new Date());
					Date date=format.parse(time);
					recruitment.setSolveTime(date);
					recruitment.update();
					//记录日志
					CmSysLogTbl log=new CmSysLogTbl();
					log.setoPerator(getLoginUser());
					log.setType(1);
					log.setTableName("cm_recruitment_tbl");
					log.setRemark("更新招聘信息"+recruitment.getId());
					log.save();
					addResult(mv, true, "操作成功!");
				}
			}
			ebeanServer.commitTransaction();
		}catch(Exception e){
			addResult(mv, false, "操作失败!");
		}
		return mv;
	}
	/**
	 * 根据id改变留言状态
	 * @param request
	 * @param response
	 * @param url
	 * @param id
	 * @return
	 */
	@RequestMapping("admin/recruitment_operation_id")
	public ModelAndView operationById(HttpServletRequest request,HttpServletResponse response,String id,int judge){
		ModelAndView mv = createView("", request, response);
		try{
			CmRecruitmentTbl recruitment = CmRecruitmentTbl.find.byId(Integer.parseInt(id));
			if(recruitment!=null){
				if(judge==1){
					recruitment.setIsDelete(-1);
					//记录日志
					CmSysLogTbl log=new CmSysLogTbl();
					log.setoPerator(getLoginUser());
					log.setType(1);
					log.setTableName("cm_recruitment_tbl");
					log.setRemark("删除招聘信息"+recruitment.getId());
					log.save();
					addResult(mv, true, "删除成功!");
					return mv;
				}else{
					if(recruitment.getState()==1)recruitment.setState(-1);
					else recruitment.setState(1);
					DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time=format.format(new Date());
					Date date=format.parse(time);
					recruitment.setSolveTime(date);
					recruitment.update();
					//记录日志
					CmSysLogTbl log=new CmSysLogTbl();
					log.setoPerator(getLoginUser());
					log.setType(1);
					log.setTableName("cm_recruitment_tbl");
					log.setRemark("更新招聘信息"+recruitment.getId());
					log.save();
					addResult(mv, true, "操作成功!");
					return mv;
				}
			}
		}catch(Exception e){
			addResult(mv, false, "操作失败!");
		}
		return mv;
	}
	
	@RequestMapping("save/recruitment")
	public void  saver(HttpServletResponse response,HttpServletRequest request,CmRecruitmentTbl cm){
		ResultJson rj = new ResultJson();
		try {
			if(StringUtil.isBlank(cm.getName())){
				throw new ParameterException("姓名不能为空");
			}
			if(StringUtil.isBlank(cm.getPhone())){
				throw new ParameterException("联系电话不能为空");
			}
			if(cm.getSex()==0){
				throw new ParameterException("性别不能为空");
			}
			if(StringUtil.isBlank(cm.getNation())){
				throw new ParameterException("民族不能为空");
			}
			if(StringUtil.isBlank(cm.getBirthday())){
				throw new ParameterException("生日不能为空");
			}
			if(cm.getPoliticalStatus()==0){
				throw new ParameterException("政治面貌不能为空");
			}
			if(StringUtil.isBlank(cm.getLearnExpr())){
				throw new ParameterException("学习经历不能为空");
			}
			if(StringUtil.isBlank(cm.getWorkExpr())){
				throw new ParameterException("工作经历不能为空");
			}
			cm.setIsDelete(-1);
			cm.setWebSet(getWebSet(request));
			cm.setState(1);
			cm.setIsDelete(-1);
			cm.save();
			rj.setResultMsg("收到你的信息了，请你耐心等待，我们将尽快与你联系");
			rj.setResult(true);
		} catch (Exception e) {
			rj.setResultMsg(e.getMessage());
			rj.setResult(false);
		}
		outJson(response, rj);
		return ;
	}
	@RequestMapping("admin/recruitment/show_details")
	public ModelAndView details(HttpServletRequest request,HttpServletResponse response,Integer id,String url){
		ModelAndView mv = createBgView(url, request, response);
		try {
			CmRecruitmentTbl cmr = CmRecruitmentTbl.find.byId(id);
			mv.addObject("cmr", cmr);
			cmr.setState(2);
			cmr.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
}
