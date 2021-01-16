package com.tjsj.wp.entity;

public class SmsSearchSuccessEntity {

private String code;
	
	private String msg;
	
	private MessageSearchDataEntity data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public MessageSearchDataEntity getData() {
		return data;
	}

	public void setData(MessageSearchDataEntity data) {
		this.data = data;
	}
	
	
}
