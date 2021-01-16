package com.tjsj.wp.tools;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.cache.ServerCacheManager;

public class L2CacheManagerService {

	 private static  ServerCacheManager serverCacheManager = Ebean.getServerCacheManager();
	
	 public static void clear(Class<?> classz){
		 serverCacheManager.clear(classz); 
	 }
	 
	 public static void clearAll(){
		 serverCacheManager.clearAll();
	 }
}
