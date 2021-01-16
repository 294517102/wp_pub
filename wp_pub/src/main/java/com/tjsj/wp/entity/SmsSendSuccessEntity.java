package com.tjsj.wp.entity;

public class SmsSendSuccessEntity {

	private String code;
	
	private String msg;
	
	private MessageSendDataEntity data;

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

	public MessageSendDataEntity getData() {
		return data;
	}

	public void setData(MessageSendDataEntity data) {
		this.data = data;
	}
	
	
}
