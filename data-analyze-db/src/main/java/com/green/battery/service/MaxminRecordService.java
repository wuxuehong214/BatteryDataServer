package com.green.battery.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.green.battery.dao.MaxminRecordDao;
import com.green.battery.entity.MaxminRecord;

public class MaxminRecordService {
	
	private Logger logger = Logger.getLogger(MaxminRecordService.class);
	private MaxminRecordDao dao = new MaxminRecordDao();
	
	public void addBatchMaxminRecord(List<MaxminRecord> records){
		logger.info("批量插入最值统计记录:"+records.size());
		try {
			dao.insertBatch(records);
		} catch (Exception e) {
			logger.warn("批量插入最值统计记录时数据库操作异常!",e);
		}
	}

}
