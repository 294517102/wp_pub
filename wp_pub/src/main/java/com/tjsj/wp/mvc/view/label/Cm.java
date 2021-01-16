package com.tjsj.wp.mvc.view.label;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.avaje.ebean.PagedList;
import com.tjsj.m_util.comm.CommUtil;
import com.tjsj.m_util.entity.BaseEntity;
import com.tjsj.wp.orm.entity.CmHomeSlideTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;

public class Cm {

	/*
	 * 项目 context path
	 */
	private String cp;
	
	/*
	 * 域名
	 */
	private String domain;
	
    private SmWebSetTbl  webSet;
	
	public Cm(String cp, SmWebSetTbl  webSet) {
		super();
		this.cp = cp;
		this.webSet = webSet;
		this.domain = webSet.getDomain();
	}

	
	public String getCp() {
		if(StringUtils.isNotBlank(cp)){
			cp = StringUtils.removeStart(cp, "/");
		}
		return webSet.getProtocol()+"://"+domain+"/"+cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}


	public List<CmHomeSlideTbl> getSs() {
		return CmHomeSlideTbl.find.setUseQueryCache(true).where()
															.eq("deleteStatus", -1)
															.eq("state", -1)
															.eq("webSet", webSet)
															.order("sequence asc")
															.findList();
	}
	public List<CmHomeSlideTbl> sl(String type) {
		return CmHomeSlideTbl.find.setUseQueryCache(true).where()
															.eq("deleteStatus", -1)
															.eq("state", -1).eq("type",type)
															.eq("webSet", webSet)
															.order("sequence asc")
															.findList();
	}
	/*
	 * 打印分页
	 */
	public String page(String url,Integer pageIndex,int totalPages){
		if(pageIndex==null||pageIndex<=0){
			pageIndex = 1;
		}
		return CommUtil.printPagesIndexConditionByHtml(url,pageIndex,totalPages);
	}
	
	public String page(String url,Integer pageIndex,PagedList<BaseEntity> pl){
		return page(url,pageIndex,pl.getTotalCount());
	}
	
	public String page(HttpServletRequest request,String pageIndex,PagedList<BaseEntity> pl){
		if(StringUtils.isBlank(pageIndex)){
			pageIndex = "1";
		}
		if(pl==null){
			return null;
		}

		return CommUtil.printPagesIndexConditionByHtml(request, Integer.parseInt(pageIndex)-1, pl.getTotalPageCount());
	}
}
