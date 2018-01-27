package com.green.battery.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.green.battery.dao.YlwRecordDao;
import com.green.battery.entity.YlwRecord;
import com.opensymphony.xwork2.ActionSupport;

public class YlwAction extends ActionSupport {
	
	public static class DataItem{
		List<String> xs = new ArrayList<String>();
		List<Float> values = new ArrayList<Float>();
		
		public List<String> getXs() {
			return xs;
		}
		public void setXs(List<String> xs) {
			this.xs = xs;
		}
		public List<Float> getValues() {
			return values;
		}
		public void setValues(List<Float> values) {
			this.values = values;
		}
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7725454903684584900L;
	
	private DataItem[] data;
	private long taskid;

	public static void main(String args[]) {
		List<Object[]> result = new ArrayList<Object[]>();

		Object[] o1 = new Object[] { "2013", 1 };
		Object[] o2 = new Object[] { "2014", 2 };

		result.add(o1);
		result.add(o2);
		System.out.println(JSONArray.toJSONString(result));
	}

	public String ylw() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
		YlwRecordDao dao = new YlwRecordDao();
		List<YlwRecord> records = null;
		try {
			records = dao.queryRecords(taskid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(records == null) records = new ArrayList<YlwRecord>();
		
		data = new DataItem[3];
		for(int i=0;i<3;i++){
			data[i] = new DataItem();
		}
		
		for(YlwRecord record:records){
			
			for(int i=0;i<3;i++)data[i].xs.add(sdf.format(record.getSjc()));
			data[0].values.add(record.getWd());
			data[1].values.add((float)record.getDl());
			data[2].values.add((float)record.getDy());
		}
		return SUCCESS;
	}

	public DataItem[] getData() {
		return data;
	}

	public void setData(DataItem[] data) {
		this.data = data;
	}

	public long getTaskid() {
		return taskid;
	}

	public void setTaskid(long taskid) {
		this.taskid = taskid;
	}


}
