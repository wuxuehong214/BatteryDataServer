package com.green.battery.action;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.alibaba.fastjson.JSONArray;
import com.green.analyze.battery.entity.BatteryStatusEntity;
import com.green.battery.dao.CfdRecordDao;
import com.green.battery.dao.LogDao;
import com.green.battery.dao.MaxminRecordDao;
import com.green.battery.dao.TaskDao;
import com.green.battery.db.NotInitializedException;
import com.green.battery.entity.CfdRecord;
import com.green.battery.entity.LogEntity;
import com.green.battery.entity.MaxminRecord;
import com.green.battery.entity.TaskEntity;
import com.green.battery.entity.TaskHistoryEntity;
import com.green.battery.entity.UserEntity;
import com.opensymphony.xwork2.ActionSupport;

public class TaskAction extends ActionSupport implements RequestAware,SessionAware, ApplicationAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7660045181753651809L;
	private Map<String, Object> session;
	private Map<String, Object> request;
	private Map<String, Object> application;
	
	private List<TaskEntity> data;
	private List<LogEntity> logs;
	private List<TaskHistoryEntity> his;
	private TaskEntity task;
	private String page;
	private long taskid;
	private BatteryStatusEntity battery;
	
	private List<CfdRecord> cds;
	private List<CfdRecord> fds;
	private List<CfdRecord> wcds;
	
	private List<MaxminRecord> mmrecods;
	
	/**
	 * 获取任务诊断详情
	 * @return
	 */
	public String result(){
		TaskDao taskdao = new TaskDao();
		TaskEntity task = null;
		try {
			task = taskdao.getTask(taskid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(task == null) return "error500";
		
		this.task = task;
		task.setStateDescp(getText(task.getStateDescp()));
		 String fpath=ServletActionContext.getServletContext().getRealPath("upload");
		 fpath = fpath+"/"+task.getPath();
		 
		 File f = new File(fpath);
		 File[] fs = f.listFiles();
		 if(fs!=null)
		 for(File ff:fs){
			 if(ff.getName().endsWith("GWI")){
				 try {
					battery = BatteryStatusEntity.convet(ff);
				} catch (Exception e) {
					battery = null;
				}
				 
				 //TODO 临时使用
//				 battery.setDxxh("NCR18650PF");
//				 battery.setWd(26.8f);
//				 battery.setDl(1200);
			 }
		 }
		 if(battery == null) battery = new BatteryStatusEntity();
		 
		 
		 CfdRecordDao dao = new CfdRecordDao();
		 try {
			cds = dao.queryCfdRecords(CfdRecord.TYPE_CD, taskid);
		} catch (Exception e) {
			cds = new ArrayList<CfdRecord>();
		}
		 
		 try {
			fds = dao.queryCfdRecords(CfdRecord.TYPE_FD, taskid);
		} catch (Exception e) {
			fds = new ArrayList<CfdRecord>();
		}
		 
		 try {
			wcds = dao.queryCfdRecords(CfdRecord.TYPE_WCD, taskid, 24*3600*30);//超过30天未充电的记录
		} catch (Exception e1) {
			wcds = new ArrayList<CfdRecord>();
		} 
		 
		 MaxminRecordDao mmdao = new MaxminRecordDao();
		 try {
			mmrecods = mmdao.queryRecoreds(taskid);
			for(MaxminRecord m:mmrecods)m.setTypeDescp(getText(m.getType()));
		} catch (Exception e) {
			mmrecods = new ArrayList<MaxminRecord>();
		}
		 
		 System.out.println(battery.toString());
		 page = "analyze_result";
		return "target";
	}
	
	/**
	 * 任务列表
	 * @return
	 */
	public String list(){
		
		TaskDao dao = new TaskDao();
		logs = new ArrayList<LogEntity>();
		LogEntity l1  =new LogEntity();
		l1.setContent("log1");
		
		LogEntity l2  =new LogEntity();
		l2.setContent("log1");
		
		logs.add(l1);
		logs.add(l2);
//		try {
//			this.his = dao.getAllTasks(0, 1000000000);
//			for(TaskHistoryEntity task:his){
//				task.getTask().setReason("<a	href=\"#myModal\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-trash-o\"></i></a> &nbsp;&nbsp;&nbsp;&nbsp;"
//						+ "<a	href=\"Task_result?taskid="+task.getTask().getId()+"\" ><i class=\"fa fa-signal\"></i></a>  ");
//			}
//			System.out.println(JSONArray.toJSONString(his));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String del(){
		TaskDao dao = new TaskDao();
		try {
			dao.deleteTask(taskid);
			
			UserEntity u = (UserEntity)session.get("user");
			LogDao logDao = new LogDao();
			LogEntity log = new LogEntity();
			log.setType(LogEntity.TYPE_ANALYZE);
			log.setEventType(LogEntity.TYPE_ANALYZE_DEL);
			log.setLogTime(new Date());
			log.setTaskId(taskid);
			log.setUserId(u.getId());
			log.setContent(LogEntity.event(log.getEventType()));
			logDao.addLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		page = "analyze_mine";
		return "target";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String del2(){
		TaskDao dao = new TaskDao();
		try {
			dao.deleteTask(taskid);
			
			UserEntity u = (UserEntity)session.get("user");
			LogDao logDao = new LogDao();
			LogEntity log = new LogEntity();
			log.setType(LogEntity.TYPE_ANALYZE);
			log.setEventType(LogEntity.TYPE_ANALYZE_DEL);
			log.setLogTime(new Date());
			log.setTaskId(taskid);
			log.setUserId(u.getId());
			log.setContent(LogEntity.event(log.getEventType()));
			logDao.addLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		page = "analyze_history";
		return "target";
	}
	/**
	 * 
	 * @return
	 */
	public String list2(){
		TaskDao dao = new TaskDao();
		
		UserEntity u = (UserEntity)session.get("user");
		try {
			this.data = dao.getTasks(u.getRole(),u.getId(), 0, 100000000);
			for(TaskEntity task:data){  //<a	href=\"Task_del?taskid="+task.getId()+"\" onclick=\"return delTask()\">
				task.setStateDescp(getText(task.getStateDescp()));
				task.setReason("<a	href=\"#myModal\" role=\"button\" data-toggle=\"modal\" onclick=\"delTask('"+task.getId()+"')\"><i class=\"fa fa-trash-o\"></i></a> &nbsp;&nbsp;&nbsp;&nbsp;"
						+ "<a	href=\"Task_result?taskid="+task.getId()+"\" ><i class=\"fa fa-signal\"></i></a>  ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 任务提交
	 * @return
	 */
	public String submit(){
		
		TaskDao dao = new TaskDao();
		task.setSubTime(new Date());
		task.setState(TaskEntity.STATE_NEW);
		UserEntity u = (UserEntity)session.get("user");
		if(u != null)
		task.setUserId(u.getId());
		try {
			dao.addTask(task);
			
			
			LogDao logDao = new LogDao();
			LogEntity log = new LogEntity();
			log.setType(LogEntity.TYPE_ANALYZE);
			log.setEventType(LogEntity.TYPE_ANALYZE_SUBMIT);
			log.setLogTime(new Date());
			log.setTaskId(task.getId());
			log.setUserId(u.getId());
			log.setContent(LogEntity.event(log.getEventType()));
			logDao.addLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		page = "analyze_mine";
		return "redirect";
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

	public List<TaskEntity> getData() {
		return data;
	}

	public void setData(List<TaskEntity> data) {
		this.data = data;
	}

	public TaskEntity getTask() {
		return task;
	}

	public void setTask(TaskEntity task) {
		this.task = task;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public long getTaskid() {
		return taskid;
	}

	public void setTaskid(long taskid) {
		this.taskid = taskid;
	}

	public BatteryStatusEntity getBattery() {
		return battery;
	}

	public void setBattery(BatteryStatusEntity battery) {
		this.battery = battery;
	}

	public List<CfdRecord> getCds() {
		return cds;
	}

	public void setCds(List<CfdRecord> cds) {
		this.cds = cds;
	}

	public List<CfdRecord> getFds() {
		return fds;
	}

	public void setFds(List<CfdRecord> fds) {
		this.fds = fds;
	}

	public List<MaxminRecord> getMmrecods() {
		return mmrecods;
	}

	public void setMmrecods(List<MaxminRecord> mmrecods) {
		this.mmrecods = mmrecods;
	}

	public List<CfdRecord> getWcds() {
		return wcds;
	}

	public void setWcds(List<CfdRecord> wcds) {
		this.wcds = wcds;
	}

	public List<TaskHistoryEntity> getHis() {
		return his;
	}

	public void setHis(List<TaskHistoryEntity> his) {
		this.his = his;
	}

	public List<LogEntity> getLogs() {
		return logs;
	}

	public void setLogs(List<LogEntity> logs) {
		this.logs = logs;
	}

}
