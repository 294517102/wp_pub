package com.tjsj.wp.mvc.controller.bn;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.entity.BaseEntity;
import com.tjsj.m_util.exception.ParameterException;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.BnSignUpTbl;
import com.tjsj.wp.orm.entity.CmCommentExtTbl;
import com.tjsj.wp.orm.entity.CmCommentTbl;
import com.tjsj.wp.orm.entity.CmSysLogTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;

@Controller
public class CommentMaintenanceController extends WpBaseController{
	@Autowired
	private EbeanServer ebeanServer;
	/**
	 * 保存页面留言板的内容
	 * @param response 响应
	 * @param title 标题
	 * @param content 内容
	 * @param name 留言人姓名
	 * @param phone 留言人电话
	 */
	@RequestMapping("comment_save")
	public void saveComment(HttpServletRequest request, HttpServletResponse response,
			String customerInfo,String company,String address,String title,String sex,
			String content,String email,String name,String phone,Integer webSet,CmCommentExtTbl comm_ext,Integer type,Integer creator_id){
		CmCommentTbl comment=new CmCommentTbl();
		ResultJson rj=new ResultJson();
		if(StringUtils.isBlank(phone)){
			rj.setResult(false);
			rj.setResultMsg("请填写联系方式.");
			outJson(response, rj);
			return ;	
		}
		try{
		if(title!=null){
			comment.setTitle(title);
		}
		if(StringUtils.isNotBlank(sex)){
			comment.setSex(sex);
		}
		if(content!=null){
			comment.setContent(content);
		}
		if(name!=null){
			comment.setName(name);
		}
		if(type!=null){
			comment.setType(type);
		}
		if(customerInfo!=null){
			comment.setCustomerInfo(customerInfo);
		}
		if(company!=null){
			comment.setCompany(company);
		}
		if(phone!=null){
			comment.setPhone(phone);
		}
		if(email!=null){
			comment.setEmail(email);
		}
		if(StringUtils.isNotBlank(address)){
			comment.setAddress(address);
		}
		
		if(comm_ext!=null){
			comm_ext.save();
			comment.setCommentExt(comm_ext);
		}
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(new Date());
		Date date=format.parse(time);
		comment.setCommentTime(date);
		if(webSet==null||webSet==0){
			
			comment.setWebSet(this.getWebSet(request));
		}else{
			comment.setWebSet(SmWebSetTbl.find.byId(webSet));
		}
		if(creator_id!=null && creator_id!=0){
			SmUserTbl luser=SmUserTbl.find.byId(creator_id);
			if(luser!=null){
				comment.setCreator(luser);
			}
		}
		comment.save();
		//记录日志
				CmSysLogTbl log=new CmSysLogTbl();
				log.setoPerator(this.getLoginUser());
				log.setType(2);
				log.setTableName("cm_comment_tbl");
				log.setRemark("新增留言"+comment.getId());
				if(webSet==null||webSet==0){
					
					log.setWebset(getWebSet(request));
				}else{
					log.setWebset(SmWebSetTbl.find.byId(webSet));
				}
				log.save();
		}catch(Exception e){
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg("糟糕，消息提交失败了！");
			outJson(response, rj);
			return ;
		}
		rj.setResult(true);
		rj.setResultMsg("你的消息我们已收到，随后将联系您，谢谢！");
		outJson(response, rj);
		return ;
	}
	
	/**
	 * 查询当前网站的留言板内容
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("obtain_comment_list")
	public ModelAndView obtain_comment_list(HttpServletRequest request,HttpServletResponse response,Integer pageIndex,String url,PageParameter page,Integer type,Integer creator_id){
		ModelAndView mv = createBgView(url, request, response);
	try{
		/*根据当前登录用户获取吗留言信息*/
		ExpressionList<CmCommentTbl> exp=CmCommentTbl.find.where().eq("webSet", getWebSet(request)).eq("isDelete", -1);
		if(type!=null && type!=0){
			exp.eq("type", type);
		}
		if(creator_id!=null && creator_id!=0){
			SmUserTbl  cuser=SmUserTbl.find.byId(creator_id);
			if(cuser!=null){
				exp.eq("creator", cuser);
			}
		}
		PagedList<CmCommentTbl> comment = exp.orderBy("comment_time desc").setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
		pagePrint(request, mv, comment);
		
	}catch(Exception e){
		e.printStackTrace();
	}
		return mv;
	}
	/**
	 * 查询留言列表
	 * @param request
	 * @param response
	 * @param order
	 * @param page
	 * @param url
	 * @return
	 */
	@RequestMapping("obtain_comment_list_like")
	public ModelAndView obtain_order_list(HttpServletRequest request, 
										  HttpServletResponse response,
										  CmCommentTbl comment,
										  PageParameter page,
										  String url){
		ModelAndView mv = null;
		if(StringUtils.isNotBlank(url)){
			mv = createBgView(url, request, response);
		}else{
			mv = createBgView("business/comment/comment_list", request, response);
		}
		try {
			PagedList<BaseEntity> pageList = comment.getLikeOrExpressionList().eq("webSet", getWebSet(request)).eq("isDelete", -1).setFirstRow(page.getFirstRow())
											.setMaxRows(page.getMaxRows()).orderBy("comment_time desc").findPagedList();
			pagePrint(request, mv, pageList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	
	@RequestMapping("/admin/yuyue/comment_all_excel_export")
	public void orderExcelAllExport(HttpServletRequest request, HttpServletResponse response,String article_id,String title) {
		ResultJson rj = new ResultJson();
		try {
			HSSFWorkbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet();
			Row rowIndex = sheet.createRow(0);
			if(StringUtils.isBlank(title)) {
				title="导出列表";
			}
			// 查询订单信息
			ExpressionList<CmCommentTbl> express = CmCommentTbl.find.where().eq("webSet.id", getWebSet(request).getId());
			if (StringUtils.isNotBlank(article_id)) {
				express.eq("email",article_id);
			}
			List<CmCommentTbl> commentList = express.orderBy("id desc").findList();
			
			String[] indexVal = { "参与单位", "参与人数", "联系人", "联系电话", "是否住宿","预约时间"};
			for (int i = 0; i < indexVal.length; i++) {
				Cell cell = rowIndex.createCell(i);
				cell.setCellValue(indexVal[i]);
			}
			int n = 1;
			System.err.println("orderlist:" + commentList.size());
			for (CmCommentTbl comm : commentList) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Object value[] = { comm.getCompany(), comm.getTitle(),comm.getName(),comm.getPhone(),comm.getSex(), comm.getCommentTime() != null ? sdf.format(comm.getCommentTime()) : null };
				Row row = sheet.createRow(n);
				for (int j = 0; j < value.length; j++) {
					Cell cell = row.createCell(j);
					if (value[j] == null) {
						value[j] = "--";
					}
					cell.setCellValue(value[j].toString());
				}
				n++;
			}
			outputToExcel(title, workBook, response);
		} catch (Exception e) {
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg("错误:" + e.getMessage());
			outJson(response, rj);
		}
	}


	/**
	 * 导出到页面
	 * 
	 * @param fileName
	 *            文件名
	 * @param workbook
	 *            数据
	 * @param response
	 * @throws IOException
	 */
	private void outputToExcel(String fileName, HSSFWorkbook workbook, HttpServletResponse response)
			throws IOException {
		response.reset();
		response.addHeader("content-disposition",
				"attachment;filename=" + URLEncoder.encode(fileName + ".xls", "utf-8"));
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		workbook.write(bos);
		bos.flush();
		bos.close();
	}
	/**
	 * 对留言状态修改和删除
	 * @param request
	 * @param response
	 * @param order 订单对象
	 */
	@RequestMapping("updata_comment")
	public void updata_comment(HttpServletRequest request,HttpServletResponse response,CmCommentTbl  comment){
		ResultJson rj=new ResultJson();
		try{
			comment.update();
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
	 * 根据Id查看留言详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @param url
	 * @return
	 */
	@RequestMapping("admin/obtain_comment_by_id")
	public ModelAndView obtain_comment_by_id(HttpServletRequest request,HttpServletResponse response,Integer id,String url){
		ModelAndView mv=createBgView(url, request, response);
		try{
			if(id==null||id==0){
				addResult(mv, false, "没有该信息");
			}
			CmCommentTbl comment=CmCommentTbl.find.byId(id);
			mv.addObject("data", comment);
		}catch(Exception e){
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * 在线报名
	 * @param request
	 * @param response
	 * @param sign
	 */
	@RequestMapping("new/student")
	public void newStudent(HttpServletRequest request,HttpServletResponse response,BnSignUpTbl sign){
		ResultJson rj=new ResultJson();
		try{
			if(sign==null) {
				throw new ParameterException("参数错误");
			}
			if(sign.getName()==null) {
				throw new ParameterException("学生姓名必填");
			}
			if(sign.getSex()==null) {
				throw new ParameterException("性别必填");
			}
			if(sign.getIdCard()==null) {
				throw new ParameterException("身份证号码必填");
			}
			if(sign.getGrade()==null) {
				throw new ParameterException("欲就读年纪必填");
			}
			if(sign.getNowSchool()==null) {
				throw new ParameterException("现在就读学校必填");
			}
			if(sign.getFatherName()==null) {
				throw new ParameterException("父亲姓名必填");
			}
			if(sign.getfPhone()==null) {
				throw new ParameterException("父亲联系方式必填");
			}
			if(sign.getMotherName()==null) {
				throw new ParameterException("母亲姓名必填");
			}
			if(sign.getmPhone()==null) {
				throw new ParameterException("母亲联系方式必填");
			}
			sign.setWebSet(getWebSet(request));
			sign.setIsDelete(-1);
			sign.setInsertTime(new Date());
			sign.insert();
			rj.setResult(true);
			rj.setResultMsg("成功");
		}catch(Exception e){
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
		}
		outJson(response, rj);
	}
	
	/**
	 * 删除报名
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("delete/student")
	public void deleteStudent(HttpServletRequest request,HttpServletResponse response,Integer id){
		ResultJson rj=new ResultJson();
		try{
			if(id==null) {
				throw new ParameterException("参数错误");
			}
			BnSignUpTbl sign =BnSignUpTbl.find.byId(id);
			if(sign==null) {
				throw new ParameterException("参数错误");
			}
			sign.setIsDelete(1);
			sign.update();
			rj.setResult(true);
			rj.setResultMsg("成功");
		}catch(Exception e){
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
		}
		outJson(response, rj);
	}
	
	@RequestMapping("admin/search/student")
	public ModelAndView searchStudent(HttpServletRequest request,HttpServletResponse response,String name,PageParameter page){
		ModelAndView mv=createBgView("business/zhaosheng/zhaosheng_list", request, response);
		try{
			ExpressionList<BnSignUpTbl> exp=BnSignUpTbl.find.where().eq("isDelete", -1).eq("webSet", getWebSet(request));
			if(!StringUtils.isBlank(name)) {
				exp.like("name", "%"+name+"%");
			}
			PagedList<BnSignUpTbl> student=exp.setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request, mv, student);
			mv.addObject("resultList", student);
		}catch(Exception e){
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
		}
		return mv;
	}
	
	@RequestMapping("student/obtain")
	public ModelAndView studentObtain(HttpServletRequest request,HttpServletResponse response,Integer id){
		ModelAndView mv=createBgView("business/zhaosheng/zhaosheng_obtain", request, response);
		try{
			if(id==null) {
				throw new ParameterException("参数错误");
			}
			BnSignUpTbl sign=BnSignUpTbl.find.byId(id);
			mv.addObject("sign", sign);
		}catch(Exception e){
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
		}
		return mv;
	}
	
	
	
}
