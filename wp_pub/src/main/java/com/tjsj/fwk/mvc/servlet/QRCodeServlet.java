package com.tjsj.fwk.mvc.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.tjsj.m_util.file.imageUtils;



@WebServlet(name = "QRCodeServlet", urlPatterns = { "/QRCodeServlet.htm" })
public class QRCodeServlet extends HttpServlet {
	
	 /**
	 * @author gongdzh 
	 * 2017年3月29日 下午5:25:07
	 */
	
	private static Logger logger = LoggerFactory.getLogger(QRCodeServlet.class);
	
	private static final long serialVersionUID = 1L;
		private static final String KEY = "key";  
	    private static final String SIZE = "size";  
	    private static final String IMAGETYPE = "jpg";   
	    private static final int BLACK = 0xff000000;
	    private static final int WHITE = 0xFFFFFFFF;
	    @Override
	    protected void doGet(HttpServletRequest req,
	        HttpServletResponse resp) throws ServletException, IOException {
	 
	    	String keycode = req.getParameter(KEY);
	        if (StringUtils.isNotBlank(keycode)) {  
	            ServletOutputStream stream = null; 
	            try {  
	                int size=129;  
	                String msize = req.getParameter(SIZE);  
	                if (StringUtils.isNotBlank(msize)) {  
	                    try{  
	                        size=Integer.valueOf(msize);  
	                    } catch (NumberFormatException e) {  
	                    	e.printStackTrace();
	                    	logger.error("抛出异常", e);
	                    }  
	                }  
	           
	                stream = resp.getOutputStream();  
	                QRCodeWriter writer = new QRCodeWriter();  
	               
	                BitMatrix m = writer.encode(keycode, BarcodeFormat.QR_CODE, size, size);  
	               
	                MatrixToImageWriter.writeToStream(m, IMAGETYPE, stream);  
	             
	                String physicalPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()+"/file.png";
	                File file = new File(physicalPath);
	                if (!file.getParentFile().exists()) {
	                    file.getParentFile().mkdirs();
	                }
	                BufferedImage image = toBufferedImage(m);
	                ImageIO.write(image, "png",file);
	                
	            } catch (Exception e) {  
	            	System.out.println(e.getMessage());
	                e.printStackTrace();  
	                logger.error("抛出异常", e);
	            } finally {  
	                if (stream != null) {  
	                    stream.flush();  
	                    stream.close();  
	                }  
	            }  
	        }  
	    }

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			// TODO Auto-generated method stub
			this.doGet(req, resp);
		}
		  /**
	     * 生成二维码内容<br>
	     * 
	     * @param matrix
	     * @return
	     */
	    public static BufferedImage toBufferedImage(BitMatrix matrix) {
	        int width = matrix.getWidth();
	        int height = matrix.getHeight();
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        for (int x = 0; x < width; x++) {
	            for (int y = 0; y < height; y++) {
	                image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
	            }
	        }
	        return image;
	    }
	    
	  
}
