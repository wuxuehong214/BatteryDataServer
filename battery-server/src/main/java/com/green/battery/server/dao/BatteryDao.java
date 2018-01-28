package com.green.battery.server.dao;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.green.battery.server.model.BatteryEntity;


public class BatteryDao {
	Logger log = Logger.getLogger(BatteryDao.class);
	
	/**
	 * 新增电池信息
	 * @param battery
	 * @throws Exception
	 */
	public void addBattery(BatteryEntity battery) throws Exception{
			Session session = MySessionFactory.openSession();
			Transaction ta = session.beginTransaction();
			session.save(battery);
			ta.commit();
			session.close();
	}
	
	/**
	 * 获取电池信息
	 * @param serial
	 * @return
	 * @throws Exception
	 */
	public BatteryEntity getBattery(String serial)throws Exception{
		BatteryEntity battery;
		Session session = MySessionFactory.openSession();
		Transaction ta = session.beginTransaction();
		Query query = session.createQuery("from BatteryEntity where serial = ?");
		query.setParameter(0,serial);
		battery = (BatteryEntity) query.uniqueResult();
		ta.commit();
		session.close();
		return battery;
	}
	
	/**
	 * 更新电池信息
	 * @param battery
	 * @throws Exception
	 */
	public void updateBattery(BatteryEntity battery)throws Exception{
		Session session = MySessionFactory.openSession();
		Transaction ta = session.beginTransaction();
		session.update(battery);
		ta.commit();
		session.close();
	}
	
	public static void main(String args[]) throws Exception{
		BatteryDao dao = new BatteryDao();
		BatteryEntity battery = new BatteryEntity();
		battery.setRegister(new Date());
		battery.setSerial("dd");
		battery.setDcxh("2342");
		battery.setManufacturer("Green way");
		dao.addBattery(battery);
		
		
		System.out.println("##################:"+battery.getId());
//		String serial = "wqweasdfrqw";
//		BatteryEntity entity = dao.getBattery(serial);
//		
//		System.out.println(entity.getManufacturer());
		
		
	}
	
}
