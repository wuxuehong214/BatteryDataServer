package com.green.power.data.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.green.power.data.entity.BatteryDataEntity;
import com.green.power.data.entity.ResponseEntity;
import com.green.power.data.service.BatteryDataService;

/**
 * 
 * 电池数据资源管理
 * 
 * @author Administrator
 * 
 */

@Path("batterycache")
public class DataResource {

//	private DataService dataService = new DataService();
	private BatteryDataService service = new BatteryDataService();
	private Logger logger = Logger.getLogger(DataResource.class);

	@POST
	@Path("cache")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String put(String entity) {
		logger.info("Put battery datas!");
		ResponseEntity<Integer> response = new ResponseEntity<Integer>();
		try{
			List<BatteryDataEntity> datas = JSONArray.parseArray(entity,BatteryDataEntity.class);
			
			boolean r = service.addDatas(datas);
			if(r){
				response.setCode(ResponseEntity.CODE_SUCCESS);
			}else
			{
				response.setMsg("电池数据信息插入异常!");
			}
		}catch(Exception e){
			response.setData(0);
			response.setMsg("数据信息解析异常!");
		}
		return JSONObject.toJSONString(response);
	}
	
	@GET
	@Path("{serialNum}")
	@Produces(MediaType.APPLICATION_JSON)
	public String query(@PathParam("serialNum") String serialNum, @DefaultValue("100") @QueryParam("size")int size){
		logger.info("Query battery datas:"+serialNum);
		ResponseEntity<List<BatteryDataEntity>> response = new ResponseEntity<List<BatteryDataEntity>>();
		List<BatteryDataEntity> datas = service.queryBatteryDatas(serialNum, 0, size);
		logger.info("Acutal datas size:"+datas.size());
		response.setData(datas);
		response.setCode(1);
		return JSONObject.toJSONString(response);
	}
	
	@GET
	@Path("clear/{serialNum}")
	@Produces(MediaType.APPLICATION_JSON)
	public String clear(@PathParam("serialNum") String serialNum){
		logger.info("Clear battery datas:"+serialNum);
		ResponseEntity<Integer> response = new ResponseEntity<Integer>();
		boolean r = service.clearBatteryData(serialNum);
		response.setData(0);
		response.setCode(r?1:0);
		response.setMsg(r?"清除成功!":"清除失败!");
		return JSONObject.toJSONString(response);
	}
	
	@GET
	@Path("count/{serialNum}")
	@Produces(MediaType.APPLICATION_JSON)
	public String count(@PathParam("serialNum") String serialNum){
		logger.info("Clear battery datas:"+serialNum);
		ResponseEntity<Integer> response = new ResponseEntity<Integer>();
		int r = service.countBatteryData(serialNum);
		response.setData(r);
		response.setCode(1);
		response.setMsg("");
		return JSONObject.toJSONString(response);
	}
}
