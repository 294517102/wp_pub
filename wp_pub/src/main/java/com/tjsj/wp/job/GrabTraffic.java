package com.tjsj.wp.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tjsj.wp.orm.entity.SmSiteSkimTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;
/**
 * 抓取网站浏览量信息
 * @author andrew_silence
 * @date 2017年10月7日 下午4:22:13
 * @version V1.0
 */
@Component
public class GrabTraffic {

	private static Logger  logger = LoggerFactory.getLogger(GrabTraffic.class);
	
	
//	@Scheduled(cron="0 0/2 * * * ?")  //每隔两分钟
//	@Scheduled(cron="0 0 12 * * ?") //每天中午12点触发
	
//	@Scheduled(cron="0 0 */1 * * ?") //每天隔一小时触发
	
	@Scheduled(cron="0 0/10 * * * ?")  //每隔两分钟
	public void totalSkim(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		System.err.println("启动");
		List<SmWebSetTbl> setlist=SmWebSetTbl.find.all();
		for (SmWebSetTbl smWebSetTbl : setlist) {
			SmSiteSkimTbl skim=getskim(smWebSetTbl);
			if(skim!=null){
				SmSiteSkimTbl dayskim;
				List<SmSiteSkimTbl> dayskimList=SmSiteSkimTbl.find.where().gt("insertTime",sdf.format(new Date())).eq("webSet", smWebSetTbl).orderBy("insertTime desc").findList();
			if(dayskimList!=null && dayskimList.size()>0){
				 dayskim=dayskimList.get(0);
				 dayskim.setInsertTime(new Date());
				 dayskim.setPvSkim(skim.getPvSkim());
				 dayskim.setUvSkim(skim.getUvSkim());
				 dayskim.update();
			}else{
				skim.setWebSet(smWebSetTbl);
				skim.save();
			}
				
			}
		}
		
	}
	
	
	private SmSiteSkimTbl getskim(SmWebSetTbl webset){
		SmSiteSkimTbl skim=null;
		try {
			URL url = new URL("http://online.cnzz.com/online/online_v3.php?id="+webset.getSiteId()+"&h=z1.cnzz.com&on=1&s=line");
			HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();  
			InputStream is = httpUrl.getInputStream();  
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));  
			StringBuilder sb = new StringBuilder();  
			String line;  
			while ((line = br.readLine()) != null) {  
				//这里是对链接进行处理  
				line = line.replaceAll("</?a[^>]*>", "");  
				//这里是对样式进行处理  
				line = line.replaceAll("<(\\w+)[^>]*>", "<$1>");  
				sb.append(line);  
			}  
			
			  String pv= org.apache.commons.lang3.StringUtils.substringBetween(sb.toString().trim(), "今日IP[", "]");
		       String uv= org.apache.commons.lang3.StringUtils.substringBetween(sb.toString().trim(), "今日PV[", "]");
//		      System.out.println("pv:"+pv);
//		      System.out.println("uv："+uv);
		      if(StringUtils.isNotBlank(pv)&&StringUtils.isNotBlank(uv)){
		    	  skim=new SmSiteSkimTbl();
		    	  skim.setInsertTime(new Date());
//		       今日IP[3] | 今日PV[507] | 昨日IP[3]
		    	  skim.setPvSkim(pv);
		    	  skim.setUvSkim(uv);
		      }
			
			is.close();  
			br.close();  
		} catch ( IOException e) {
			e.printStackTrace();
		}
		return skim;  
	}

	public static void main(String[] args) {
		try {
			String sited="1274015557";
			URL url = new URL("http://online.cnzz.com/online/online_v3.php?id="+sited+"&h=z1.cnzz.com&on=1&s=line");
			HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();  
			InputStream is = httpUrl.getInputStream();  
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));  
			StringBuilder sb = new StringBuilder();  
			String line;  
			while ((line = br.readLine()) != null) {  
				//这里是对链接进行处理  
				line = line.replaceAll("</?a[^>]*>", "");  
				//这里是对样式进行处理  
				line = line.replaceAll("<(\\w+)[^>]*>", "<$1>");  
				sb.append(line);  
			}  
			System.out.println("得到的数据"+sb.toString());
			  String pv= org.apache.commons.lang3.StringUtils.substringBetween(sb.toString().trim(), "今日IP[", "]");
		       String uv= org.apache.commons.lang3.StringUtils.substringBetween(sb.toString().trim(), "今日PV[", "]");
//		      System.out.println("pv:"+pv);
//		      System.out.println("uv："+uv);
			
			is.close();  
			br.close();  
		} catch ( IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
