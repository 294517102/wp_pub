package com.tjsj.wp.mvc.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tjsj.base.constant.DefSet;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.fwk.mvc.shiro.annotation.SessionUser;
import com.tjsj.m_util.exception.ParameterException;
import com.tjsj.m_util.other.ShellUtil;
import com.tjsj.wp.mvc.config.TomcatConfig;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.CmHomeSlideTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;

@Controller
public class SystemSettingController extends WpBaseController {

	@Autowired
	private DefSet defSet;
	
	private static Logger logger = LoggerFactory.getLogger(SystemSettingController.class);
	
	public SystemSettingController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping("/admin/release")
	public void release( 	HttpServletRequest request,
							HttpServletResponse response,
							String _sign,
							@SessionUser SmUserTbl user){
		ResultJson rj = new ResultJson();
		try {
			if(StringUtils.isBlank(_sign)){
				throw new ParameterException("请求无效");
			}
			if(!_sign.equalsIgnoreCase(com.tjsj.m_util.codec.MD5.md5(user.getUsername()+user.getId()))){
				throw new ParameterException("参数错误");
			}
			 String webRoot = SystemSettingController.class.getResource("/").getFile();
			 		webRoot = StringUtils.removeEnd(webRoot, "classes/");
			 		webRoot += "webapp/";
			 String os = System.getProperty("os.name");  
			 if(os == null || os.toLowerCase().indexOf("linux") <= -1){  
			    throw new RuntimeException("运行环境非linux系统");  
			 } 
			 StringBuffer  sb =  new StringBuffer();
			 sb.append("rsync -avogz ");
			 sb.append(defSet.getEditDir()+" ");
			 sb.append(webRoot);
			 logger.info("excuse shell command:"+sb.toString());
			String rs = ShellUtil.execCmd(sb.toString(), null);
			logger.info("result:"+rs);
			if(StringUtils.isNotBlank(rs)&&rs.indexOf("sending incremental file list")>=0){
				rj.setResult(true);
			}
			rj.setResultMsg(rs);
			 
		} catch (Exception e) {
			logger.error("抛出异常：", e);
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
		}
		outJson(response, rj);
	}
	
	@RequestMapping("admin/to_slide_set")
	public ModelAndView to_slide_set(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv=createBgView("system_setting/site_manager/slide_set", request, response);
		List<CmHomeSlideTbl> homeSlidelist=CmHomeSlideTbl.find.where().eq("webSet.id", getWebSet(request).getId()).eq("state", -1).eq("delete_status", -1).findList();
		mv.addObject("homeSlide", homeSlidelist);
		return mv;
	}
}
