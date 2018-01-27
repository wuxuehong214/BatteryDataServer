package com.green.battery.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import com.green.battery.dao.TaskDao;
import com.green.battery.db.NotInitializedException;
import com.green.battery.entity.TaskEntity;

public class TaskService {
	
	private Logger logger = Logger.getLogger(TaskService.class);
	private TaskDao dao = new TaskDao();
	
	/**
	 * 更新任务状态
	 * @param task
	 */
	public void updateTask(TaskEntity task){
//		if(task.getState() == null)
//		{
//			System.out.println();
//		}
		logger.info("更新任务:"+task.getState());
		try {
			dao.update(task);
		} catch (Exception e) {
			logger.warn("更新任务时数据库操作异常!",e);
		}
	}
	
	/**
	 * 按状态查询任务
	 * @param state
	 * @return
	 */
	public List<TaskEntity> queryTasksByState(String state){
//		logger.info("获取状态为["+state+"]的所有任务!");
		List<TaskEntity> tasks = null;
		try {
			tasks = dao.getTasksByState(state);
			return tasks;
		} catch (Exception e) {
			logger.warn("获取状态为["+state+"]的所有任务!",e);
		}
		return new ArrayList<TaskEntity>();
	}
	
}
