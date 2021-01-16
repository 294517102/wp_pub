package com.tjsj.wp.mvc.controller.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.codec.HanyuPinyinUtil;
import com.tjsj.m_util.codec.HanyuPinyinUtil.Type;
import com.tjsj.m_util.comm.CommUtil;
import com.tjsj.m_util.file.imageUtils;
import com.tjsj.m_util.json.JsonPluginsUtil;
import com.tjsj.wp.orm.entity.SmRoleTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;
import com.tjsj.wp.tools.L2CacheManagerService;


@Controller
public class VerifyController {

	
	private static Logger logger = LoggerFactory.getLogger(VerifyController.class);

	@RequestMapping({ "/verify_code.htm" })
	public void validate_code(HttpServletRequest request, HttpServletResponse response, String code, String code_name) {
		HttpSession session = request.getSession(false);
		String verify_code = "";
		if (CommUtil.null2String(code_name).equals(""))
			verify_code = (String) session.getAttribute("verify_code");
		else {
			verify_code = (String) session.getAttribute(code_name);
		}
		boolean ret = true;
		if ((verify_code != null) && (!verify_code.equals(""))
				&& (!CommUtil.null2String(code.toUpperCase()).equals(verify_code))) {
			ret = false;
		}

		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@RequestMapping({ "/verify"})
	public void verify(HttpServletRequest request, HttpServletResponse response, String name,String param){
		ResultJson result = new ResultJson();
		result.setCode("");
		result.setResultMsg("");
		result.setUrl("");
		System.out.println(name+"==="+param);
		try {
			response.setContentType("text/plain");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			//有些组件以param形式传入
		if(StringUtils.isBlank(param)||StringUtils.isBlank(name)){
			logger.info("the username is null , Check failure ");
			result.setInfo("輸入錯誤");
			result.setStatus("n");
			writer.print(JsonPluginsUtil.beanToJson(result));
			return;
			}
			System.err.println(name);
			
			result.setInfo("验证通过");
			result.setStatus("y");
				switch(name){
				
					case "username":
						SmUserTbl users = SmUserTbl.find.where().eq("username", param).findUnique();
						if ((users != null)) {
							result.setInfo("用户已存在");
							result.setStatus("n");
						}
						break;
					case "phone":
						SmUserTbl phone = SmUserTbl.find.where().eq("phone", param).findUnique();
						 if ((phone != null)) {
								result.setInfo("电话已存在");
								result.setStatus("n");
						}else{
							result.setInfo("验证通过！");
							result.setStatus("y");
						}
						break;
					case "email":
						SmUserTbl email = SmUserTbl.find.where().eq("email", param).findUnique();
						if ((email != null)) {
							result.setInfo("邮箱已存在");
							result.setStatus("n");
						}
						break;
					case "name":
						SmRoleTbl role=SmRoleTbl.find.where().eq("name", param).findUnique();
						if((role) !=null){
							result.setInfo("角色已存在");
							result.setStatus("n");
						}
						break;
					default:
						logger.info("the 'req' value is incorrect !");
						//result.setInfo("参数不正确");
						result.setInfo("参数不正确");
						result.setStatus("n");
						break;
				}
				
				logger.info("check result:"+ClassUtil.classToString(result));
				outJson(response, result);
//				writer.print(JsonPluginsUtil.beanToJson(result));
		}catch (Exception e) {
			logger.error("Execption throws:", e);
			result.setInfo("参数不正确");
			result.setStatus("n");
		}
		
	}
	

	@RequestMapping({ "/verify_output.htm"})
	public void verify(HttpServletRequest request, HttpServletResponse response, String name) throws IOException {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		HttpSession session = request.getSession(false);
		 // 生成随机字串  
        String verifyCode = imageUtils.generateVerifyCode(4);

		if (CommUtil.null2String(name).equals("")){
			session.setAttribute("verify_code", verifyCode);
		}else {
			session.setAttribute(name, verifyCode);
		}
		ServletOutputStream responseOutputStream = response.getOutputStream();
		BufferedImage image=imageUtils.outputImage(200, 80, verifyCode);
		ImageIO.write(image, "JPEG", responseOutputStream);
		responseOutputStream.flush();
		responseOutputStream.close();
	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	
	@RequestMapping({ "/verify_check.htm" })
	public void verify_check(HttpServletRequest request, HttpServletResponse response){
		ResultJson rj = new ResultJson();
		
		HttpSession session = request.getSession();
		String verify_code = null==session.getAttribute("verify_code")?"":session.getAttribute("verify_code").toString();
		
		rj.setResultMsg(verify_code);
		outJson(response, rj);
		return;
	}
	
	/**
	 * 将BaseEntity以json字符串格式输出
	 * @param response
	 * @param entity 实体对象
	 */
	protected void outJson(HttpServletResponse response, Object entity) {
		this.outJson(response, JSONObject.toJSONString(entity));
	}
	
	/**
	 * 输出json数据字符串
	 * @param response HttpServletResponse对象
	 * @param jsonDataStr
	 *            字符串
	 */
	public void outJson(HttpServletResponse response, String jsonDataStr) {
		try {
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(jsonDataStr);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 把汉字转化成拼音 
	 * @param request
	 * @param response
	 * @param str
	 */
	@RequestMapping("admin/StringToPingYin")
	public void ObtStringToPingYin(HttpServletRequest request,HttpServletResponse response,String str){
		ResultJson rj=new ResultJson();
		try {
			if(StringUtils.isBlank(str)){
				rj.setResultData("参数异常");
				rj.setResult(false);
			}
			HanyuPinyinUtil tool = new HanyuPinyinUtil(); 
			rj.setResultData(tool.toPinYin(str, "_", Type.LOWERCASE));
			rj.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			rj.setResultMsg(e.getMessage());
			rj.setResult(false);
		}
		outJson(response, rj);
	}
	@RequestMapping("clearL2Cache")
	public void clearL2Cache(HttpServletRequest request,HttpServletResponse response,String className){
		ResultJson rj=new ResultJson();
		try {
			if(StringUtils.isNotBlank(className)){
				L2CacheManagerService.clear(Class.forName("com.tjsj.wp.orm.entity."+className));
			}else{
				L2CacheManagerService.clearAll();
			}
			rj.setResultMsg("缓存清理成功");
			rj.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			rj.setResultMsg(e.getMessage());
			rj.setResult(false);
		}
		outJson(response, rj);
	}
	
	
}
