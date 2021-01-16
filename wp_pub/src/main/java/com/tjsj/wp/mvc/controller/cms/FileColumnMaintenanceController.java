package com.tjsj.wp.mvc.controller.cms;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.CmColumnTbl;
import com.tjsj.wp.orm.entity.CmHomeSlideTbl;
import com.tjsj.wp.orm.entity.SmAccessoryTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;

/**
 * 
 * @author andrew_silence
 * @date 2017年8月10日 下午7:16:47
 * @version V1.0
 */

@Controller
public class FileColumnMaintenanceController extends WpBaseController{
	@Autowired
	private EbeanServer ebeanServer;
	@RequestMapping("/admin/column_file_list.htm")
	public ModelAndView columnFileList(HttpServletRequest request,HttpServletResponse response, PageParameter page,Integer cid,String fileName){
		ModelAndView mv=createBgView("content_manager/file_colum/file_list", request, response);
		try {
			ExpressionList explist=SmAccessoryTbl.find.where().eq("columnList.id", cid);
			if(StringUtils.isNotBlank(fileName)){
				explist.like("originalName", "%"+fileName+"%");
			}
			PagedList<SmAccessoryTbl> acclist=explist.order("id desc").setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request, mv, acclist);
		} catch (Exception e) {
			e.printStackTrace();		}
		return mv;
	}
    /**
     * 
     * @param request
     * @param response
     * @param page
     * @return
     */
	@RequestMapping("/admin/save_file")
	public ModelAndView saveFile(HttpServletRequest request,HttpServletResponse response, String file_list,Integer colunm_id,String title){
		ModelAndView mv=createBgView("", request, response);
		try {
			if(StringUtils.isBlank(file_list)){
				addResult(mv, false, "请上传文件");
	    		return mv;
			}
			if(colunm_id==null||colunm_id==0){
				addResult(mv, false, "请选择上传的栏目");
				return mv;
			}
			CmColumnTbl c=CmColumnTbl.find.byId(colunm_id);
			if(c==null){
				addResult(mv, false, "请选择上传的栏目");
				return mv;
			}
			Set<SmAccessoryTbl> fileList=new HashSet<>();
			String[] arr=StringUtils.split(file_list, ",");
			for (String string : arr) {
				SmAccessoryTbl a=SmAccessoryTbl.find.byId(Integer.valueOf(string));
				if(StringUtils.isNotBlank(title)){
					a.setInfo(title);
				}
				if(a!=null){
					fileList.add(a);
				};
			}
			if(fileList.size()>0){
				c.getFileList().addAll((fileList));
				c.update();
			}
			addResult(mv, true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();		}
		return mv;
	}
}
