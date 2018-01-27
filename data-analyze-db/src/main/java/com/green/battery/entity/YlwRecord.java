package com.green.battery.entity;

import java.util.Date;

/**
 * 电压、电流、温度 记录
 * @author Administrator
 *
 */
public class YlwRecord {
	
	private long id;
	private int dy;
	private int dl;
	private float wd;
	private Date sjc;
	private long taskId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}
	public int getDl() {
		return dl;
	}
	public void setDl(int dl) {
		this.dl = dl;
	}
	public float getWd() {
		return wd;
	}
	public void setWd(float wd) {
		this.wd = wd;
	}
	public Date getSjc() {
		return sjc;
	}
	public void setSjc(Date sjc) {
		this.sjc = sjc;
	}
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

}
