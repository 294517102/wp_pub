package com.tjsj.wp.mvc.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.wp.thread.TailLogThread;



@ServerEndpoint("/admin/log/{file}")
@Component
public class LogWebSocketHandle {
	
	private static Logger logger = LoggerFactory.getLogger(LogWebSocketHandle.class);
	
	private Process process;
	private InputStream inputStream;
	
	/**
	 * 新的WebSocket请求开启
	 * @throws IOException 
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config,@PathParam("file")String file) throws IOException {
		try {
			// 执行tail -f命令
		
			 String os = System.getProperty("os.name");  
			 ResultJson  rj = new ResultJson();
			/* if(os == null || os.toLowerCase().indexOf("linux") <= -1){  
				
				 rj.setResult(false);
				 rj.setResultMsg("运行环境非linux系统");
				 session.getBasicRemote().sendText(JSON.toJSONString(rj));
			    throw new RuntimeException("运行环境非linux系统");  
			 } */
			 if(StringUtils.isBlank(file)){
				 rj.setResult(false);
				 rj.setResultMsg("请求非法");
				 session.getBasicRemote().sendText(JSON.toJSONString(rj));
			    throw new RuntimeException("请求非法");  
			 }
			 String cmd = "tail -60f logs/";
			 if(file.equalsIgnoreCase("console")){
				 cmd += "ConsoleInfo.log";
			 }
			 if(file.equalsIgnoreCase("error")){
				 cmd += "ErrorInfo.log";
			 }
			//logger.info("执行命令："+cmd);
			process = Runtime.getRuntime().exec(cmd);
			inputStream = process.getInputStream();
			
			// 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
			TailLogThread thread = new TailLogThread(inputStream, session);
			thread.start();
		} catch (Exception e) {
			session.close();
			//logger.error("exception throws:", e);
		}
	}
	
	/**
	 * WebSocket请求关闭
	 */
	@OnClose
	public void onClose() {
		try {
			if(inputStream != null)
				inputStream.close();
		} catch (Exception e) {
			//logger.error("exception throws:", e);
		}
		if(process != null)
			process.destroy();
	}
	
	@OnError
	public void onError(Throwable thr) {
		logger.error("exception throws:",thr);
	}
}
