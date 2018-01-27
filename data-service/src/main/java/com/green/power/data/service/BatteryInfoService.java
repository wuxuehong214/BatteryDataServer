package com.green.power.data.service;

import org.apache.log4j.Logger;

import com.green.power.data.dao.BatteryInfoDao;
import com.green.power.data.entity.BatteryInfoEntity;

/**
 * 电池信息服务
 * 
 * @author Administrator
 * 
 */
public class BatteryInfoService {

	private BatteryInfoDao dao;
	private Logger logger = Logger.getLogger(BatteryInfoService.class);

	public BatteryInfoService() {
		dao = new BatteryInfoDao();
	}

	/**
	 * 新增电池信息
	 * @param battery
	 * @return
	 */
	public boolean addBatteryInfo(BatteryInfoEntity battery) {
		logger.info("新增OR编辑电池信息:"+battery.getSerialNum()+"\t"+battery.getBatteryName());
		if (battery == null || battery.getSerialNum() == null) {
			logger.info("BatteryInfo is null or  serialNum is null");
			return false;
		}
		try {
			BatteryInfoEntity b = dao.queryBatteryInfo(battery.getSerialNum());
			if (b == null)
				dao.addBatteryInfo(battery);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * 查询电池信息
	 * @param serialNum
	 * @return
	 */
	public BatteryInfoEntity queryBatteryInfo(String serialNum){
		logger.info("查询电池信息:"+serialNum);
		try {
			BatteryInfoEntity battery = dao.queryBatteryInfo(serialNum);
			return battery;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 更新电池信息
	 * @param battery
	 * @return
	 */
	public boolean updateBatteryInfo(BatteryInfoEntity battery){
		logger.info("更新电池信息:"+battery.getSerialNum());
		try {
			dao.updateBatteryInfo(battery);
			return true;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		return false;
	}
}
