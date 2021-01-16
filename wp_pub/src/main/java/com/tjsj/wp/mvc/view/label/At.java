package com.tjsj.wp.mvc.view.label;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.avaje.ebean.Query;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model.Find;
import com.avaje.ebean.PagedList;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.wp.orm.entity.CmArticleTbl;
import com.tjsj.wp.orm.entity.CmColumnTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;



public class At {

	
	private SmWebSetTbl  webSet;
	
	
	public At(SmWebSetTbl  webSet) {
		this.webSet = webSet;
	}
	/*
	 * 根据栏目获取文章列表
	 */
	public  List<CmArticleTbl> bc(CmColumnTbl column){
		if(column==null){
			return null;
		}
		
		return bc( column.getId());
	}
	public  List<CmArticleTbl> bc(int column_id){
		if(column_id<=0){
			return null;
		}
		
		return CmArticleTbl.find.setUseQueryCache(true).where() 
												.eq("column.id", column_id)
												.eq("isDelete", "-1").eq("state", "1")
												.orderBy("isHome desc, sequence desc,issueTime desc ,updateTime desc ")
												.findList();
	}
	public  List<CmArticleTbl> bpc(int column_id){
		if(column_id<=0){
			return null;
		}
		CmColumnTbl column=CmColumnTbl.find.byId(Integer.valueOf(column_id));
		Set<CmColumnTbl> clist=column.getChildren();
		Set<CmColumnTbl> columnlist=new HashSet<>();
		for (CmColumnTbl c : clist) {
			if(c.getChildren().size()>0){
				columnlist.addAll(c.getChildren());
			}
		}
		columnlist.addAll(clist);
		return CmArticleTbl.find.setUseQueryCache(true).where() 
												.eq("isDelete", "-1").eq("state", "1")
												.in("column",columnlist)
												.orderBy("isHome desc,issueTime desc ,sequence desc,updateTime desc ")
												.findList();
	}
	
	public PagedList<CmArticleTbl> bc(int column_id,Integer pageIndex,Integer pageSize){
		if(column_id<=0){
			return null;
		}
		
		PageParameter pp =new PageParameter(pageIndex,pageSize);
		return CmArticleTbl.find.setUseQueryCache(true).where()
															.eq("column.id", column_id).eq("isDelete", "-1").eq("state", "1")
															.orderBy("issueTime desc,updateTime desc ")
															.setFirstRow(pp.getFirstRow())
															.setMaxRows(pp.getMaxRows())
															.findPagedList();
	}

	
	public PagedList<CmArticleTbl> bc(CmColumnTbl column,Integer pageIndex,Integer pageSize){
		if(column==null){
			return null;
		}
		return bc(column.getId(),pageIndex,pageSize);
	}
	
	public PagedList<CmArticleTbl> bc(CmColumnTbl column,String pageIndex,Integer pageSize){
		if(column==null||pageIndex==null){
			return null;
		}
		
		return bc(column.getId(),Integer.valueOf(pageIndex),pageSize);
	}
	public PagedList<CmArticleTbl> bcimg(Integer column_id,String pageIndex,Integer pageSize){
		ExpressionList<CmArticleTbl> exp=CmArticleTbl.find.setUseQueryCache(true).where().eq("isDelete", "-1").eq("state", "1");
		PageParameter pp =new PageParameter(Integer.valueOf(pageIndex),pageSize);
		if(column_id>0) {
			CmColumnTbl column=CmColumnTbl.find.byId(Integer.valueOf(column_id));
			Set<CmColumnTbl> clist=column.getChildren();
			Set<CmColumnTbl> columnlist=new HashSet<>();
			for (CmColumnTbl c : clist) {
				if(c.getChildren().size()>0){
					columnlist.addAll(c.getChildren());
				}
			}
			columnlist.addAll(clist);
			columnlist.add(column);
			exp.in("column",columnlist);
			System.out.println("====column========column==");
		}
		return 	exp.isNotNull("thumbnail")
				.orderBy("isHome desc, sequence desc, issueTime desc,updateTime desc")
				.setFirstRow(pp.getFirstRow())
				.setMaxRows(pp.getMaxRows())
				.findPagedList();
	}	
	/*
	 * 根据栏目获取文章列表
	 */
	public  List<CmArticleTbl> hot(CmColumnTbl column){
		if(column==null){
			return null;
		}
		
		return hot( column.getId());
	}
	public  List<CmArticleTbl> hot(int column_id){
		if(column_id<=0){
			return null;
		}
		
		return CmArticleTbl.find.setUseQueryCache(true).where() 
												.eq("column.id", column_id)
												.eq("isDelete", "-1").eq("state", "1")
												.orderBy("viewTimes desc")
												.findList();
	}
	
	
	public PagedList<CmArticleTbl> bchp(Integer column_id,String hot,String pageIndex,Integer pageSize){
		ExpressionList<CmArticleTbl> exp=CmArticleTbl.find.setUseQueryCache(true).where().eq("isDelete", "-1").eq("state", "1").eq("webSet", this.webSet);
		if(column_id>0) {
			if(column_id>0) {
				CmColumnTbl column=CmColumnTbl.find.byId(Integer.valueOf(column_id));
				Set<CmColumnTbl> clist=column.getChildren();
				Set<CmColumnTbl> columnlist=new HashSet<>();
				for (CmColumnTbl c : clist) {
					if(c.getChildren().size()>0){
						columnlist.addAll(c.getChildren());
					}
				}
				columnlist.addAll(clist);
				columnlist.add(column);
				exp.in("column",columnlist);
				System.out.println("====column========column==");
			}
		}
		if(StringUtils.isNotBlank(hot)){
			exp.eq("isHome",hot);
		}
		PageParameter pp =new PageParameter(Integer.valueOf(pageIndex),pageSize);
		return exp.orderBy("issueTime desc,updateTime desc ")
				.setFirstRow(pp.getFirstRow())
				.setMaxRows(pp.getMaxRows())
				.findPagedList();
	}	
	/**
	 * 获取热门图片
	 * @param column
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PagedList<CmArticleTbl> bch(CmColumnTbl column,String hot,String pageIndex,Integer pageSize){
		if(column==null){
			return null;
		}
		return bch(column.getId(),hot,pageIndex,pageSize);
	}

	public PagedList<CmArticleTbl> bch(int column_id,String hot,String pageIndex,Integer pageSize){
		
		ExpressionList<CmArticleTbl> expression=CmArticleTbl.find.setUseQueryCache(true).where().eq("column.id", column_id).eq("isDelete", "-1").eq("state", "1");
		if(column_id>0) {
		   expression.eq("column.id",column_id);
		}
		if(StringUtils.isNotBlank(hot)){
		   expression.eq("isHome",hot);
		}
		PageParameter pp =new PageParameter(Integer.valueOf(pageIndex),pageSize);
		return expression.orderBy("isHome desc, sequence desc, issueTime desc,updateTime desc ")
				.setFirstRow(pp.getFirstRow())
				.setMaxRows(pp.getMaxRows())
				.findPagedList();
	}

	/*
	 * 获取单篇文章
	 */
	public  CmArticleTbl bi(int id){
		if(id<=0){
			return null;
		}
		
		return CmArticleTbl.find.setUseQueryCache(true).where().eq("id", id).findUnique();
	}

	public CmArticleTbl bi(String id){
		if(StringUtils.isBlank(id)){
			return null;
		}
		return bi(Integer.parseInt(id));
	}
	
	public  CmArticleTbl bi(CmArticleTbl article){
		if(article == null){
			return null;
		}
		
		return bi(article.getId());
	}
	
	public Integer pv(int id){
		CmArticleTbl article= CmArticleTbl.find.setUseQueryCache(true).where().eq("id", id).findUnique();
		Integer view=article.getViewTimes()+1;
		article.setViewTimes(view);//设置文章浏览次数
		article.update();
		return article.getViewTimes();
	}
	
	/*
	 * 前一篇文章
	 */
	public CmArticleTbl prev(CmArticleTbl at){
		if(at == null){
			return null;
		}
		List<CmArticleTbl> ats = bc(at.getColumn());
		CmArticleTbl prev = null;
		for (CmArticleTbl a : ats) {		
			if(a.getId()==at.getId()){
				break;
			}else{
				prev = a;
			}			
		}
		return  prev ;
	}
	/*
	 * 后一篇文章
	 */
	public CmArticleTbl next(CmArticleTbl at){
		if(at == null){
			return null;
		}
		List<CmArticleTbl> ats = bc(at.getColumn());
		CmArticleTbl next = null;
		for (int i = 0; i < ats.size(); i++) {
			
			if(ats.get(i).getId()==at.getId()&&i+1<ats.size()){
				next = ats.get(i+1);
				break;
			}
		}
		return  next ;
	}
	
	public PagedList<CmArticleTbl> sr(String key,String pageIndex,Integer pageSize){
		if(StringUtils.isBlank(key)){
			return null;
		}
		if(StringUtils.isBlank(pageIndex)){
			pageIndex = "1";
		}
		PageParameter pp =new PageParameter(Integer.parseInt(pageIndex),pageSize);
		return CmArticleTbl.find.setUseQueryCache(true).where()
											.eq("webSet", this.webSet)
											.or()
											.like("title", "%"+key+"%")
										//	.like("describes", "%"+key+"%")
											.endJunction()
											.eq("isDelete", "-1").eq("state", "1")
											.orderBy("isHome desc, sequence desc,issueTime desc ,updateTime desc")
											.setFirstRow(pp.getFirstRow())
											.setMaxRows(pp.getMaxRows())
											.findPagedList();
	}
	
	public PagedList<CmArticleTbl> sr(String key, Integer column,String pageIndex,Integer pageSize){
		if(StringUtils.isBlank(key)){
			return null;
		}
		if(StringUtils.isBlank(pageIndex)){
			pageIndex = "1";
		}
		ExpressionList<CmArticleTbl> exp=CmArticleTbl.find.setUseQueryCache(true).where().eq("webSet", this.webSet).eq("isDelete", "-1").eq("state", "1");
		
		//zp修改   只支持三级菜单。后面修改次逻辑
		CmColumnTbl columns=CmColumnTbl.find.byId(Integer.valueOf(column));
		Set<CmColumnTbl> clist=columns.getChildren();
		Set<CmColumnTbl> columnlist=new HashSet<>();
		for (CmColumnTbl c : clist) {
			if(c.getChildren().size()>0){
				columnlist.addAll(c.getChildren());
			}
		}
		columnlist.addAll(clist);
		PageParameter pp =new PageParameter(Integer.parseInt(pageIndex),pageSize);
		if(columnlist.size()>0) {
			exp.in("column", columnlist);
		}
		if(StringUtils.isNotBlank(key)) {
			exp.like("title", "%"+key+"%");
		}
	PagedList<CmArticleTbl> plist= exp.orderBy("isHome desc, sequence desc,issueTime desc,insertTime desc ,updateTime desc")
											.setFirstRow(pp.getFirstRow())
											.setMaxRows(pp.getMaxRows())
											.findPagedList();
	return plist;
	}
	
	public PagedList<CmArticleTbl> srbyc(String key, Integer column,String pageIndex,Integer pageSize){
		if(StringUtils.isBlank(key)){
			return null;
		}
		if(StringUtils.isBlank(pageIndex)){
			pageIndex = "1";
		}
		ExpressionList<CmArticleTbl> exp=CmArticleTbl.find.setUseQueryCache(true).where().eq("webSet", this.webSet).eq("isDelete", "-1").eq("state", "1");
		
		//zp修改   只支持三级菜单。后面修改次逻辑
		CmColumnTbl columns=CmColumnTbl.find.byId(Integer.valueOf(column));
	
		if(columns!=null) {
			exp.eq("column", columns);
		}
		if(StringUtils.isNotBlank(key)) {
			exp.like("title", "%"+key+"%");
		}
		PageParameter pp =new PageParameter(Integer.parseInt(pageIndex),pageSize);
	PagedList<CmArticleTbl> plist= exp.orderBy("isHome desc, sequence desc,issueTime desc,insertTime desc,updateTime desc")
											.setFirstRow(pp.getFirstRow())
											.setMaxRows(pp.getMaxRows())
											.findPagedList();
	return plist;
	}	
	
	
	//查询栏目 及下级栏目 文章
	public PagedList<CmArticleTbl> bcac(CmColumnTbl column,String pageIndex,Integer pageSize){
		if(StringUtils.isBlank(pageIndex)){
			pageIndex = "1";
		}
		//zp修改   只支持三级菜单。后面修改次逻辑
		CmColumnTbl columns=CmColumnTbl.find.byId(column.getId());
		Set<CmColumnTbl> clist=columns.getChildren();
		Set<CmColumnTbl> columnlist=new HashSet<>();
		for (CmColumnTbl c : clist) {
			if(c.getChildren().size()>0){
				columnlist.addAll(c.getChildren());
			}
		}
		columnlist.addAll(clist);
		columnlist.add(columns);
		PageParameter pp =new PageParameter(Integer.parseInt(pageIndex),pageSize);
		return CmArticleTbl.find.setUseQueryCache(true).where()
											.eq("webSet", this.webSet)
											.in("column", columnlist)
											.eq("isDelete", "-1").eq("state", "1")
											.orderBy("issueTime desc,insertTime desc ,updateTime desc")
											.setFirstRow(pp.getFirstRow())
											.setMaxRows(pp.getMaxRows())
											.findPagedList();
	}
	
	//指定栏目查询文章
		public PagedList<CmArticleTbl> bcac(String key,String ids,String pageIndex,Integer pageSize){
			if(StringUtils.isBlank(key)){
				return null;
			}
			if(StringUtils.isBlank(ids)){
				return null;
			}
			if(StringUtils.isBlank(pageIndex)){
				pageIndex = "1";
			}
			//zp修改   只支持三级级菜单。后面修改次逻辑
			Set<CmColumnTbl> columnlist=new HashSet<>();
			for(String id: ids.split(",")){
				CmColumnTbl columns=CmColumnTbl.find.byId(Integer.valueOf(id));
				Set<CmColumnTbl> clist=columns.getChildren();
				for (CmColumnTbl c : clist) {
					if(c.getChildren().size()>0){
						columnlist.addAll(c.getChildren());
					}
				}
			columnlist.addAll(clist);
			columnlist.add(columns);
			}
			PageParameter pp =new PageParameter(Integer.parseInt(pageIndex),pageSize);
			return CmArticleTbl.find.setUseQueryCache(true).where()
												.eq("webSet", this.webSet)
												.in("column", columnlist)
												.or()
												.like("title", "%"+key+"%")
												.endJunction()
												.eq("isDelete", "-1").eq("state", "1")
												.orderBy("isHome desc, sequence desc,issueTime desc,insertTime desc ,updateTime desc")
												.setFirstRow(pp.getFirstRow())
												.setMaxRows(pp.getMaxRows())
												.findPagedList();
		}
	
	public PagedList<CmArticleTbl> sr(String key, CmColumnTbl column,String pageIndex,Integer pageSize){
		if(column==null){
			return null;
		}
		return  sr(key,column.getId(),pageIndex,pageSize);
	}
	
	public PagedList<CmArticleTbl> bco(int column_id,Integer pageIndex,Integer pageSize,String orderby){
		if(column_id<=0){
			return null;
		}
		PageParameter pp =new PageParameter(pageIndex,pageSize);
		
		return CmArticleTbl.find.setUseQueryCache(true).where()
				.eq("column.id", column_id).eq("isDelete", "-1").eq("state",1)
				.orderBy(orderby)
				.setFirstRow(pp.getFirstRow())
				.setMaxRows(pp.getMaxRows())
				.findPagedList();
	}
	public PagedList<CmArticleTbl> bco(CmColumnTbl column,String pageIndex,Integer pageSize,String orderby){
		if(column==null||pageIndex==null){
			return null;
		}
		return bco(column.getId(),Integer.valueOf(pageIndex),pageSize,orderby);
		
	}
}
