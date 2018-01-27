package com.green.power.data.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import com.green.power.data.dao.BatteryDataDao;
import com.green.power.data.db.NotInitializedException;
import com.green.power.data.entity.BatteryDataEntity;

/**
 * 电池数据服务
 * @author Administrator
 *
 */
public class BatteryDataService {
	
	private BatteryDataDao dao;
	private Logger logger = Logger.getLogger(BatteryDataService.class);
	
	public BatteryDataService(){
		dao = new BatteryDataDao();
	}
	
	/**
	 * 批量插入电池监控数据
	 * @param datas
	 * @return
	 */
	public boolean addDatas(List<BatteryDataEntity> datas){
		if(datas == null) return false;
		logger.info("批量插入电池数据:"+datas.size());
		Date cur = new Date();
		for(BatteryDataEntity data:datas)
			data.setServerReadTime(cur);
		try {
			dao.insertBatch(datas);
			return true;
		} catch (Exception e) {
			logger.warn(e.getMessage(),e);
		}
		return false;
	}
	
	/**
	 * 查询电池最新一条监控数据信息
	 * @param serialNum
	 * @return
	 */
	public BatteryDataEntity queryLastData(String serialNum){
		logger.info("查询电池:"+serialNum+"最新监控数据!");
		try {
			return dao.queryLastBatteryData(serialNum);
		} catch (Exception e) {
			logger.warn(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 批量查询
	 * @param serialNum
	 * @param start
	 * @param size
	 * @return
	 */
	public List<BatteryDataEntity> queryBatteryDatas(String serialNum, int start, int size){
		logger.info("批量获取电池"+serialNum+"监控数据:start:"+start+"\tsize:"+size);
		try {
			return dao.queryBatteryDatas(serialNum, start, size);
		} catch (Exception e) {
			logger.warn(e.getMessage(),e);
		}
		return new ArrayList<BatteryDataEntity>();
	}
	
	/**
	 * 删除电池数据
	 * @param serialNum
	 * @return
	 */
	public boolean clearBatteryData(String serialNum){
		logger.info("清除电池:"+serialNum+"所有监控数据!");
		try {
			dao.deleteData(serialNum);
			return true;
		} catch (Exception e) {
			logger.warn(e.getMessage(),e);
		}
		return false;
	}
	
	/**
	 * 查询指定电池数据条数
	 * @param serialNum
	 * @return
	 */
	public int countBatteryData(String serialNum){
		logger.info("查询电池:"+serialNum+"监控数据条数!");
		try {
			return dao.countData(serialNum);
		} catch (SQLException | TimeoutException | NotInitializedException e) {
			logger.warn(e.getMessage(),e);
		}
		return 0;
	}
}
