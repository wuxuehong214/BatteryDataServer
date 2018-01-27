package com.green.battery.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.green.battery.dao.TaskDao;
import com.green.battery.entity.TaskEntity;
import com.green.battery.entity.TaskHistoryEntity;
import com.opensymphony.xwork2.ActionSupport;

public class TaskHistoryAction extends ActionSupport implements RequestAware,SessionAware, ApplicationAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3020554113799031736L;
	private Map<String, Object> session;
	private Map<String, Object> request;
	private Map<String, Object> application;
	
	private List<TaskHistoryEntity> data;
	
	public String list(){
		TaskDao dao = new TaskDao();
		try {
			this.data = dao.getAllTasks(0, 1000000000);
			for(TaskHistoryEntity task:data){
				task.getTask().setStateDescp(getText(task.getTask().getStateDescp()));
				task.getTask().setReason("<a	href=\"#myModal\" role=\"button\" data-toggle=\"modal\" onclick=\"delTask('"+task.getTask().getId()+"')\"><i class=\"fa fa-trash-o\"></i></a> &nbsp;&nbsp;&nbsp;&nbsp;"
						+ "<a	href=\"Task_result?taskid="+task.getTask().getId()+"\" ><i class=\"fa fa-signal\"></i></a>  ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
		
	}
	
	
	
	public List<TaskHistoryEntity> getData() {
		return data;
	}



	public void setData(List<TaskHistoryEntity> data) {
		this.data = data;
	}



	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}
	

}
