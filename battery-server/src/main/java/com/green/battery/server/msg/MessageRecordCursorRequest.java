package com.green.battery.server.msg;

public class MessageRecordCursorRequest extends AbstractMessage {
	
	private int count = 0;   //当存储游标大于记录次数时  记录请求次数
	private byte[] data;         //原始数据
	private String serialNum;  //电池序列号
	private int records;        //记录数
	private String status;   //身份验证信息  base64编码后数据

	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public byte getRequestType() {
		return TYPE_RECORD_REQUEST;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
