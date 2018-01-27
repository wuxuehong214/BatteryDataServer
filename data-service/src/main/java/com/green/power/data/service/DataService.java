package com.green.power.data.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.green.power.data.dao.DataDao;
import com.green.power.data.entity.BatteryDataEntity;
import com.green.power.data.entity.ResponseEntity;

/**
 * 数据服务
 * @author Administrator
 *
 */
public class DataService {
	
	private Logger logger  = Logger.getLogger(DataService.class);
	private DataDao dao = new DataDao();
	
	/**
	 * 批量存储电池数据
	 * @param entity
	 * @return
	 */
	public ResponseEntity<?> put(String entity){
		logger.info("Put battery data!");
		ResponseEntity<?> response = new ResponseEntity<>();
		response.setCode(ResponseEntity.CODE_SUCCESS);
		
		List<BatteryDataEntity> datas = null;
		try{
			datas = JSONArray.parseArray(entity, BatteryDataEntity.class);
		}catch(Exception e){
			logger.warn(e.getMessage(),e);
			response.setCode(ResponseEntity.CODE_FAIL);
			response.setMsg("Parse data exception!"+e.getMessage());
		}
		
		if(datas != null){
			List<Document> ds = new ArrayList<>();
			for(BatteryDataEntity data:datas){
				String json = JSONObject.toJSONString(data);
				ds.add(Document.parse(json));
				
			}
			
			boolean r = dao.put(ds);
			response.setCode(r?ResponseEntity.CODE_SUCCESS:ResponseEntity.CODE_FAIL);
			if(!r)
				response.setMsg("数据发布失败!");
		}
		return response;
	}
	
	public ResponseEntity<List<BatteryDataEntity>> query(String serialNum){
		logger.info("Query battery's data:"+serialNum);
		
		List<BatteryDataEntity> datas = dao.query(serialNum);
		
		ResponseEntity<List<BatteryDataEntity>> response = new ResponseEntity<List<BatteryDataEntity>>();
		response.setCode(ResponseEntity.CODE_SUCCESS);
		response.setData(datas);
		
		return response;
	}
	
	public static void main(String args[]){
		DataService service = new DataService();
//		List<DataEntity> ds = new ArrayList<>();
//		for(int i=0;i<10;i++){
//			DataEntity d = new DataEntity();
//			d.setSerialNum("battery1");
//			d.setCacheAddress(10000+i);
//			d.setData(new byte[]{0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08});
//			d.setDataLength(8);
//			ds.add(d);
//		}
//		String entity = JSONArray.toJSONString(ds);
//		System.out.println(entity);
//		ResponseEntity<?> response = service.put(entity);
//		System.out.println(JSONObject.toJSONString(response));
		
		ResponseEntity<List<BatteryDataEntity>> response = service.query("battery1");
		System.out.println(JSONObject.toJSON(response));
	}

}
