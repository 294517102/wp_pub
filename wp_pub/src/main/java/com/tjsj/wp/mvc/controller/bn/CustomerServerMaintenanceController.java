package com.tjsj.wp.mvc.controller.bn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.CmCustomerServiceTbl;
import com.tjsj.wp.orm.entity.CmSysLogTbl;
/**
 * 在线客服管理
 * @author zp
 *
 */
@Controller
public class CustomerServerMaintenanceController extends WpBaseController {
	/**
	 * 根据id改变留言状态
	 * @param request
	 * @param response
	 * @param url
	 * @param id
	 * @return
	 */
	@RequestMapping("admin/cusertomer_server_state")
	public ModelAndView operationById(HttpServletRequest request,HttpServletResponse response,String id,int judge){
		ModelAndView mv = createView("", request, response);
		try{
			CmCustomerServiceTbl cs = CmCustomerServiceTbl.find.byId(Integer.parseInt(id));
			if(cs!=null){
				if(judge==1){
					cs.delete();
					//记录日志
					CmSysLogTbl log=new CmSysLogTbl();
					log.setoPerator(getLoginUser());
					log.setType(2);
					log.setTableName("cm_customer_service_tbl");
					log.setRemark("删除客服代码"+cs.getId());
					log.save();
					addResult(mv, true, "删除成功!");
					return mv;
				}else{
					if(cs.getState()==0)cs.setState(1);
					else cs.setState(-1);
					cs.update();
					addResult(mv, true, "操作成功!");
					return mv;
				}
			}
		}catch(Exception e){
			addResult(mv, false, "操作失败!");
		}
		return mv;
	}
}
