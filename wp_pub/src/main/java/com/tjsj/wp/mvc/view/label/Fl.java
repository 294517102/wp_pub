package com.tjsj.wp.mvc.view.label;

import java.util.List;

import com.avaje.ebean.Query;
import com.tjsj.base.constant.Const;
import com.tjsj.wp.orm.entity.CmFriendlyLinkTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;

public class Fl {
	
	private SmWebSetTbl  webSet;
	
	public Fl(SmWebSetTbl  webSet){
		this.webSet = webSet;
	}
	
	public  List<CmFriendlyLinkTbl> bs(Integer size){
		if(webSet==null){
			return null;
		}
		
		if(size==null||size<=0){
			size = Const.PAGE_SIZE;
		}
		return CmFriendlyLinkTbl.find.setUseQueryCache(true).where().eq("webSet", webSet).eq("isDelete", -1).order("sequence asc").setMaxRows(size).findList();
	}

	/*
	 * 获取友情链接分类
	 */
	public  List<CmFriendlyLinkTbl> top(){
		if(webSet==null){
			return null;
		}
		return CmFriendlyLinkTbl.find.where().eq("parent.id", null).eq("webSet", webSet).eq("isDelete", -1).orderBy("sequence asc,id asc").findList();
	}
}
