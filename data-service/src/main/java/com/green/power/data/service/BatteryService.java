package com.green.power.data.service;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.green.power.data.dao.BatteryDao;
import com.green.power.data.entity.ResponseEntity;

/**
 * 电池服务
 * @author Administrator
 *
 */
public class BatteryService {
	
	private BatteryDao dao = new BatteryDao();
	private Logger logger = Logger.getLogger(BatteryService.class);
	
	public ResponseEntity<?> put(String entity){
		logger.info("Put battery info:"+entity);
		Document d = Document.parse(entity);
		
		boolean r = dao.put(d);
		 
		ResponseEntity<?> response = new ResponseEntity<>();
		response.setCode(r?ResponseEntity.CODE_SUCCESS:ResponseEntity.CODE_FAIL);
		if(!r)
			response.setMsg("Put battery info failed!");
		return response;
	}
	
//	public ResponseEntity<BatteryInfoEntity> query(String serialNum){
//		logger.info("Query battery info:"+serialNum);
//		
//		Document d = dao.query(serialNum);
////		String json = d.toJson();
////		
////		System.out.println(json);
////		
////		BatteryEntity be = JSONObject.parseObject(json, BatteryEntity.class);
//		BatteryInfoEntity be = BatteryInfoEntity.toBatteryEntity(d);
//		ResponseEntity<BatteryInfoEntity> response = new ResponseEntity<BatteryInfoEntity>();
//		response.setCode(1);
//		response.setMsg("");
//		response.setData(be);
//		return response;
//	}

}
