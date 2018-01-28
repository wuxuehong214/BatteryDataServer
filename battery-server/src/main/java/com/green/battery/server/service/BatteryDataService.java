package com.green.battery.server.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.green.battery.server.dao.BatteryRecordDao;
import com.green.battery.server.model.BatteryRecordEntity;


/**
 * 电池数据服务
 * @author Administrator
 *
 */
public class BatteryDataService {
	
	private Logger logger = Logger.getLogger(BatteryService.class);
	private BatteryRecordDao dao = new BatteryRecordDao();
	
	/**
	 * 批量添加电池数据
	 * @param records
	 * @throws Exception
	 */
	public void addBatchBatteryRecords(List<BatteryRecordEntity> records) throws Exception{
		logger.info("Ask for add batch battery records:"+records.size());
		try {
			dao.addBatchBatteryRecords(records);
		} catch (Exception e) {
			throw e;
		}
	}
	

}
