/**
 * @author gongdzh 
 * 2017年10月17日 上午9:02:28
 */
package com.tjsj.wp.mvc.view.label;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.avaje.ebean.Model.Find;
import com.avaje.ebean.PagedList;
import com.avaje.ebean.Query;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.wp.orm.entity.CmColumnTbl;
import com.tjsj.wp.orm.entity.SmAccessoryTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;

public class Cl{

	
	private SmWebSetTbl  webSet;
	
	public Cl(SmWebSetTbl  webSet){
		this.webSet = webSet;
	}
	
	/*
	 * 根据关键词获取栏目对象
	 */
	public  CmColumnTbl bk(String keyword) throws Exception{
		
		if(StringUtils.isBlank(keyword)||webSet==null){
			return null;
		}
		List<CmColumnTbl> columns = CmColumnTbl.find.where().eq("isDelete", -1).eq("keyword", keyword).eq("webSet", webSet).orderBy("sequence desc").findList();
		if(columns.size()<=0){
			return null;
		}
		return columns.get(0);
	}
	
	public static CmColumnTbl bi(int  id) throws Exception{
		if(id<=0){
			return null;
		}
		
	return CmColumnTbl.find.where().eq("id", id).findUnique();
	}
	
	public static CmColumnTbl bi(CmColumnTbl  column) throws Exception{
		if(column == null){
			return null;
		}
		
		return bi(column.getId());
	}
	
	public CmColumnTbl bi(String id) throws NumberFormatException, Exception{
		if(StringUtils.isBlank(id)){
			return null;
		}
		return bi(Integer.parseInt(id));
	}
	
	/*
	 * 获取站点的顶级目录
	 */
	public  List<CmColumnTbl> top(){
		if(webSet==null){
			return null;
		}
		return CmColumnTbl.find.where().eq("parent.id", null).eq("webSet", webSet).eq("isDelete", -1).orderBy("sequence asc,id asc").findList();
	}
//	获取栏目资料下载
	public PagedList<SmAccessoryTbl> bf(CmColumnTbl column,String pageIndex,Integer pageSize){
		if(column==null){
			return null;
		}
		PageParameter pp =new PageParameter(Integer.parseInt(pageIndex),pageSize);
		return SmAccessoryTbl.find.setUseQueryCache(true).where().in("columnList", column).eq("deleteStatus",0)
				.setFirstRow(pp.getFirstRow())
				.setMaxRows(pp.getMaxRows())
				.findPagedList();
	}
}