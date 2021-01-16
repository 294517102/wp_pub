package com.tjsj.wp.mvc.controller.bn;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.entity.BaseEntity;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.BnOrderTbl;
import com.tjsj.wp.orm.entity.IfPiInterfaceTbl;
//订单处理controller
@Controller
public class OrderMaintenanceController extends WpBaseController {
	/**
	 * 查看当前站点没有删除的订单
	 * @param request
	 * @param response
	 * @param pageIndex 当前页
	 * @param url 需要跳转的url
	 * @return 返回到页面
	 */
	@RequestMapping("obtain_order_list")
	public ModelAndView obtain_order_list(HttpServletRequest request,HttpServletResponse response,Integer pageIndex,String url,PageParameter page){
		ModelAndView mv=createBgView(url, request, response);
		try{
			if(pageIndex==null) pageIndex=0;
			PagedList<BnOrderTbl> order = BnOrderTbl.find.where().eq("webSet", getWebSet(request)).eq("isDelete", -1).orderBy("insertTime desc").
					setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request, mv, order);
		}catch(Exception e){
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * 查询订单列表
	 * @param request
	 * @param response
	 * @param order
	 * @param page
	 * @param url
	 * @return
	 */
	@RequestMapping("obtain_order_list_like")
	public ModelAndView obtain_order_list(HttpServletRequest request, 
										  HttpServletResponse response,
										  BnOrderTbl order,
										  PageParameter page,
										  String url){
		ModelAndView mv = null;
		if(StringUtils.isNotBlank(url)){
			mv = createBgView(url, request, response);
		}else{
			mv = createBgView("business/order/order_list", request, response);
		}
		try {
			PagedList<BaseEntity> pageList = order.getLikeOrExpressionList().eq("webSet", getWebSet(request)).eq("isDelete", -1).setFirstRow(page.getFirstRow())
											.setMaxRows(page.getMaxRows()).orderBy("id desc").findPagedList();
			pagePrint(request, mv, pageList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * 对订单的修改和删除
	 * @param request
	 * @param response
	 * @param order 订单对象
	 */
	@RequestMapping("updata_state_order")
	public void updata_state_order(HttpServletRequest request,HttpServletResponse response,BnOrderTbl  order){
		ResultJson rj=new ResultJson();
		try{
			order.update();
			rj.setResult(true);
			rj.setResultMsg("操作成功");
			outJson(response, rj);
			return ;
		}catch(Exception e){
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg("处理异常");
			outJson(response, rj);
			return ;
		}
	}
	

		
}
