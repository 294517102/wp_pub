package com.tjsj.base.entity;

import com.tjsj.base.constant.Const;

public class PageParameter {
	private Integer pageIndex;
	private Integer pageSize;
	
	public PageParameter() {

	}
	public PageParameter(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public PageParameter(Integer pageIndex, Integer pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public int  getFirstRow(){
		if(pageIndex==null||pageIndex<0){
			pageIndex = 1 ;
		}
		return (pageIndex-1)*(pageSize==null?Const.PAGE_SIZE:pageSize);
	}
	public int getMaxRows(){
		pageSize = pageSize==null?Const.PAGE_SIZE:pageSize;
		return pageSize;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
