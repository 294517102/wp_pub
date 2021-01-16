/**
 * 
 */
package com.tjsj.wp.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author andrew-silence
 *
 */
public class PhantomTools {
	 private static String tempPath = "C:\\Users\\andrew-silence\\Desktop";// 图片保存目录
	    private static String BLANK = " ";
	    // 下面内容可以在配置文件中配置
	    private static String binPath = "F:\\chromeDownLoad\\phantomjs-2.5.0-beta2-windows\\bin\\phantomjs.exe";// 插件引入地址
	    private static String jsPath = "F:\\chromeDownLoad\\phantomjs-2.5.0-beta2-windows\\examples\\rasterize.js";// js引入地址

	    // 执行cmd命令
	    public static String cmd(String imgagePath, String url) {
	        return binPath + BLANK + jsPath + BLANK + url + BLANK + imgagePath;
	    }
	    //关闭命令
	    public static void close(Process process, BufferedReader bufferedReader) throws IOException {
	        if (bufferedReader != null) {
	            bufferedReader.close();
	        }
	        if (process != null) {
	            process.destroy();
	            process = null;
	        }
	    }
	    /**
	     * @param userId 
	     * @param url
	     * @throws IOException 
	     */
	    public static void printUrlScreen2jpg(String url) throws IOException{
	        String imgagePath = tempPath+"/"+System.currentTimeMillis()+".png";//图片路径
	        //Java中使用Runtime和Process类运行外部程序
	        Process process = Runtime.getRuntime().exec(cmd(imgagePath,url));
	        InputStream inputStream = process.getInputStream();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	        String tmp = "";
	        while ((tmp = reader.readLine()) != null) {
	            close(process,reader);
	        }
	        System.out.println("success");
	    }
	    
	    public static void main(String[] args) throws IOException {
	        String url = "https://t.xsz.tjsjnet.com/";//以百度网站首页为例
	        PhantomTools.printUrlScreen2jpg(url);
	    }
}
