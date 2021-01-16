package com.tjsj.wp.tools;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.avaje.ebean.EbeanServer;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.CmSysLogTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;

public class SystemLogService  {
	//日志文件
		private static Logger logger = Logger.getLogger(SystemLogService.class);
		
		
		/**
		 * 写入系统日志
		 * @param tableName	操作的表名
		 * @param type	1 修改  2新增  3删除
		 * @param oPerator_id	操作用户id
		 * @param remark	备注信息
		 * @return
		 */
		public static boolean SaveLogToDB(String tableName,Integer type,Integer oPerator_id,String remark){
			
			if(StringUtil.isBlank(tableName)||
					StringUtil.isBlank(oPerator_id)||
					StringUtil.isBlank(type)||
					StringUtil.isBlank(remark)){
				return false;
			}
			if(StringUtil.isBlank(SmUserTbl.find.byId(oPerator_id))){
				return false;
			}
			CmSysLogTbl syslog=new CmSysLogTbl();
			SmWebSetTbl webset=SmUserTbl.find.byId(oPerator_id).getWebSet();
			if(!StringUtil.isBlank(webset)){
				syslog.setWebset(webset);
			}
			syslog.setoPerator(SmUserTbl.find.byId(oPerator_id));
			syslog.setTableName(tableName);
			syslog.setType(type);
			syslog.setRemark(remark);
			syslog.save();
			logger.info("SAVE LOG&"+syslog.getId());
			return true;
		}
}
