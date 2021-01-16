package com.tjsj.fwk.mvc.servlet;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tjsj.m_util.codec.RandomUtil;
import com.tjsj.wp.orm.entity.SmAccessoryTbl;
/**
 * Servlet implementation class UploadServlet
 */
@WebServlet(name = "DownloadServlet", urlPatterns = { "/downloadFile.htm" })
public class DownloadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
      
	private static Logger logger = LoggerFactory.getLogger(DownloadServlet.class);
	
  
	
	
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println(getRealRootPath(request));
//		response.getWriter().append("Don't allow access by get method");
		doPost(request, response);
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		
		
		ServletOutputStream stream = null; 
            try {  
            	String id = request.getParameter("id");  
            	if(id==null){
            		response.getWriter().append("文件不存在");
            	}
            	SmAccessoryTbl a=SmAccessoryTbl.find.byId(Integer.valueOf(id));
            	if(a==null){
            		response.getWriter().append("文件不存在");
            	}
           String path=a.getPath()+a.getName();
            	  // path是指欲下载的文件的路径。
                File file = new File(path);
                // 取得文件名。
                String filename = file.getName();
                // 取得文件的后缀名。
                String ext = filename.substring(filename.lastIndexOf(".")).toUpperCase();
                String newfilename = RandomUtil.getFlowRandomPcms()+ext;
                // 以流的形式下载文件。
                InputStream fis = new BufferedInputStream(new FileInputStream(path));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                // 清空response
                response.reset();
                // 设置response的Header
                response.addHeader("Content-Disposition", "attachment;filename=" + newfilename);
                response.addHeader("Content-Length", "" + file.length());
                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
               
               
            } catch (Exception e) {  
            	System.out.println(e.getMessage());
                e.printStackTrace();  
                logger.error("抛出异常", e);
            } finally {  
                if (stream != null) {  
                    try {
						stream.flush();
						stream.close();  
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
                }  
        }  
		
		
		
	}
	 
}
