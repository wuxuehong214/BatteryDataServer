package com.green.battery.server.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.green.battery.server.model.BatteryRecordEntity;

public class BatteryRecordDao {
	
	Logger log = Logger.getLogger(BatteryRecordDao.class);
	
	/**
	 * 批量插入
	 * @param records
	 * @throws Exception
	 */
	public void addBatchBatteryRecords(List<BatteryRecordEntity> records)throws Exception{
		Session session = MySessionFactory.openSession();
		Transaction ta = session.beginTransaction();
		for(BatteryRecordEntity record:records)
			session.save(record);
		ta.commit();
		session.close();
	}

}
