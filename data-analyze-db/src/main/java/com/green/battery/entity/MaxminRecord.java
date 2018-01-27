package com.green.battery.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 最值记录：
 * 电池最大充电电流
 * 电池最大放电电流
 * 电池最高电压
 * 电池最低电压
 * 电池最高温度
 * 电池最低温度
 * 
 * @author Administrator
 *
 */
public class MaxminRecord {
	
	public static final String TYPE_MAX_DL = "MAX_DL";
	public static final String TYPE_MIN_DL = "MIN_DL";
	public static final String TYPE_MAX_DY = "MAX_DY";
	public static final String TYPE_MIN_DY = "MIN_DY";
	public static final String TYPE_MAX_WD = "MAX_WD";
	public static final String TYPE_MIN_WD = "MIN_WD";
	
	
	private long id;
	private long taskId;
	private Date startTime;
	private Date endTime;
	private int continus;
	private String type;
	private float value = 0f;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String getStartTimes(){
		if(startTime != null)
			return sdf.format(startTime);
		return "";
	}
	
	public String getEndTimes(){
		if(endTime != null)
			 return sdf.format(endTime);
		return "";
	}
	
	
	private String valueDescp;
	private String typeDescp;
	private String continuesDescp;
	
	public String getTypeDescp(){
		if(typeDescp != null) return typeDescp;
		if(TYPE_MAX_DL.endsWith(type)) return "电池最大充电电流";
		else if(TYPE_MIN_DL.endsWith(type)) return "电池最大放电电流";
		else if(TYPE_MAX_DY.equals(type)) return "电池最高电压";
		else if(TYPE_MIN_DY.equals(type)) return "电池最低电压";
		else if(TYPE_MAX_WD.equals(type)) return "电池最高温度";
		else if(TYPE_MIN_WD.equals(type)) return "电池最低温度";
		return "未知";
	}
	
	public String getValueDescp(){
		if(TYPE_MAX_DL.equals(type) || TYPE_MIN_DL.equals(type)) return (int)value+"mA";
		else if(TYPE_MAX_DY.equals(type) || TYPE_MIN_DY.equals(type)) return (int)value+"mV";
		else if(TYPE_MAX_WD.equals(type) || TYPE_MIN_WD.equals(type)) return value+"C";
		return "未知";
	}
	
	public String getContinuesDescp(){
		int hour = continus/3600;
		StringBuffer sb = new StringBuffer();
		if(hour>0) sb.append(hour+"h");
		int times = continus%3600;
		int mintute = times/60;
		if(mintute>0) sb.append(mintute+"m");
		sb.append(times%60+"s");
		return sb.toString();
	}

	public MaxminRecord(){
		
	}
	
	public MaxminRecord(long taskId, String type){
		this.taskId = taskId;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getContinus() {
		return continus;
	}

	public void setContinus(int continus) {
		this.continus = continus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public void setValueDescp(String valueDescp) {
		this.valueDescp = valueDescp;
	}

	public void setTypeDescp(String typeDescp) {
		this.typeDescp = typeDescp;
	}

	public void setContinuesDescp(String continuesDescp) {
		this.continuesDescp = continuesDescp;
	}
	
}

