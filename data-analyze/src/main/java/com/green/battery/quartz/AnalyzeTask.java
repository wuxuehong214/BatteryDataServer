package com.green.battery.quartz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.green.analyze.battery.entity.BatteryStatusEntity;
import com.green.analyze.battery.entity.RecordEntity;
import com.green.battery.entity.TaskEntity;
import com.green.battery.service.TaskService;

public class AnalyzeTask implements Runnable {
	
	private TaskEntity task;
	private Logger logger = Logger.getLogger(AnalyzeTask.class);
	private TaskService taskService = new TaskService();
	private AnalyzeRuntimeStatus runtime;
	
	private static class ExecutorServiceProducer{
		private static ExecutorService  executorService = Executors.newFixedThreadPool(5);
	}
	
	public void execute(){
		ExecutorServiceProducer.executorService.execute(this);
	}
	
	public static void main(String args[]){
		TaskEntity task = new TaskEntity();
		task.setSubTime(new Date());
		task.setId(23);
		AnalyzeTask t = new AnalyzeTask(task);
		new Thread(t).start();
	}
	
	public AnalyzeTask(TaskEntity task){
		this.task = task;
		runtime = new AnalyzeRuntimeStatus(task);
	}

	public void run() {
		
		logger.info("任务:【"+task.getName()+"】开始执行！");
	    //TODO 更新任务状态，正在执行
		TaskEntity tmp = new TaskEntity();
		tmp.setExecuteTime(new Date());
		tmp.setId(task.getId());
		tmp.setState(TaskEntity.STATE_EXECUTING);
		tmp.setWaitTime((int)((tmp.getExecuteTime().getTime()-task.getSubTime().getTime())/1000));  //s
		taskService.updateTask(tmp);
		task.setExecuteTime(tmp.getExecuteTime());
		
		
		//判断文件是否存在
//		File f = new File("upload/"+task.getPath());
		File f = new File(task.getPath());
		if(!f.exists()){
			logger.info("任务:【"+task.getName()+"】数据文件未上传！");
			tmp = new TaskEntity();
			tmp.setId(task.getId());
			tmp.setState(TaskEntity.STATE_EXCEPTION);
			tmp.setReason("数据文件未上传!");
			taskService.updateTask(tmp);
			return;
		}
		
		//判断文件是否正确
		File[] fs = f.listFiles();
		int count = 0;
		for(File ff:fs){
			if(ff.getName().endsWith(".GWR")) count+=1;
			if(ff.getName().endsWith(".GWI")) count+=1;
		}
		if(count <2){
			logger.info("任务:【"+task.getName()+"】数据文件不足,需要有GWR/GWI两个文件！");
			tmp = new TaskEntity();
			tmp.setId(task.getId());
			tmp.setState(TaskEntity.STATE_EXCEPTION);
			tmp.setReason("数据文件不足,需要有GWR/GWI两个文件!");
			taskService.updateTask(tmp);
			return;
		}
		
		//读取文件
		for(File ff:fs){
			if(ff.getName().endsWith("GWI")){
				logger.info("任务:【"+task.getName()+"】分析GWI文件!");
				try {
					BatteryStatusEntity bse = BatteryStatusEntity.convet(ff);
					tmp = new TaskEntity();
					tmp.setId(task.getId());
					tmp.setSerialNum(bse.getSerialNum());
					taskService.updateTask(tmp);
				} catch (Exception e) {
					logger.warn("任务:【"+task.getName()+"】分析GWI文件异常！",e);
					tmp = new TaskEntity();
					tmp.setId(task.getId());
					tmp.setSerialNum("未知");
					tmp.setState(TaskEntity.STATE_EXCEPTION);
					tmp.setReason("电池实时数据信息分析异常!");
					taskService.updateTask(tmp);
					return;
				}
				break;
			}
		}
		
		for(File ff:fs){
			if(ff.getName().endsWith("GWR")){
				byte[] buffer = new byte[64];
				try {
					InputStream is = new FileInputStream(ff);
					int len = -1;
					while((len = is.read(buffer))  != -1){
						if(len == 64){
							  RecordEntity record = RecordEntity.convet(buffer);
							  runtime.newRecord(record);
						}
					}
					is.close();
				} catch (IOException e) {
					logger.warn("任务:【"+task.getName()+"】分析GWR文件异常！",e);
					tmp = new TaskEntity();
					tmp.setId(task.getId());
					tmp.setState(TaskEntity.STATE_EXCEPTION);
					tmp.setReason("电池历史数据信息分析异常!");
					taskService.updateTask(tmp);
					return;
				}
				runtime.finish();
				break;
			}
		}
		
		tmp = new TaskEntity();
		tmp.setFinishTime(new Date());
		tmp.setId(task.getId());
		tmp.setState(TaskEntity.STATE_FINISHED);
		tmp.setActionTime((int)((tmp.getFinishTime().getTime()-task.getExecuteTime().getTime())/1000));  //s
		taskService.updateTask(tmp);
	}

}
