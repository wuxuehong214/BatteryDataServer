package com.green.battery.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.green.battery.dao.CfdRecordDao;
import com.green.battery.entity.CfdRecord;

public class CfdRecordService {

	private Logger logger = Logger.getLogger(CfdRecordService.class);
	private CfdRecordDao dao = new CfdRecordDao();

	public void addBatch(List<CfdRecord> records) {
		logger.info("批量插入充放电记录:" + records.size());
		try {
			dao.insertBatch(records);
		} catch (Exception e) {
			logger.warn("批量插入充放电记录时数据库操作异常!", e);
		}
	}

}
