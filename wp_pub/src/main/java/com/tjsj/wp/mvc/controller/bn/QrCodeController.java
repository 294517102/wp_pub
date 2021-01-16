package com.tjsj.wp.mvc.controller.bn;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import sun.misc.BASE64Decoder;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.SmAccessoryTbl;

import sun.misc.BASE64Encoder;

@Controller
public class QrCodeController extends WpBaseController {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int margin = 0;
    private static final int LogoPart = 4;
//    private static final String logoPath ="C:\\Users\\andrew-silence\\Desktop\\微信图片_20200512124659.png";


    @RequestMapping("/qrcodeutil.htm")
    public void getQrCodeFile(HttpServletResponse resp,String key,Integer size,Integer acc_id) throws Exception {
    	try {
    	    String logoPath ="";
    		ServletOutputStream stream = resp.getOutputStream();
    		if(org.apache.commons.lang3.StringUtils.isBlank(key)) {
    			key="没有二维码内容";
    		}
    		int width = 260;
    		int height = 260;
    		if(size!=null && size!=0) {
    			width=size;
    			height=size;
    		}
    		String content = key;
    		BitMatrix bitMatrix = setBitMatrix(content, width, height);
    		
    		BufferedImage image = toBufferedImage(bitMatrix);
    		if(acc_id!=null && acc_id!=0) {
    			SmAccessoryTbl a=SmAccessoryTbl.find.byId(acc_id);
				if(a!=null) {
					logoPath=a.getUrl();	
				}
    		}
    		// 加入LOGO水印效果
    		if (logoPath!=null &&!logoPath.equals("")) {
    			image = addLogo(image, logoPath);
    		}
    		// 可通过输出流输出到页面,也可直接保存到文件
    		ImageIO.write(image, "png", resp.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * 生成二维码矩阵信息
     * @param content 二维码图片内容
     * @param width 二维码图片宽度
     * @param height 二维码图片高度
     */
    public static BitMatrix setBitMatrix(String content, int width, int height){
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 指定编码方式,防止中文乱码
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 指定纠错等级
        hints.put(EncodeHintType.MARGIN, margin); // 指定二维码四周白色区域大小
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitMatrix;
    }



    /**
     * 生成二维码图片  去白边
     * @param matrix 二维码矩阵信息
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
    /**
     * 在二维码图片中添加logo图片
     * @param image 二维码图片
     * @param logoPath logo图片路径
     * @throws IOException 
     */
    public static BufferedImage addLogo(BufferedImage image, String logoPath) throws IOException{
        BASE64Decoder base64de = new BASE64Decoder();
        Graphics2D g = image.createGraphics();
        //消除文字锯齿
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //消除画图锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //把Base64编码过的字节数组字符串解码转换成字节数组
        byte[] b= base64de.decodeBuffer(downloadFile(logoPath));
        //将字节数组转化为输入流
        InputStream   is=new ByteArrayInputStream(b);
        //将图片流写入ImageIO流
        BufferedImage logoImage = ImageIO.read(is);
        // 计算logo图片大小,可适应长方形图片,根据较短边生成正方形
        int width = image.getWidth() < image.getHeight() ? image.getWidth() / LogoPart : image.getHeight() / LogoPart;
        int height = width;
        // 计算logo图片放置位置
        int x = (image.getWidth()-width)/ 2;
        int y = (image.getHeight()-height)/ 2;
        int x1 = (image.getWidth()-width-10)/ 2;
        int y1 = (image.getHeight()-height-10)/ 2;
        // 边框颜色
        g.setColor(Color.white);
        // 画笔粗细
        g.setStroke(new BasicStroke(5)); // 画笔粗细
        //画图层,作用是在logo图片周围加点白边，显示更美观
        g.fillRect(x1, y1, width+10, height+10);
        //绘制logo边框,
        g.drawRoundRect(x1, y1, width+10,height+10, 30, 30);

        // 在二维码图片上绘制logo图片
        g.drawImage(logoImage, x, y, width, height, null);
        logoImage.flush();
        g.dispose();
        return image;
    }
    //将网络图片转base64
    public static String downloadFile(String urlPath){
        ByteArrayOutputStream data = new ByteArrayOutputStream();  
        try {
            // 打开图片路径
            URL url = new URL(urlPath);
            URLConnection connection = url.openConnection();
            // 通过输入流获取图片数据
            InputStream in = connection.getInputStream();
            byte[] by = new byte[1024];
            // 将内容读取内存中
            int len = -1;
            while ((len = in.read(by)) != -1) {
                data.write(by, 0, len);
            }
            //关闭流
            in.close();     
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回Base64编码过的字节数组字符串
        BASE64Encoder encoder = new BASE64Encoder();     
        return encoder.encode(data.toByteArray());
    }

}