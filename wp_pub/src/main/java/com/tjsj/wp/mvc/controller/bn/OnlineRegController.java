package com.tjsj.wp.mvc.controller.bn;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.exception.ParameterException;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.BnJlinfoTbl;
import com.tjsj.wp.tools.ImportExcelUtil;

@Controller
public class OnlineRegController extends WpBaseController {

	
	

	@RequestMapping("testreds")
	public void testReds(HttpServletRequest request, HttpServletResponse response) {
		ResultJson rj = new ResultJson();
		try {

			File file = new File("F:\\Tencent\\QQ\\Tencent Files\\2962554761\\FileRecv\\学员合格成绩9.29（传冯军）.xls");
			ImportExcelUtil a = new ImportExcelUtil();
			List<Map<String, Object>> dataList = a.importExcel(file);
			for (Map<String, Object> map : dataList) {
				JSONObject obj = new JSONObject(map);
				BnJlinfoTbl t = JSON.parseObject(obj.toString(), BnJlinfoTbl.class);
				t.setIsDelete(9);
				if(t.getStrSex().equals("男")) {
					t.setSex(1);
				}
				if(t.getStrSex().equals("女")) {
					t.setSex(2);
				}
				t.setWebSet(getWebSet(request));
				System.out.println(t.toString());
				t.save();
			}
			rj.setResult(true);
			rj.setResultMsg("读取成功");
		} catch (Exception e) {
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
		}
		outJson(response, rj);
	}

}
