package com.green.battery.quartz;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.green.battery.entity.TaskEntity;
import com.green.battery.service.TaskService;

public class QuartSchedulerListener implements ServletContextListener {
	
	public static String path;
	
	private static TaskService taskService = new TaskService();
	private static Logger logger = Logger.getLogger(QuartSchedulerListener.class);

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	static class SingleThread{
		
		private static class SingleThreadProducer{
			private static SingleThread st = new SingleThread();
		}
		public void execute(){
			System.out.println("******************************执行!");
		}
		private SingleThread() {
			new Thread(new Runnable() {
				public void run() {
					 while(true){
						 	  List<TaskEntity> tasks = taskService.queryTasksByState(TaskEntity.STATE_NEW);
						 	  if(tasks.size()>0)
						 		  	logger.info("Get new tasks:"+tasks.size());
						 	 
						 	  if(tasks.size()>0){
						 		  	  for(TaskEntity task:tasks){
						 		  		  //更新任务状态
						 		  		     TaskEntity tmp = new TaskEntity();
						 		  		     tmp.setId(task.getId());
						 		  		     tmp.setState(TaskEntity.STATE_QUEUE);
						 		  		     taskService.updateTask(tmp);
						 		  		     //执行
						 		  		     task.setPath(path+"/"+task.getPath());
						 		  		     new AnalyzeTask(task).execute();
						 		  	  }
						 		  	  
						 	  }
						 	  
						 	  try {
								TimeUnit.SECONDS.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
					 }
				}
			}).start();
		}
		
		public static SingleThread getInstance(){
			return SingleThreadProducer.st;
		}
	}

	public void contextInitialized(ServletContextEvent s) {
		path=s.getServletContext().getRealPath("upload");
		logger.info("Start to check new tasks!");
		SingleThread.getInstance().execute();
		
	}

}
