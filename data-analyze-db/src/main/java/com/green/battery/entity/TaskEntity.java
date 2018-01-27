package com.green.battery.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 66735841230379808L;
	
	public static final String STATE_NEW = "NEW";
	public static final String STATE_QUEUE = "QUEUE";
	public static final String STATE_EXECUTING = "EXECUTING";
	public static final String STATE_FINISHED = "FINISHED";
	public static final String STATE_EXCEPTION = "EXCEPTION";
	public static final String STATE_ALL = "ALL";

	private long id;
	private String name;  //任务名称
	private long userId; //用户ID
	private String serialNum;  //电池序列号
	private Date subTime; //提交时间
	private Date executeTime; //执行时间
	private Date finishTime; //完成时间
	private String state = null;  //状态     0-等待执行  1-执行中  2-执行完成 3-执行异常   -1-所有状态
	private int waitTime =-1;  //从提交到执行的等待时间   单位：秒
	private int actionTime = -1;  //执行 响应时间   分析用时   单位：秒
	private String path;   //分析结果路径
	private String reason;  //异常原因
	private String stateDescp;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String getSubTimes(){
		if(subTime != null)
		return sdf.format(subTime);
		return "";
	}
	
	public String getExecuteTimes(){
		if(executeTime != null)
			return sdf.format(executeTime);
			return "";
	}
	
	public String getFinishTimes(){
		if(finishTime != null)
			return sdf.format(finishTime);
		return "";
	}
	
	
	public void setStateDescp(String stateDescp) {
		this.stateDescp = stateDescp;
	}

	public String getStateDescp(){
		if(stateDescp != null) return stateDescp;
		if(STATE_NEW.equals(state)) return "task.state.new";
		else if(STATE_QUEUE.equals(state)) return "task.state.queue";
		else if(STATE_EXECUTING.equals(state)) return "task.state.executing";
		else if(STATE_FINISHED.equals(state)) return "task.state.finished";
		else if(STATE_EXCEPTION.equals(state)) return "task.state.exception";
		else return "未知状态";
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public Date getSubTime() {
		return subTime;
	}
	public void setSubTime(Date subTime) {
		this.subTime = subTime;
	}
	public Date getExecuteTime() {
		return executeTime;
	}
	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	public int getActionTime() {
		return actionTime;
	}
	public void setActionTime(int actionTime) {
		this.actionTime = actionTime;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "TaskEntity [id=" + id + ", name=" + name + ", userId=" + userId
				+ ", serialNum=" + serialNum + ", subTime=" + subTime
				+ ", executeTime=" + executeTime + ", finshTime=" + finishTime
				+ ", state=" + state + ", waitTime=" + waitTime
				+ ", actionTime=" + actionTime + ", path=" + path + ", reason="
				+ reason + "]";
	}
	
	
	
}
