package com.green.power.data.resource;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.green.power.data.entity.BatteryInfoEntity;
import com.green.power.data.entity.BatteryDataEntity;
import com.green.power.data.entity.ResponseEntity;
import com.green.power.data.service.BatteryDataService;
import com.green.power.data.service.BatteryInfoService;

/**
 * 电池信息
 * @author Administrator
 *
 */
@Path("batteryinfo")
public class BatteryResource {
	
	private BatteryInfoService batteryService = new BatteryInfoService();
	private BatteryDataService batteryDataService = new BatteryDataService();
	private Logger logger = Logger.getLogger(BatteryResource.class);
	
	@POST
	@Path("info")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String put(String entity) {
		logger.info("Put battery info:"+entity);
		BatteryInfoEntity battery = null;
		ResponseEntity<Integer> response = new ResponseEntity<>();
		response.setCode(0);
		response.setData(0);
		response.setMsg("nothing");
		try{
			battery = JSONObject.parseObject(entity,BatteryInfoEntity.class);
		}catch(Exception e){
			logger.warn("解析电池信息失败:"+entity);
			response.setMsg("解析电池信息失败!");
		}
		
		if(battery != null){
			BatteryInfoEntity t =  batteryService.queryBatteryInfo(battery.getSerialNum());
			boolean r = false;
			if(t == null){
				r = batteryService.addBatteryInfo(battery);
			}else{
				r = batteryService.updateBatteryInfo(battery);
			}
			if(r){
				response.setCode(1);
			}else{
				response.setMsg("保存电池信息异常!");
			}
		}
		return JSONObject.toJSONString(response);
	}
	
	@GET
	@Path("{serialNum}")
	@Produces(MediaType.APPLICATION_JSON)
	public String query(@PathParam("serialNum")String serialNum){
		ResponseEntity<BatteryInfoEntity> response = new ResponseEntity<BatteryInfoEntity>();
		response.setCode(0);
		BatteryInfoEntity battery = batteryService.queryBatteryInfo(serialNum);
		response.setData(battery);
		if(battery!= null){
			response.setCode(1);;
		}else{
			response.setMsg("查询不到指定的电池信息!");
		}
		return JSONObject.toJSONString(response);
	}
	
	@GET
	@Path("getaddress/{serialNum}")
	public String getAddress(@PathParam("serialNum")String serialNum){
		logger.info("Get battery lastest address:"+serialNum);
		ResponseEntity<Map<String,Long>> response = new ResponseEntity<Map<String,Long>>();
		response.setCode(0);
		response.setMsg("nothing");
		
		BatteryDataEntity data = batteryDataService.queryLastData(serialNum);
		if(data == null ){
			response.setMsg("该电池还未上传数据!");
			Map<String,Long> map = new HashMap<String, Long>();
			map.put("recordTime", 0l);
			map.put("recordCurcor", 0l);
			response.setData(map);
		}else{
			response.setCode(1);
			Map<String,Long> map = new HashMap<String, Long>();
			map.put("recordTime", data.getRecordTime());
			map.put("recordCurcor",data.getRecordCurcor());
			response.setData(map);
		}
		return JSONObject.toJSONString(response);
	}

}
