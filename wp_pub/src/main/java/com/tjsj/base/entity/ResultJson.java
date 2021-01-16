

package com.tjsj.base.entity;

import com.alibaba.fastjson.JSON;
import com.tjsj.m_util.json.FastjsonPropertyPreFilter;

/**
 * json数据返回数据格式
 */
public class ResultJson {

	/**
	 * 模块编号
	 */
	private String code;
	
	/**
	 * 返回状态:true成功 false:失败
	 */
	private boolean result;
	
	/**
	 * 返回信息提示
	 */
	private String resultMsg;
	
	
	/**
	 * 返回数据
	 */
	private Object resultData;

	/**
	 * 返回后跳的地址
	 */
	private String url;
//	返回的提示信息
	private String info;
	/**
	 * 返回的状态码 
	 */
	private String status;
	
	private Integer iCode;
	
	
	private Object data;
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public void setData(String data) {	
		this.data = JSON.parse(data);
	}

	public Integer getiCode() {
		return iCode;
	}

	public void setiCode(Integer iCode) {
		this.iCode = iCode;
	}

	/**
	 * 获取后跳的地址
	 * @return 返回后跳的地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置后跳的地址
	 * @param url 后跳地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取模块编号
	 * @return 返回模块编号
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置模块编号
	 * @param code 模块编号
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取返回状态
	 * @return 返回状态
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * 设置返回状态
	 * @param result 返回状态
	 */
	public void setResult(boolean result) {
		if(result){
			code = "0";
		}else{
			code = "-1";
		}
		this.result = result;
	}

	/**
	 * 设置返回信息提示
	 * @return 返回提示信息
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * 设置返回信息提示
	 * @param resultMsg 返回提示信息
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}
	
	

	
}