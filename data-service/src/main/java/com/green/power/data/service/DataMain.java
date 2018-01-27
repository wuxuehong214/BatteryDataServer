package com.green.power.data.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.green.power.data.dao.DBUtil;
import com.green.power.data.dao.MongoDBFactory;
import com.green.power.data.entity.BatteryDataEntity;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class DataMain {

	public void insertOne() {
		BatteryDataEntity data = new BatteryDataEntity();
		data.setClientReadTime(new Date());
		// data.setData("我".getBytes());
		data.setDataLength(8);
		data.setRemark1("hehehe");
		data.setRemark2("你好");
		data.setSerialNum("battery1");
		data.setServerReadTime(new Date());

		MongoDBFactory db = MongoDBFactory.getInstance();
		MongoCollection<Document> conn = db
				.getCollection(DBUtil.COLLECTION_BATTERYDATA_NAME);

		String json = JSONObject.toJSONString(data);

		Document d = BatteryDataEntity.toDocument(json);

		System.out.println(json);
		System.out.println(d);
		System.out.println(d.toJson());
		conn.insertOne(d);
	}

	public void query() {
		MongoDBFactory db = MongoDBFactory.getInstance();
		MongoCollection<Document> conn = db
				.getCollection(DBUtil.COLLECTION_BATTERYDATA_NAME);

		FindIterable<Document> it = conn.find(new Document("cacheAddress", 123)
				.append("serialNum", "battery1"));
		it.forEach(new Block<Document>() {
			public void apply(Document d) {
				System.out.println(d);
				System.out.println(d.toJson());

				// System.out.println(d.get("data"));
				BatteryDataEntity data = BatteryDataEntity.toDataEntity(d);
				System.out.println(data);
				// System.out.println(Arrays.toString(data.getData()));
			}
		});
	}

	public static void main(String args[]){
		
//		DataMain data = new DataMain();
//		data.insertOne();
//		data.query();
		
		List<BatteryDataEntity> datas = new ArrayList<BatteryDataEntity>();
		
		for(int i=0;i<10;i++){
			Date d = new Date();
			BatteryDataEntity data = new BatteryDataEntity();
			data.setSerialNum("battery1");
			data.setClientReadTime(d);
			data.setData("A89WERFSFASDFAS090A9SFDA(#@");
			data.setDataLength(40);
			data.setRecordCurcor(12345555+(i+1));
			data.setRecordTime(34535456456l+(i+1));
			data.setRemark1("remar1.."+i);
			data.setRemark2("remark2.."+i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			datas.add(data);
		}
		
		System.out.println(JSONArray.toJSON(datas));
	}

}
