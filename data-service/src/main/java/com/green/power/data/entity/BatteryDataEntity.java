package com.green.power.data.entity;

import java.util.Date;

import org.bson.Document;

/**
 * 电池监控数据实体
 * @author Administrator
 *
 */
public class BatteryDataEntity {

	private long id;           //ID
	private String serialNum;    //序列号   与电池关联的标识
	private long recordTime;     //起始的记录次数
	private long recordCurcor;     //起始的记录游标
	private int dataLength;      //数据长度
	private Date clientReadTime;  //无用！！！
	private Date serverReadTime;  //无用！！！
	private String data;          //具体电池监控数据
	private String remark1;
	private String remark2;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}


	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public Date getClientReadTime() {
		return clientReadTime;
	}

	public void setClientReadTime(Date clientReadTime) {
		this.clientReadTime = clientReadTime;
	}

	public Date getServerReadTime() {
		return serverReadTime;
	}

	public void setServerReadTime(Date serverReadTime) {
		this.serverReadTime = serverReadTime;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	public static Document toDocument(String json){
		return Document.parse(json);
	}
	
	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}


	public long getRecordCurcor() {
		return recordCurcor;
	}

	public void setRecordCurcor(long recordCurcor) {
		this.recordCurcor = recordCurcor;
	}

	public static BatteryDataEntity toDataEntity(Document d){
		BatteryDataEntity data = new BatteryDataEntity();
//		data.setId(d.getObjectId("_id").toString());
		data.setSerialNum(d.getString("serialNum"));
		data.setRecordTime(d.getLong("recordTime"));
		data.setRecordCurcor(d.getLong("recordCuror"));
		data.setDataLength(d.getInteger("dataLength", 0));
//		data.setData(Base64.decodeFast(d.getString("data")));
		data.setClientReadTime(new Date(d.getLong("clientReadTime")));
		data.setServerReadTime(new Date(d.getLong("serverReadTime")));
		data.setRemark1(d.getString("remark1"));
		data.setRemark2(d.getString("remark2"));
		return data;
	}

}
