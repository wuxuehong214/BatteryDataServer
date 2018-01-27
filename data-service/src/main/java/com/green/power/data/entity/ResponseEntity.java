package com.green.power.data.entity;

public class ResponseEntity<T> {
	
	public static final int CODE_SUCCESS = 1;
	public static final int CODE_FAIL = 0;

	private int code = 0;;
	private String msg = "nothing";
	private T data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
