package com.green.battery.server.service;

import java.util.Date;

import org.apache.log4j.Logger;

import com.green.battery.server.dao.BatteryDao;
import com.green.battery.server.model.BatteryEntity;

/**
 * 电池服务
 * @author Administrator
 *
 */
public class BatteryService {
	
	
	private Logger logger = Logger.getLogger(BatteryService.class);
	private BatteryDao dao = new BatteryDao();
	
	/**
	 * 获取电池游标
	 * @param serialNum
	 * @return
	 */
	public BatteryEntity getBattery(String serialNum)throws Exception{
		logger.info("Get cursor for the battery:"+serialNum);
		BatteryEntity battery;
		try {
			battery = dao.getBattery(serialNum);
		} catch (Exception e) {
//			logger.warn("Exception while query battery in getCursor:"+serialNum);
			throw e;
		}
		
		if(battery != null){
			logger.info("Get the battery info:"+battery.getSerial()+"\tcursor:"+battery.getCursors());
			return battery;
		}else{
			logger.info("Current battery is not register yet:"+serialNum+"\t prepare to register it!");
			
			//register the battery
			BatteryEntity b = new BatteryEntity();
			b.setSerial(serialNum);
			b.setManufacturer("Greenway");
			b.setRegister(new Date());
			try {
				dao.addBattery(b);
			} catch (Exception e) {
//				logger.warn("Exception while add battery in getCursor:"+e.getMessage());
				throw e;
			}
			return b;
		}
	}
	
	/**
	 * 更新电池游标
	 * @param serialNum
	 * @param cursor
	 */
	public void updateBattery(BatteryEntity battery)throws Exception{
		logger.info("Update the battery:"+battery.getId()+"\t"+battery.getSerial());
		try {
			dao.updateBattery(battery);
		} catch (Exception e) {
			logger.warn("Exception while update battery:"+e.getMessage());
			throw e;
		}
	}

}
