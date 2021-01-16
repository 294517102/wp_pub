package com.tjsj.wp.mvc.controller.cms;

import java.io.File;
import java.util.Date;
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
public class AccessoryMaintenanceController extends WpBaseController{
	@Autowired
	private EbeanServer ebeanServer;
	/**
	 * 图片列表
	 * @param request
	 * @param response
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping("/admin/accessory_list")
	public ModelAndView accessoryList(HttpServletRequest request,HttpServletResponse response, PageParameter page){
		ModelAndView mv=createBgView("tools/file_manager/accessory_list", request, response);
		try {
			ExpressionList explist=SmAccessoryTbl.find.setUseQueryCache(true).where();
			if(this.getLoginUser().getType()==4&&!this.getLoginUser().getUsername().equalsIgnoreCase(Const.ADMIN)){
				explist.eq("creator.id", getLoginUser().getId());
			}
			PagedList<SmAccessoryTbl> acclist=explist.setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request, mv, acclist);
		} catch (Exception e) {
			e.printStackTrace();		}
		return mv;
	}
	@RequestMapping("/admin/file_manager.htm")
	public ModelAndView accessoryList(HttpServletRequest request,HttpServletResponse response, PageParameter page,Integer type,String fileName){
		ModelAndView mv=createBgView("tools/file_manager/accessory_list", request, response);
		try {
			
			type = type==null?1:type;
			ExpressionList explist=SmAccessoryTbl.find.where();
			
			if(StringUtils.isNotBlank(fileName)){
				explist.like("originalName", "%"+fileName+"%");
			}
			if(this.getLoginUser().getType()==4&&!this.getLoginUser().getUsername().equalsIgnoreCase(Const.ADMIN)){
				explist.eq("creator.id", getLoginUser().getId());
			}
			List<String> list = Lists.newArrayList();
			list.add(".jpg");
			list.add(".jpeg");
			list.add(".bmp");
			list.add(".gif");
			list.add(".png");
			list.add(".tif");
			if(type==1){
				explist.in("ext", list);
			}else{
				explist.notIn("ext", list);
			}
			PagedList<SmAccessoryTbl> acclist=explist.order("id desc").setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request, mv, acclist);
		} catch (Exception e) {
			e.printStackTrace();		}
		return mv;
	}
	
	
	/**
	 * 删除图片表对应的无用文件
	 * @param request
	 * @param response
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping("/admin/delete_accessory_file")
	public void deleteAccessoryFile(HttpServletRequest request,HttpServletResponse response,Integer id){
		ResultJson rj = new ResultJson();
		try {
		if(!isLogin()){
			throw new RuntimeException("请登录后操作");
		}
		if(id==null || id<=0){
		throw new RuntimeException("参数无效");
		}
		SmAccessoryTbl access=SmAccessoryTbl.find.byId(id);
		if(access==null){
		throw new RuntimeException("参数无效");	
		}
		deleteFile(new File(access.getPath()+access.getName()));
//		移除缩略图
		deleteFile(new File(getSmallPath(access.getPath()+access.getName())));
		
//		if(rj.isResult()){
			access.delete();
//		}
		rj.setResult(true);
		rj.setResultMsg("文件删除成功");
		} catch (Exception e) {
		rj.setResult(false);
		rj.setResultMsg(e.getMessage());
		}
		outJson(response, rj);
	}
	
	/**
	 * 删除文件操作
	 * @param file
	 * @return
	 */
	private ResultJson deleteFile(File file) {
		ResultJson rj = new ResultJson();
	    if (file.exists()) {//判断文件是否存在  
	     if (file.isFile()) {//判断是否是文件  
	      file.delete();//删除文件   
	      rj.setResult(true);
	      rj.setResultMsg("文件删除成功");
	     } else if (file.isDirectory()) {
	    	 rj.setResult(false);
		     rj.setResultMsg("对不起你没有权限删除"+file.getName()+"文件夹");
	    	 //否则如果它是一个目录  
	   /*   File[] files = file.listFiles();//声明目录下所有的文件 files[];  
	      for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件  
	       this.deleteFile(files[i]);//把每个文件用这个方法进行迭代  
	      }  
	      file.delete();//删除文件夹  
*/	     }  
	    } else {  
	    	 rj.setResult(false);
		     rj.setResultMsg("所删除的文件不存在");
	    }
		return rj;  
	   }  
	/**
	 * 得到缩略图路径
	 * @param path
	 * @return
	 */
	public static String getSmallPath(String path){
		String  ext = getFileExt(path);
		String newPath = path.replace("."+ext,".small."+ext);
		System.out.println("得到的缩略图路径:"+newPath);
		return newPath;
	}
		 /**
	     * 返回文件的扩展名
	     * @param filePath
	     * @return
	     */
	    public static String getFileExt(String filePath) {
	        if (filePath != null && !"".equals(filePath)) {
	            return filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
	        }
	        return null;
	    }
	
	    /**
	     * 保存轮播图全局操作
	     * @param request
	     * @param response
	     * @param pauseTime
	     * @param status
	     * @return
	     */
	    @RequestMapping("admin/save_home_slide_status")
	    public ModelAndView saveHomeSlideStatus(HttpServletRequest request,HttpServletResponse response,Integer pauseTime,Integer status){
	    	ModelAndView mv=createView("", request, response);
			if(pauseTime<=0||StringUtil.isBlank(pauseTime)){
	    		addResult(mv, false, "切换时间有误");
	    		return mv;
	    	}
	    	if(StringUtil.isBlank(status)){
	    		addResult(mv, false, "提示文字开关异常");
	    		return mv;
	    	}
	    	try {
		    	SmWebSetTbl webset=SmUserTbl.find.byId(getLoginUser().getId()).getWebSet();
		    	List<CmHomeSlideTbl>homeSlides=CmHomeSlideTbl.find.where().eq("webSet", webset).findList();
		    	for (CmHomeSlideTbl homeSlide : homeSlides) {
		    		homeSlide.setPauseTime(pauseTime);
		    		homeSlide.setRemarkState(status);
		    		homeSlide.update();
				}
		    	webset=SmUserTbl.find.byId(getLoginUser().getId()).getWebSet();
		    	Session session = getSubjectSession();
		    	session.setAttribute("webSet", webset);
		    	addResult(mv, true, "修改成功");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				addResult(mv, false, "修改失败，系统错误!");
			}
	    	
	    	return mv;
	    }
	    
	    /**
	     * 跳转到轮播图添加
	     * @param request
	     * @param response
	     * @return
	     */
	    @RequestMapping("admin/to_home_slide_add")
	    public ModelAndView to_home_slide_add(HttpServletRequest request,HttpServletResponse response){
	    	ModelAndView mv=createBgView("system_setting/site_manager/slide_add", request, response);
	    	List<CmHomeSlideTbl> homeSlide=CmHomeSlideTbl.find.where().eq("webSet", getWebSet(request)).orderBy("sequence desc").findList();
	    	if(homeSlide.size()>0){
	    		mv.addObject("orderby", homeSlide.get(0).getSequence()+1);
	    	}else{
	    		mv.addObject("orderby", 1);
	    	}
	    	return mv;
	    }
	    
	    /**
	     * 保存轮播图
	     * @param request
	     * @param response
	     * @param params
	     * @param pic_id
	     * @return
	     * @throws Exception 
	     */
	    @RequestMapping("admin/save_home_slide")
	    public ModelAndView save_home_slide(HttpServletRequest request,HttpServletResponse response,CmHomeSlideTbl params,Integer pic_id) throws Exception{
	    	ModelAndView mv=createView("", request, response);
	    	try {
	    		if(StringUtil.isBlank(pic_id)){
		    		addResult(mv, false, "图片必须有哦！");
		    		return mv;
		    	}
		    	SmAccessoryTbl accessory=SmAccessoryTbl.find.byId(pic_id);
		    	SmWebSetTbl webset=SmWebSetTbl.find.byId(getWebSet(request).getId());
		    	params.setAccessory(accessory);
		    	params.setWebSet(webset);
		    	params.setDeleteStatus(-1);
		    	params.setRemarkState(-1);
		    	params.setState(-1);
		    	params.setCreator(SmUserTbl.find.byId(getLoginUser().getId()));
		    	System.err.println(params.getId());
		    	if(StringUtil.isBlank(params.getId())||params.getId()==0){
		    		params.setInsertTime(new Date());
		    		params.setPauseTime(3500);
		    		params.save();
		    		addResult(mv, true, "保存成功");
		    	}else{
		    		CmHomeSlideTbl homeSlide=CmHomeSlideTbl.find.byId(params.getId());
		    		ClassUtil.copyObjectByPropertyValues(homeSlide, params);
		    		homeSlide.update();
		    		addResult(mv, true, "修改成功");
		    	}
		    	Session session = getSubjectSession();
		    	session.setAttribute("webSet", webset);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
	    	
	    	return mv;
	    }
}
