package com.green.battery.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.green.battery.dao.CfdRecordDao;
import com.green.battery.db.NotInitializedException;
import com.green.battery.entity.CfdRecord;
import com.opensymphony.xwork2.ActionSupport;

public class CfdAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5949397534851940169L;

	private List<CfdRecord> cds;
	private List<CfdRecord> fds;
	private List<CfdRecord> wcds;
	private long taskid;
	private String type;

	public String records() {
		CfdRecordDao dao = new CfdRecordDao();
		try {
			cds = dao.queryCfdRecords(type, taskid);
		} catch (Exception e) {
			e.printStackTrace();
			cds = new ArrayList<CfdRecord>();
		}
		return SUCCESS;
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

	public List<CfdRecord> getWcds() {
		return wcds;
	}

	public void setWcds(List<CfdRecord> wcds) {
		this.wcds = wcds;
	}

	public long getTaskid() {
		return taskid;
	}

	public void setTaskid(long taskid) {
		this.taskid = taskid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
