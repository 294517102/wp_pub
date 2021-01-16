package com.tjsj.wp.mvc.controller.cms;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.entity.BaseEntity;
import com.tjsj.m_util.exception.ParameterException;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.BnJlinfoTbl;
import com.tjsj.wp.orm.entity.CmArticleTbl;
import com.tjsj.wp.orm.entity.CmArticleTemplateTbl;
import com.tjsj.wp.orm.entity.CmColumnTbl;
import com.tjsj.wp.orm.entity.CmCommentTbl;
import com.tjsj.wp.orm.entity.CmSmallContentTbl;
import com.tjsj.wp.orm.entity.CmSysLogTbl;
import com.tjsj.wp.orm.entity.SmAccessoryTbl;
import com.tjsj.wp.tools.ImportExcelUtil;
import com.tjsj.wp.tools.SystemLogService;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

@Controller
public class JlinfoMaintenanceController extends WpBaseController{
	@Autowired
	private EbeanServer ebeanServer;
	
	
	/**
	 * 模块的保存和编辑
	 * @param request
	 * @param response
	 * @param content 模块实体
	 * @param pic_id 图片id
	 */
	@RequestMapping("/info/query_info")
	public ModelAndView query_info(HttpServletRequest request,HttpServletResponse response,String idCard,String currentJob,String url,PageParameter page){
		ModelAndView mv=createBgView(url, request, response);
		try{
			
			ExpressionList<BnJlinfoTbl> exp=BnJlinfoTbl.find.setUseQueryCache(true).where().eq("isDelete", 9);
			if(StringUtils.isNotBlank(idCard)) {
				exp.eq("idCard", idCard);
			}
			if(StringUtils.isNotBlank(currentJob)) {
				exp.like("currentJob","%"+currentJob+"%");
			}
			PagedList<BnJlinfoTbl> jllist=exp.orderBy("id desc").setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			//pagePrint(request,mv,jllist);
			pageAjaxPrint(request, mv, jllist, "/info/query_info", "");
			addResult(mv,true, "查询成功");
		}catch(Exception e){
			e.printStackTrace();
			addResult(mv,false, e.getMessage());
		}
		return mv;
	}
	@RequestMapping("/admin/jlinfo")
	public ModelAndView jlinfo(HttpServletRequest request,HttpServletResponse response,String url,BnJlinfoTbl jlinfo,String ent_time,String train_time,String update_time){
		ModelAndView mv=createBgView(url, request, response);
		try {
			DateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
			if(StringUtils.isNotBlank(train_time)) {
				jlinfo.setTrainTime(DateUtil.parse(train_time, sdf));
			}
			if(StringUtils.isNotBlank(ent_time)) {
				jlinfo.setEntTime(DateUtil.parse(ent_time, "yyyy-MM-dd"));
			}
			if(StringUtils.isNotBlank(update_time)) {
				jlinfo.setUpdateTime(DateUtil.parse(update_time, "yyyy-MM-dd"));
			}
			if(jlinfo.getId()==0) {
				int c=BnJlinfoTbl.find.where().eq("number", jlinfo.getNumber()).eq("idCard",jlinfo.getIdCard()).eq("isDelete", 9).findCount();
				if(c>0) {
					throw new ParameterException("添加的证书已存在");
				}
				jlinfo.setWebSet(getWebSet(request));
				jlinfo.save();
			}else {
				BnJlinfoTbl olderjl=BnJlinfoTbl.find.byId(jlinfo.getId());
				ClassUtil.copyObjectByPropertyValues(olderjl, jlinfo);
				olderjl.update();
			}
			addResult(mv, true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	@RequestMapping("/admin/jllist")
	public ModelAndView jllist(HttpServletRequest request,HttpServletResponse response,String url,BnJlinfoTbl jlinfo,PageParameter page ){
		ModelAndView mv=createBgView(url, request, response);
		try {
			ExpressionList<BnJlinfoTbl> exp= BnJlinfoTbl.find.setUseQueryCache(true).where().eq("webSet.id",getWebSet(request).getId()).eq("isDelete", 9);
			//		栏目查看
			if(StringUtils.isNotBlank(jlinfo.getIdCard())){
				exp.like("idCard", "%"+jlinfo.getIdCard()+"%");
			}
			if(StringUtils.isNotBlank(jlinfo.getCurrentJob())) {
				exp.like("currentJob","%"+jlinfo.getCurrentJob()+"%");
			}
			if(StringUtils.isNotBlank(jlinfo.getFormerUnit())) {
				exp.like("formerUnit","%"+jlinfo.getFormerUnit()+"%");
			}
			if(StringUtils.isNotBlank(jlinfo.getNumber())) {
				exp.like("number","%"+jlinfo.getNumber()+"%");
			}
			PagedList<BnJlinfoTbl> jllist=exp.orderBy("id desc").setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request,mv,jllist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	@RequestMapping("/admin/jldetail")
	public ModelAndView adminjllist(HttpServletRequest request,HttpServletResponse response,String url,BnJlinfoTbl jlinfo){
		ModelAndView mv=createBgView(url, request, response);
		try {
			if(jlinfo.getId()==0){
				throw new ParameterException("请选择查询的数据");
			}
			BnJlinfoTbl info= BnJlinfoTbl.find.byId(jlinfo.getId());
			mv.addObject("data",info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping("/read_excel_data")
	public ModelAndView read_lsjs_excel_data(HttpServletRequest request,HttpServletResponse response,Integer file_id){
		ModelAndView mv=createBgView("", request, response);
		try{
			SmAccessoryTbl afile=SmAccessoryTbl.find.byId(file_id);
			if(afile==null){
				throw new ParameterException("文件不存在");
			}
			File file=new File(afile.getPath()+afile.getName());
			ImportExcelUtil ex=new ImportExcelUtil();
			List<Map<String, Object>> objList=ex.importExcel(file);
			JSONArray array=new JSONArray();
			for (Map<String, Object> map : objList) {
				JSONObject obj=new JSONObject(map);
				array.add(obj);
			}
			System.out.println("得到的obj"+objList+"==");
			mv.addObject("data",objList);
			mv.addObject("code", 0);
			mv.addObject("count", objList.size());
			addResult(mv, true, "查询成功");
		}catch(Exception e){
			e.printStackTrace();
			addResult(mv, false, "操作失败"+e.getMessage());
		}

		return mv;
	}
	@RequestMapping("admin/save_jl_list")
	public ModelAndView save_supplyList(HttpServletRequest request,HttpServletResponse response,String datalist){
		ModelAndView mv=createBgView("", request, response);
		try{
			if(StringUtils.isBlank(datalist)){
			throw new ParameterException("请选择上传的excel数据");
			}
			
			List<BnJlinfoTbl> list=JSONArray.parseArray(datalist,BnJlinfoTbl.class);
			for (BnJlinfoTbl info : list) {
				
				BnJlinfoTbl odata= BnJlinfoTbl.find.where().eq("number", info.getNumber()).eq("idCard",info.getIdCard()).eq("isDelete", 9).findUnique();
				
				if(odata!=null&& odata.getId()>0){
//					更新
					ClassUtil.copyObjectByPropertyValues(odata, info);
					odata.update();
					CmSysLogTbl syslog=new CmSysLogTbl(getLoginUser().getRealName()+ "数据导入证书信息,操作id"+odata.toString()+"===>"+info.toString(), "bn_jlinfo_tbl", 1, getLoginUser(), getWebSet(request));
					syslog.save();
				}else{
					info.setWebSet(getWebSet(request));
					info.setIsDelete(9);
					info.save();
				}
				
			}
			addResult(mv, true,"操作成功");
		}catch(Exception e){
			e.printStackTrace();
			addResult(mv, false,e.getMessage());
		}
		return mv;
	}	
	@RequestMapping("/admin/jlinfo/all_excel_export")
	public void orderExcelAllExport(HttpServletRequest request, HttpServletResponse response,String title,BnJlinfoTbl jlinfo) {
		ResultJson rj = new ResultJson();
		try {
			HSSFWorkbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet();
			Row rowIndex = sheet.createRow(0);
			if(StringUtils.isBlank(title)) {
				title="培训信息";
			}
			// 查询订单信息
			ExpressionList<BnJlinfoTbl> exp = BnJlinfoTbl.find.where().eq("webSet.id",getWebSet(request).getId()).eq("isDelete", 9);
			if(StringUtils.isNotBlank(jlinfo.getIdCard())){
				exp.like("idCard", "%"+jlinfo.getIdCard()+"%");
			}
			if(StringUtils.isNotBlank(jlinfo.getCurrentJob())) {
				exp.like("currentJob","%"+jlinfo.getCurrentJob()+"%");
			}
			if(StringUtils.isNotBlank(jlinfo.getFormerUnit())) {
				exp.like("formerUnit","%"+jlinfo.getFormerUnit()+"%");
			}
			if(StringUtils.isNotBlank(jlinfo.getNumber())) {
				exp.like("number","%"+jlinfo.getNumber()+"%");
			}
			List<BnJlinfoTbl> commentList = exp.orderBy("id desc").findList();
			
			String[] indexVal = {"证书编号", "姓名", "性别", "身份证号", "学历","专业","培训时间","有效期至","现就职单位","原就职单位","变更时间"};
			for (int i = 0; i < indexVal.length; i++) {
				Cell cell = rowIndex.createCell(i);
				cell.setCellValue(indexVal[i]);
			}


			int n = 1;
			System.err.println("orderlist:" + commentList.size());
			for (BnJlinfoTbl comm : commentList) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Object value[] = { comm.getNumber(),comm.getName(),comm.getStrSex(),comm.getIdCard(),comm.getRecord(),comm.getMajor(),comm.getTrainTime()!= null ? sdf.format(comm.getTrainTime()) : null,comm.getEntTime()!= null ? sdf.format(comm.getEntTime()) : null,comm.getCurrentJob(),comm.getFormerUnit(),comm.getUpdateTime()!=null? sdf.format(comm.getUpdateTime()) : null };
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
}
