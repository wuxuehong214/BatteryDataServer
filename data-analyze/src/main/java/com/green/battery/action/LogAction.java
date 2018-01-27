package com.green.battery.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.green.battery.dao.LogDao;
import com.green.battery.entity.LogEntity;
import com.green.battery.entity.UserEntity;
import com.opensymphony.xwork2.ActionSupport;

public class LogAction extends ActionSupport implements RequestAware,
		SessionAware, ApplicationAware {
	
	private Map<String, Object> session;
	private Map<String, Object> request;
	private Map<String, Object> application;
	
	
	private List<LogEntity> data;
	
	/**
	 * 登录日志
	 * @return
	 */
	public String loginLoglist(){
		UserEntity u = (UserEntity) session.get("user");
		LogDao dao = new LogDao();
		try {
			data = dao.getLoginLogs(u.getRole(), u.getId());
			
			for(LogEntity log:data){
				if(log.getEventType() == LogEntity.TYPE_LOGIN_IN) log.setContent(getText("log.login.in"));
				else if(log.getEventType() == LogEntity.TYPE_LOGIN_OUT) log.setContent(getText("log.login.out"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String analyzeLoglist(){
		UserEntity u = (UserEntity) session.get("user");
		LogDao dao = new LogDao();
		try {
			data = dao.getAnalyzeLogs(u.getRole(), u.getId());
			for(LogEntity log:data){
				if(log.getEventType() == LogEntity.TYPE_ANALYZE_SUBMIT) log.setContent(getText("log.diag.submit"));
				else if(log.getEventType() == LogEntity.TYPE_ANALYZE_DEL) log.setContent(getText("log.diag.delete"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setApplication(Map<String, Object> arg0) {
		this.application = arg0;
	}

	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

	public void setRequest(Map<String, Object> arg0) {
		this.request = arg0;
	}

	public List<LogEntity> getData() {
		return data;
	}

	public void setData(List<LogEntity> data) {
		this.data = data;
	}

}
