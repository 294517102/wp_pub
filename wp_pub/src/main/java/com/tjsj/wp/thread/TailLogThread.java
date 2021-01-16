package com.tjsj.wp.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.wp.mvc.websocket.LogWebSocketHandle;

/*
 * 日志输出线程
 */
public class TailLogThread extends Thread {
		
	private BufferedReader reader;
	private Session session;
	
	public TailLogThread(InputStream in, Session session) {
		this.reader = new BufferedReader(new InputStreamReader(in));
		this.session = session;
		
	}
	
	@Override
	public void run() {
		String line;

		try {
			ResultJson rj = new ResultJson();
			while((line = reader.readLine()) != null) {
				// 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
				rj.setResult(true);
				rj.setResultData(line);
				
				session.getBasicRemote().sendText(JSON.toJSONString(rj)); 
			
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}

	}
}
