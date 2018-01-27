package com.green.battery.entity.ext;

/**
 * 个人工作台 状态
 * 
 * @author Administrator
 * 
 */
public class DashBoardStatus {

	// about task
	private int total;           //总任务数
	private int wait;           //等待执行的任务数
	private int executing;   //执行中的任务数
	private int finished;     //完成的任务数
	private int exception;  //执行异常的任务数

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getWait() {
		return wait;
	}

	public void setWait(int wait) {
		this.wait = wait;
	}

	public int getExecuting() {
		return executing;
	}

	public void setExecuting(int executing) {
		this.executing = executing;
	}

	public int getFinished() {
		return finished;
	}

	public void setFinished(int finished) {
		this.finished = finished;
	}

	public int getException() {
		return exception;
	}

	public void setException(int exception) {
		this.exception = exception;
	}

}
