package com.tjsj.wp.mvc.view.label;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tjsj.wp.orm.entity.CmSmallContentTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;

public class Sc {

	private SmWebSetTbl  webSet;
	
	public Sc(SmWebSetTbl  webSet){
		this.webSet = webSet;
	}
	
	/*
	 * 根据关键字获取小资料集合
	 */
	public  List<CmSmallContentTbl> bks(String keyword){
		if(StringUtils.isBlank(keyword)||webSet==null){
			return null;
		}
		return CmSmallContentTbl.find.setUseQueryCache(true).where().eq("isDelete", -1).eq("keyword", keyword).or()
									.eq("webSet", webSet)
									.eq("webSet", null).endJunction().findList();
	}
	
	/*
	 * 根据关键字获取小资料
	 */
	public  CmSmallContentTbl bk(String keyword){
		
		List<CmSmallContentTbl> scs =  bks( keyword);
		if(scs.size()<=0){
			return null;
		}
		return scs.get(0);
	}
	
}
