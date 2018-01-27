package com.green.battery.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2115680360375883914L;
	public static final int TYPE_LOGIN = 1;
	public static final int TYPE_ANALYZE = 2;
	
	public static final int TYPE_LOGIN_IN = 10;
	public static final int TYPE_LOGIN_OUT = 11;
	public static final int TYPE_ANALYZE_SUBMIT = 21;
	public static final int TYPE_ANALYZE_DEL = 24;
	public static final int TYPE_ANALYZE_START = 22;
	public static final int TYPE_ANALYZE_FINISH = 23;
	
	public static String event(int event_type){
		switch (event_type) {
		case TYPE_LOGIN_IN:
				return "登录系统";
		case TYPE_LOGIN_OUT:
			    return "退出系统";
		case TYPE_ANALYZE_SUBMIT:
				return "发起电池诊断请求";
		case TYPE_ANALYZE_START:
				return "开始执行电池诊断";
		case TYPE_ANALYZE_FINISH:
				return "完成电池诊断";
		case TYPE_ANALYZE_DEL:
				return "删除电池诊断任务";
		default:
			return "未知";
		}
	}
	
	private long id;
	private long userId = -1;  //操作用户
	private long taskId = -1;  //任务ID
	private int type = 1 ; // 1 登陆  2-分析
	private Date logTime = new Date();; //日志时间 
	private int eventType; //事件类型
	private String content; 
	private int level = 1;  // 日志级别  1-正常 0-警告
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String getLogTimes(){
		if(logTime != null) 
			return sdf.format(logTime);
		return "";
	}
	
	private UserEntity user;
	private TaskEntity task;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public TaskEntity getTask() {
		return task;
	}
	public void setTask(TaskEntity task) {
		this.task = task;
	}
	
}
