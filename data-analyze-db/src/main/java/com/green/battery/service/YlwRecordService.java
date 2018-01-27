package com.green.battery.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.green.battery.dao.YlwRecordDao;
import com.green.battery.entity.YlwRecord;

public class YlwRecordService {
	
	private Logger logger = Logger.getLogger(YlwRecordService.class);
	private YlwRecordDao dao = new YlwRecordDao();
	
	public void addBatchYlwRecord(List<YlwRecord> records){
		logger.info("批量插入压、流、温记录:"+records.size());
		try {
			dao.insertBatch(records);
		} catch (Exception e) {
			logger.warn("批量插入压、流、温的记录时数据库操作异常!",e);
		}
	}
}
