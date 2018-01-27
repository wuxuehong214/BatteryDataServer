package com.green.battery.entity;

import com.green.battery.entity.TaskEntity;
import com.green.battery.entity.UserEntity;

public class TaskHistoryEntity {
	
	private TaskEntity task;
	private UserEntity user;
	
	
	public TaskEntity getTask() {
		return task;
	}
	public void setTask(TaskEntity task) {
		this.task = task;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	
}
