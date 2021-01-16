package com.tjsj.fwk.mvc.servlet;

import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.entity.FileEntity;
import com.tjsj.m_util.image.ThumbnailUtil;
import com.tjsj.wp.orm.entity.SmAccessoryTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;
/**
 * Servlet implementation class UploadServlet
 */
@WebServlet(name = "UploadServletByWebAccessory", urlPatterns = { "/upload/uploadImgByWebAccessory.htm" })
public class UploadServletByWebAccessory extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
      
	private static Logger logger = LoggerFactory.getLogger(UploadServletByWebAccessory.class);
	
  
	private ServletConfig config;
	
	private int limitCountInOneDay;
	
    public void init(ServletConfig config) throws ServletException {
        this.config = config;       
    }
    
	/**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServletByWebAccessory() {
        super();
        // TODO Auto-generated constructor stub
        limitCountInOneDay = 1000;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println(getRealRootPath(request));
		response.getWriter().append("Don't allow access by get method");
	}
	
	private String getRelativeRootPath(HttpServletRequest request){
		String relativeRootPath = request.getRequestURL().toString().replace(request.getRequestURI(), "")+"/";
		return relativeRootPath;
	}
	
	private String getRealRootPath(HttpServletRequest request){
		String realRootPath = config.getServletContext().getRealPath("/");
		String contextPath = request.getContextPath();
		if(contextPath.length()>1){
			if(contextPath.startsWith("/")){
				if(realRootPath.indexOf(contextPath) < 0){
					contextPath = contextPath.replace("/", "\\");				
				}
			}
			realRootPath = realRootPath.replace(contextPath, "");
		}
		//web文件均放到webapp目録
		realRootPath += "webapp/";
		return realRootPath;
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		ResultJson rj = new ResultJson();
		SmUserTbl user = (SmUserTbl) request.getSession(false).getAttribute(Const.SESSION_USER);
		if(user==null){
			rj.setResult(false);
			rj.setResultMsg("用户超时");
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(rj));
			return;
		}
		String uploadFilePath = "web_accessory";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		String uri = uploadFilePath + File.separator +sdf.format(new Date())+ File.separator;
		if(StringUtils.isNotBlank(request.getParameter("type"))&&request.getParameter("type").equals("client")){
			uri += "client" +  File.separator;
		}else{
			uri += "publish" +  File.separator;
		}
		
		uri += request.getServerName()==null?"default":request.getServerName() + File.separator;
		
		try {
		//List<Accessory> accessories= accessoryService.query("select obj from Accessory obj where obj.user.id = :userid and obj.addTime >= :addtime", paramMap, -1, -1);
		List<SmAccessoryTbl> accessories = SmAccessoryTbl.find.where().eq("creator.id", user.getId()).ge("insert_time", sdf.format(new Date())).findList();
		if(accessories.size() >= limitCountInOneDay ){
			logger.error("the user '"+user.getUsername()+"' upload file more than "+limitCountInOneDay+
						" in the day 。 Request to stop!");
			rj.setResult(false);
			rj.setResultMsg("当日用户上传文件超过"+limitCountInOneDay);
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(rj));
			return;
		}
		String realRootPath = getRealRootPath(request);
		String relativeRootPath = getRelativeRootPath(request);
		HashMap<String, Object> map = Maps.newHashMap();
		String fileName = new String();
		map = saveFileToServer(request, realRootPath+uri, fileName, null);
		if(map.get("fes")==null){
			rj.setResult(false);
			rj.setResultMsg("接收文件失败");
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(rj));
			return;
		}
		List<FileEntity> fes = (List<FileEntity>) map.get("fes");
		if(fes.size()<=0){
			rj.setResult(false);
			rj.setResultMsg("接收文件失败");
			PrintWriter writer = response.getWriter();
			writer.print(JSONObject.toJSON(rj));
			return;
		}	
		for (FileEntity fileEntity : fes) {
			SmAccessoryTbl accessory = new SmAccessoryTbl();
			accessory.setName(fileEntity.getName());
//			设置原文件名称
			accessory.setOriginalName(fileEntity.getOrgName());
			accessory.setExt(fileEntity.getExt());
			accessory.setSize((int)fileEntity.getSize());
			accessory.setPath(fileEntity.getPath());
			accessory.setWidth(fileEntity.getWidth());
			accessory.setHeight(fileEntity.getHight());
			accessory.setInsert_time(new Date());
			accessory.setCreator(user);
			fileEntity.setUrl(relativeRootPath+uri.replace("\\", "/")+fileEntity.getName());
			accessory.setUrl(fileEntity.getUrl());	
			accessory.setOriginalUrl(fileEntity.getUrl());
//			不作压缩处理
//			accessory.setUrl(ThumbnailUtil.getSmallPath(fileEntity.getUrl()));
			accessory.save();
			fileEntity.setAcyId(accessory.getId());
		}
		rj.setResult(true);
		rj.setResultMsg("上传成功");
		rj.setData(fes);
		PrintWriter writer = response.getWriter();
		writer.print(JSONObject.toJSON(rj));
		   
		} catch (Exception e) {
			logger.error("Exception throws:", e);
			rj.setResult(false);
			rj.setResultMsg("上传失败:"+e.getMessage());
			PrintWriter writer = response.getWriter();
			writer.print(JSONObject.toJSON(rj));
		
		}
		return;
	}
	 
	private HashMap<String, Object> saveFileToServer(HttpServletRequest request, 
												String filePath, 
												String fileName, 									
												String[] extendes)throws IOException, FileUploadException {
		HashMap<String, Object> map = Maps.newHashMap();
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List fileList = null;
		fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		String name = new String();
		String orgname = new String();
		String extName = new String();
		List<FileEntity> fes= new ArrayList<FileEntity>();
		//支持多文件上传
		while (it.hasNext()) {
			FileItem item = (FileItem) it.next();
			if (!item.isFormField()) {
				name = item.getName();
				System.out.println("文件原名称："+name);
				if (StringUtils.isBlank(name)) {
					continue;
				}
				if (name.lastIndexOf(".") >= 0) {
					extName = name.substring(name.lastIndexOf("."));
				}
				//校验后缀名
				boolean flag = false;
				if (extendes != null) {
					for (String s : extendes) {
						if (extName.equalsIgnoreCase(s))
							flag = true;
					}
				}else{
					flag = true;
				}
				if(!flag){ 
					map.put("result", false);
					map.put("desc", "扩展名错误");
					return  map;
				}
				//创建文件夹
				File folder = new File(filePath);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				FileEntity fe =new FileEntity();
				fe.setSize(item.getSize());
				File file = null;	
				//对于指定文件名，防止重复
				do {
					orgname=name;
					if(StringUtils.isNotBlank(fileName)){
						name = fileName;
						file = new File(filePath + name);
					}else{
						name = UUID.randomUUID().toString();
						file = new File(filePath + name + extName);
					}	
					//防止文件名称重复
				} while (file.exists()&&StringUtils.isBlank(fileName));
				fe.setExt(extName);
				fe.setOrgName(orgname);
				fe.setPath(filePath);
				fe.setName(file.getName());				
				try {
					item.write(file);
					//图片读取大小
					if (isImg(extName)) {
							BufferedImage bis = ImageIO.read(file);
							fe.setWidth(bis.getWidth());
							fe.setHight(bis.getHeight());					
							//压缩文件
//							ThumbnailUtil.thumbnailAccessory(file, fe); 
					}
				} catch (Exception e) {
					logger.error("Exception throws:", e);
				}
				
				
				fes.add(fe);
			}
		}
		map.put("fes", fes);
		return map;
	} 
	/**
	 * 查询是否是图片,此种方式通过后缀方式其实不需要也行,因为可以在上传的时候就设置上传类型是图片
	 * 
	 * @param extend
	 * @return
	 */
	public  boolean isImg(String extend) {
		boolean ret = false;
		List<String> list = Lists.newArrayList();
		list.add(".jpg");
		list.add(".jpeg");
		list.add(".bmp");
		list.add(".gif");
		list.add(".png");
		list.add(".tif");
		for (String s : list) {
			if (s.equals(extend))
				ret = true;
		}
		return ret;
	}
}
