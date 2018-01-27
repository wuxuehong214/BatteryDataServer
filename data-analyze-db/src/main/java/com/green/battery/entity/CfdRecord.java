package com.green.battery.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 充放电记录
 * @author Administrator
 *
 */
public class CfdRecord {
	
	public static final String  TYPE_CD = "CD";
	public static final String TYPE_FD ="FD";
	public static final String TYPE_WCD = "WCD";
	
	private long id;
	private String type;
	private Date startTime;
	private Date endTime;
	private int continus; //持续时间  seconds
	private long taskId;
	private Date lastSecond; //倒数第二条记录时间  防止数据截断
	
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
	
	private String dateDesc;
	
	public String getDateDesc(){
		int hour = continus/3600;
		StringBuffer sb = new StringBuffer();
		if(hour>0) sb.append(hour+"h");
		int times = continus%3600;
		int mintute = times/60;
		if(mintute>0) sb.append(mintute+"m");
		sb.append(times%60+"s");
		return sb.toString();
	}
	
	public void setDateDesc(String dateDesc) {
		this.dateDesc = dateDesc;
	}

	public CfdRecord(){
		
	}
	
	public CfdRecord(long taskId, String type){
		this.taskId = taskId;
		this.type = type;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public Date getLastSecond() {
		return lastSecond;
	}

	public void setLastSecond(Date lastSecond) {
		this.lastSecond = lastSecond;
	}
	
	

}
